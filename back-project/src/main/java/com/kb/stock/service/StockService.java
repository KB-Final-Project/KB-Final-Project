package com.kb.stock.service;

import com.kb.stock.dto.StockDTO;
import com.kb.stock.mapper.StockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockService {

    @Autowired
    private StockMapper stockMapper;

    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    @Value("${kis.api.appkey}")
    private String appKey;

    @Value("${kis.api.secretkey}")
    private String appSecret;

    @Value("${kis.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    private String currentAccessToken;
    private long tokenExpiryTime;

    // Access Token 발급 메서드
    public String getAccessToken() {
        logger.info("getAccessToken 메서드 시작");
        try {
            if (currentAccessToken != null && System.currentTimeMillis() < tokenExpiryTime) {
                logger.info("기존 유효한 토큰 반환");
                return currentAccessToken;
            }

            String url = baseUrl + "/oauth2/tokenP";
            logger.info("토큰 요청 URL: {}", url);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json; charset=utf-8");

            Map<String, String> body = new HashMap<>();
            body.put("grant_type", "client_credentials");
            body.put("appkey", appKey);
            body.put("appsecret", appSecret);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                currentAccessToken = (String) response.getBody().get("access_token");
                tokenExpiryTime = System.currentTimeMillis() + (24 * 60 * 60 * 1000);
                logger.info("Access Token 발급 성공: {}", currentAccessToken);
                return currentAccessToken;
            } else {
                logger.error("Access Token 발급 실패. 응답: {}", response.getBody());
                return null;
            }
        } catch (Exception e) {
            logger.error("Access Token 발급 중 예외 발생", e);
            return null;
        }
    }

    // WebSocket 승인 키 발급 메서드
    public String getWebSocketApprovalKey(String accessToken) {
        logger.info("getWebSocketApprovalKey 메서드 시작");
        String url = baseUrl + "/oauth2/Approval";
        logger.info("WebSocket Approval Key 요청 URL: {}", url);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

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

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String approvalKey = (String) response.getBody().get("approval_key");
                logger.info("WebSocket Approval Key 발급 성공: {}", approvalKey);
                return approvalKey;
            } else {
                logger.error("WebSocket Approval Key 요청 실패: {}", response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            logger.error("WebSocket Approval Key 요청 중 오류 발생", e);
            return null;
        }
    }

    // 종목 데이터를 받아오는 메서드 (tr_id 추가)
    public Map<String, Object> getStockPrice(String stockCode) {
        logger.info("getStockPrice 메서드 시작: 종목 코드 = {}", stockCode);

        String url = baseUrl + "/uapi/domestic-stock/v1/quotations/inquire-price?FID_COND_MRKT_DIV_CODE=J&FID_INPUT_ISCD=" + stockCode;
        logger.info("종목 현재가 요청 URL: {}", url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=utf-8");
        headers.set("authorization", "Bearer " + getAccessToken());
        headers.set("appkey", appKey);
        headers.set("appsecret", appSecret);
        headers.set("tr_id", "FHKST01010100");

        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("종목 데이터 조회 성공: {}", response.getBody());
                return response.getBody();
            } else {
                logger.error("종목 데이터 조회 실패: {}", response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            logger.error("종목 데이터 조회 중 오류 발생", e);
            return null;
        }
    }

    // DB에 주식 데이터를 저장하는 메서드
    public void saveStockData(StockDTO stockData) {
        StockDTO existingStock = stockMapper.selectAllStocks()
                .stream()
                .filter(stock -> stock.getStockCode().equals(stockData.getStockCode()))
                .findFirst()
                .orElse(null);

        if (existingStock == null) {
            stockMapper.insertStock(stockData);
        } else {
            stockMapper.updateStock(stockData);
        }
    }

    public List<StockDTO> getAllStocks() {
        return stockMapper.selectAllStocks();
    }
}
