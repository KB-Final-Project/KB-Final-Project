package com.kb.stock.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.stock.dto.StockDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// WebSocketService 클래스

@Service
public class WebSocketService extends TextWebSocketHandler {

    private final Map<String, StockDTO> lastStockData = new ConcurrentHashMap<>();
    private final Set<String> subscribedStocks = ConcurrentHashMap.newKeySet();
    private static final Logger logger = LoggerFactory.getLogger(WebSocketService.class);
    private static final long APPROVAL_KEY_EXPIRATION_SECONDS = 24 * 60 * 60; // 24시간
    private WebSocketSession webSocketSession;
    private String approvalKey;
    private String webSocketUrl;  // 동적으로 발급받은 WebSocket URL
    private LocalDateTime approvalKeyIssuedTime;  // 승인 키 발급 시간 저장 변수 추가


    private final Map<String, Double> lastPrices = new ConcurrentHashMap<>();
    @Autowired
    private TokenService tokenService;

    @Value("${kis.api.appkey}")
    private String appKey;
    @Value("${kis.api.secretkey}")
    private String appSecret;
    @Value("${kis.api.base-url}")
    private String baseUrl;  // REST API의 base URL

    private final RestTemplate restTemplate = new RestTemplate();
    private final List<String> stockCodes = Arrays.asList("035720", "068270", "035420", "000660", "373220", "005380", "005930", "055550", "000270", "105560");

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final int RECONNECT_DELAY = 5;  // 재연결 지연 시간

    // WebSocket Approval Key와 WebSocket URL 발급 메서드
    public String getWebSocketUrl() {
        if (approvalKey != null) {
            return webSocketUrl;
        }

        logger.info("WebSocket Approval Key 발급 및 WebSocket URL 요청 중...");
        String accessToken = tokenService.getAccessToken();
        String url = baseUrl + "/oauth2/Approval";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        Map<String, String> body = new HashMap<>();
        body.put("grant_type", "client_credentials");
        body.put("appkey", appKey);
        body.put("secretkey", appSecret);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                approvalKey = (String) response.getBody().get("approval_key");
                webSocketUrl = (String) response.getBody().get("websocket_url");  // WebSocket URL 발급
                logger.info("새로운 WebSocket URL 발급 성공: {}", webSocketUrl);
                return webSocketUrl;
            } else {
                logger.error("WebSocket Approval Key 발급 및 WebSocket URL 요청 실패: {}", response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            logger.error("WebSocket Approval Key 및 URL 발급 중 오류 발생", e);
            return null;
        }
    }

    // WebSocket 연결
    public void connectWebSocket() {
        webSocketUrl = getWebSocketUrl();  // 동적으로 발급받은 WebSocket URL 사용
        if (webSocketUrl == null) {
            logger.error("WebSocket 연결 실패: WebSocket URL을 가져올 수 없습니다.");
            return;
        }

        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.add("Authorization", "Bearer " + approvalKey);  // WebSocket 전용 헤더

        try {
            webSocketSession = client.doHandshake(this, String.valueOf(headers), webSocketUrl).get();
            logger.info("WebSocket 연결 성공");
            requestStockListFromWebSocket();  // WebSocket을 통해 주식 목록 조회 요청
        } catch (Exception e) {
            logger.error("WebSocket 연결 중 오류 발생", e);
            scheduleReconnect();  // 연결 실패 시 재연결 시도
        }
    }

    // WebSocket을 통해 주식 목록 조회 요청 전송
    private void requestStockListFromWebSocket() {
        if (webSocketSession != null && webSocketSession.isOpen()) {
            for (String stockCode : stockCodes) {
                String message = String.format(
                        "{\"header\":{\"approval_key\":\"%s\",\"tr_type\":\"1\",\"content-type\":\"utf-8\"},\"body\":{\"input\":{\"tr_id\":\"H0STCNT0\",\"tr_key\":\"%s\"}}}",
                        approvalKey, stockCode
                );
                try {
                    webSocketSession.sendMessage(new TextMessage(message));
                    logger.info("WebSocket 주식 목록 요청 전송 성공 - 종목코드: {}", stockCode);
                } catch (Exception e) {
                    logger.error("WebSocket 주식 목록 요청 실패 - 종목코드: {}", stockCode, e);
                }
            }
        } else {
            logger.error("WebSocket 연결이 없거나 닫혀 있습니다.");
        }
    }

    // 실시간 데이터 수신 처리
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.debug("받은 메시지: {}", payload);

        Map<String, Object> responseMap = new ObjectMapper().readValue(payload, Map.class);
        if (responseMap.containsKey("body")) {
            Map<String, Object> body = (Map<String, Object>) responseMap.get("body");
            String stockCode = (String) body.get("mksc_shrn_iscd");
            double currentPrice = Double.parseDouble((String) body.get("stck_prpr"));
            double priceChange = Double.parseDouble((String) body.get("prdy_vrss"));
            double changeRate = Double.parseDouble((String) body.get("prdy_ctrt"));

            logger.info("실시간 주가 정보 - 종목코드: {}, 현재가: {}, 변동: {}, 변동률: {}%",
                    stockCode, currentPrice, priceChange, changeRate);
        }
    }

    // 재연결을 일정 시간 후 시도하는 메서드
    private void scheduleReconnect() {
        logger.info("{}초 후 WebSocket 재연결 시도", RECONNECT_DELAY);
        scheduler.schedule(() -> {
            logger.info("WebSocket 재연결 시도 중...");
            connectWebSocket();  // 재연결 시도
        }, RECONNECT_DELAY, TimeUnit.SECONDS);
    }

    public String getWebSocketApprovalKey() {
        if (approvalKey != null && approvalKeyIssuedTime != null) {
            long timeElapsed = Duration.between(approvalKeyIssuedTime, LocalDateTime.now()).getSeconds();
            if (timeElapsed < APPROVAL_KEY_EXPIRATION_SECONDS) {
                logger.info("기존 WebSocket Approval Key 사용: {}", approvalKey);
                return approvalKey;
            }
        }

        logger.info("WebSocket Approval Key 발급 요청 중...");
        String accessToken = tokenService.getAccessToken();
        String url = baseUrl + "/oauth2/Approval";
        logger.info("WebSocket Approval Key 요청 URL: {}", url);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        Map<String, String> body = new HashMap<>();
        body.put("grant_type", "client_credentials");
        body.put("appkey", appKey);
        body.put("secretkey", appSecret);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                approvalKey = (String) response.getBody().get("approval_key");
                approvalKeyIssuedTime = LocalDateTime.now(); // 발급 시간 갱신
                logger.info("새로운 WebSocket Approval Key 발급 성공: {}", approvalKey);
                return approvalKey;
            } else {
                logger.error("WebSocket Approval Key 발급 실패: {}", response.getStatusCode());
                return null;
            }
        } catch (HttpClientErrorException e) {
            logger.error("클라이언트 오류 발생: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            return null;
        } catch (HttpServerErrorException e) {
            logger.error("서버 오류 발생: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            return null;
        } catch (ResourceAccessException e) {
            logger.error("네트워크 또는 연결 오류 발생: {}", e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("WebSocket Approval Key 발급 중 알 수 없는 오류 발생", e);
            return null;
        }
    }
    public Map<String, StockDTO> getLastStockData() {
        return new HashMap<>(lastStockData);  // 마지막으로 받은 주식 데이터를 반환
    }
    public void addSubscription(String stockCode) {
        if (webSocketSession != null && webSocketSession.isOpen() && !subscribedStocks.contains(stockCode)) {
            String message = String.format("{\"header\":{\"approval_key\":\"%s\",\"tr_type\":\"1\",\"content-type\":\"utf-8\"},\"body\":{\"input\":{\"tr_id\":\"H0STCNT0\",\"tr_key\":\"%s\"}}}", approvalKey, stockCode);
            try {
                webSocketSession.sendMessage(new TextMessage(message));
                logger.info("주식 구독 요청 성공: {}", stockCode);
                subscribedStocks.add(stockCode);  // 구독 목록에 추가
            } catch (Exception e) {
                logger.error("종목 구독 요청 중 오류 발생: {}", stockCode, e);
            }
        } else {
            logger.error("WebSocket 연결이 없거나 종목이 이미 구독되었습니다.");
        }
    }
    public void updateLastPrices(String stockCode, Double price) {
        if (stockCode != null && price != null) {
            lastPrices.put(stockCode, price);  // 주식 코드에 따른 가격 저장
            logger.info("주식 가격 업데이트 - 종목코드: {}, 가격: {}", stockCode, price);
        } else {
            logger.warn("주식 가격 업데이트 실패 - 유효하지 않은 종목코드 또는 가격");
        }
    }
}
