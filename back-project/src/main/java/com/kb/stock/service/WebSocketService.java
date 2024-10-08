package com.kb.stock.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.stock.dto.StockDTO;
import com.kb.stock.handler.StockWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketService extends TextWebSocketHandler {

    private final Map<String, StockDTO> lastStockData = new ConcurrentHashMap<>();
    private StockService stockService;
    private StockWebSocketHandler webSocketHandler;
    private final Set<String> subscribedStocks = ConcurrentHashMap.newKeySet();

    private static final Logger logger = LoggerFactory.getLogger(WebSocketService.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private WebSocketSession webSocketSession;
    private Map<String, Double> lastPrices = new ConcurrentHashMap<>();


    @Autowired
    private TokenService tokenService;

    @Value("${kis.api.appkey}")
    private String appKey;

    @Value("${kis.api.secretkey}")
    private String appSecret;

    @Value("${kis.api.base-url}")
    private String baseUrl;

    @Value("${kis.websocket.url}")
    private String webSocketUrl;

    @Autowired
    public WebSocketService(StockService stockService) {
        this.stockService = stockService;
    }
    @PostConstruct
    public void init() {
        updateAllPrices();
    }
    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void scheduledPriceUpdate() {
        updateAllPrices();
    }

    private final RestTemplate restTemplate = new RestTemplate();
    private final List<String> stockCodes = Arrays.asList("035720", "068270", "035420", "000660", "373220", "005380", "005930", "055550", "000270", "105560");

    private void updateAllStockData() {
        List<StockDTO> allStocks = stockService.getAllStocks();
        for (StockDTO stock : allStocks) {
            lastStockData.put(stock.getStockCode(), stock);
        }
        logger.info("Updated all stock data. Current stock count: {}", lastStockData.size());
    }
    public Map<String, StockDTO> getLastStockData() {
        if (lastStockData.isEmpty()) {
            updateAllStockData();
        }
        return new HashMap<>(lastStockData);
    }

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void scheduledStockDataUpdate() {
        updateAllStockData();
    }
    public String getWebSocketApprovalKey() {
        logger.info("WebSocket Approval Key 발급 요청 중...");

//        String accessToken = tokenService.getAccessToken();
        String url = baseUrl + "/oauth2/Approval";
        logger.info("WebSocket Approval Key 요청 URL: {}", url);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(accessToken);

        Map<String, String> body = new HashMap<>();
        body.put("grant_type", "client_credentials");
        body.put("appkey", appKey);
        body.put("secretkey", appSecret);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            logger.info("WebSocket Approval Key 요청 응답: {}", response);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                String approvalKey = (String) response.getBody().get("approval_key");
                logger.info("WebSocket Approval Key 발급 성공: {}", approvalKey);
                return approvalKey;
            } else {
                logger.error("WebSocket Approval Key 발급 실패: {}", response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            logger.error("WebSocket Approval Key 발급 중 오류 발생", e);
            return null;
        }
    }

    public void connectWebSocket() {
        String approvalKey = getWebSocketApprovalKey();
        if (approvalKey == null) {
            logger.error("WebSocket 연결 실패: Approval Key를 가져올 수 없습니다.");
            return;
        }

        StandardWebSocketClient client = new StandardWebSocketClient();
        try {
            webSocketSession = client.doHandshake(this, webSocketUrl).get();
            logger.info("WebSocket 연결 성공");
            subscribeToStocks();
        } catch (Exception e) {
            logger.error("WebSocket 연결 중 오류 발생", e);
        }
    }

    private void subscribeToStocks() {
        if (webSocketSession != null && webSocketSession.isOpen()) {
            for (String stockCode : stockCodes) {
                String message = String.format("{\"header\":{\"approval_key\":\"%s\",\"tr_type\":\"1\",\"content-type\":\"utf-8\"},\"body\":{\"input\":{\"tr_id\":\"H0STCNT0\",\"tr_key\":\"%s\"}}}", getWebSocketApprovalKey(), stockCode);
                try {
                    webSocketSession.sendMessage(new TextMessage(message));
                    logger.info("종목 구독 요청 전송: {}", stockCode);
                } catch (Exception e) {
                    logger.error("종목 구독 요청 전송 중 오류 발생: {}", stockCode, e);
                }
            }
        } else {
            logger.error("WebSocket 연결이 없거나 닫혀 있습니다.");
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.debug("받은 메시지: {}", payload);
        logger.info("수신된 주식 데이터: {}", stockCodes);

        Map<String, Object> responseMap = objectMapper.readValue(payload, Map.class);
        if (responseMap.containsKey("body")) {
            Map<String, Object> body = (Map<String, Object>) responseMap.get("body");
            String stockCode = (String) body.get("mksc_shrn_iscd");
            double currentPrice = Double.parseDouble((String) body.get("stck_prpr"));
            double priceChange = Double.parseDouble((String) body.get("prdy_vrss"));
            double changeRate = Double.parseDouble((String) body.get("prdy_ctrt"));

            lastPrices.put(stockCode, currentPrice);

            logger.info("실시간 주가 정보 - 종목코드: {}, 현재가: {}, 변동: {}, 변동률: {}%",
                    stockCode, currentPrice, priceChange, changeRate);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("WebSocket 연결 종료: {}", status);
        // 연결 재시도 로직을 여기에 구현할 수 있습니다.
    }

    public Map<String, Double> getLastPrices() {
        if (lastPrices.isEmpty()) {
            updateAllPrices();
        }
        return new HashMap<>(lastPrices);
    }
    public void addSubscription(String stockCode) {
        stockService.addSubscription(stockCode);
        StockDTO stockData = stockService.getStockData(stockCode);
        if (stockData != null) {
            updateLastPrices(stockCode, stockData.getCurrentPrice().doubleValue());
        }
    }
    public void updateLastPrices(String stockCode, Double price) {

        lastPrices.put(stockCode, price);

    }

    private void updateAllPrices() {
        List<StockDTO> allStocks = stockService.getAllStocks();
        for (StockDTO stock : allStocks) {
            lastPrices.put(stock.getStockCode(), stock.getCurrentPrice().doubleValue());
        }
    }

    @Autowired
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    @Autowired
    @Lazy
    public void setWebSocketHandler(StockWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

}