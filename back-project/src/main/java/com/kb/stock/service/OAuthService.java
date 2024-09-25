package com.kb.stock.service;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class OAuthService {

    @Value("${kis.api.appkey}")
    private String appKey;

    @Value("${kis.api.secretkey}")
    private String appSecret;

    @Value("${kis.api.base-url}")
    private String baseUrl;

    private String accessToken;
    private LocalDateTime tokenExpiryTime;

    // 접근 토큰 발급 메서드
    public String getAccessToken() {
        if (accessToken == null || isTokenExpired()) {
            requestNewToken();
        }
        return accessToken;
    }

    // 토큰 만료 여부 확인 메서드
    private boolean isTokenExpired() {
        return tokenExpiryTime == null || LocalDateTime.now().isAfter(tokenExpiryTime);
    }

    // 새로운 토큰 요청 메서드
    private void requestNewToken() {
        String url = baseUrl + "/oauth2/tokenP"; // 토큰 발급 API 엔드포인트

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("grant_type", "client_credentials");
        requestBody.put("appkey", appKey);
        requestBody.put("secretkey", appSecret);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json; charset=UTF-8");
            post.setEntity(new StringEntity(new ObjectMapper().writeValueAsString(requestBody)));

            // API 요청 및 응답 처리
            String responseString = EntityUtils.toString(client.execute(post).getEntity());
            Map<String, Object> responseBody = new ObjectMapper().readValue(responseString, Map.class);
            accessToken = (String) responseBody.get("access_token");

            // 토큰 만료 시간 계산
            String expiresIn = (String) responseBody.get("expires_in");
            tokenExpiryTime = LocalDateTime.now().plusSeconds(Long.parseLong(expiresIn));

            System.out.println("Access Token: " + accessToken);
            System.out.println("Token Expiry Time: " + tokenExpiryTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 웹소켓 접속키 발급 메서드
    public String getWebSocketKey() {
        String url = baseUrl + "/oauth2/Approval"; // 웹소켓 접속키 발급 API 엔드포인트

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("grant_type", "client_credentials");
        requestBody.put("appkey", appKey);
        requestBody.put("secretkey", appSecret);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json; charset=UTF-8");
            post.setEntity(new StringEntity(new ObjectMapper().writeValueAsString(requestBody)));

            // API 요청 및 응답 처리
            String responseString = EntityUtils.toString(client.execute(post).getEntity());
            Map<String, Object> responseBody = new ObjectMapper().readValue(responseString, Map.class);
            return (String) responseBody.get("approval_key");
        } catch (IOException e) {
            e.printStackTrace();
            return null; // 오류 발생 시 null 반환
        }
    }

    // 기본적인 Getter 메서드들
    public String getAppKey() {
        return appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
