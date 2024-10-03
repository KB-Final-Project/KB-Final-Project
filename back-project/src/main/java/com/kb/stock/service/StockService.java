package com.kb.stock.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class StockService {

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
}