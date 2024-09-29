package com.kb.stock.service;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockService {

    @Autowired
    private OAuthService oAuthService;

    // 현재 증시 데이터 가져오기
    public List<Map<String, Object>> getCurrentStocks() {
        String url = oAuthService.getBaseUrl() + "/uapi/domestic-stock/v1/quotations/inquirePrice";

        HttpGet request = new HttpGet(url);
        setRequestHeaders(request);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String responseString = EntityUtils.toString(client.execute(request).getEntity());
            Map<String, Object> responseBody = new ObjectMapper().readValue(responseString, Map.class);
            return parseStockData(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();  // 오류 발생 시 빈 리스트 반환
        }
    }

    // 상위 3개 주식 데이터 가져오기
    public List<Map<String, Object>> getTop3Stocks() {
        String url = oAuthService.getBaseUrl() + "/uapi/domestic-stock/v1/quotations/inquireDailyTop3";

        HttpGet request = new HttpGet(url);
        setRequestHeaders(request);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String responseString = EntityUtils.toString(client.execute(request).getEntity());
            Map<String, Object> responseBody = new ObjectMapper().readValue(responseString, Map.class);
            return parseStockData(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    // 주식 리스트를 페이징 처리하여 가져오기
    public Map<String, Object> getStocks(String keyword, int page, int size) {
        String url = oAuthService.getBaseUrl() + "/uapi/domestic-stock/v1/quotations/inquireList";
        HttpGet request = new HttpGet(url);
        setRequestHeaders(request);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String responseString = EntityUtils.toString(client.execute(request).getEntity());
            Map<String, Object> responseBody = new ObjectMapper().readValue(responseString, Map.class);
            return parseStockListData(responseBody, page, size);
        } catch (IOException e) {
            e.printStackTrace();
            return Map.of();  // 예외 발생 시 빈 맵 반환
        }
    }

    // 특정 종목의 현재가 시세 가져오기
    public Map<String, Object> getStockPrice(String code) {
        String url = oAuthService.getBaseUrl() + "/uapi/domestic-stock/v1/quotations/inquire-price?FID_COND_MRKT_DIV_CODE=J&FID_INPUT_ISCD=" + code;
        HttpGet request = new HttpGet(url);
        setRequestHeaders(request);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String responseString = EntityUtils.toString(client.execute(request).getEntity());
            return new ObjectMapper().readValue(responseString, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return Map.of();  // 예외 발생 시 빈 맵 반환
        }
    }

    // 웹소켓 접속키 발급 메서드
    public Map<String, String> getWebSocketKey() {
        String url = oAuthService.getBaseUrl() + "/oauth2/Approval";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("grant_type", "client_credentials");
        requestBody.put("appkey", oAuthService.getAppKey());
        requestBody.put("secretkey", oAuthService.getAppSecret());

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json; charset=UTF-8");
            post.setEntity(new StringEntity(new ObjectMapper().writeValueAsString(requestBody)));

            String responseString = EntityUtils.toString(client.execute(post).getEntity());
            Map<String, Object> responseBody = new ObjectMapper().readValue(responseString, Map.class);
            String approvalKey = (String) responseBody.get("approval_key");

            Map<String, String> result = new HashMap<>();
            result.put("approval_key", approvalKey);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return Map.of();  // 예외 발생 시 빈 맵 반환
        }
    }

    // 요청에 필요한 공통 헤더 설정 메서드
    private void setRequestHeaders(HttpGet request) {
        request.setHeader("Authorization", "Bearer " + oAuthService.getAccessToken());
        request.setHeader("appkey", oAuthService.getAppKey());
        request.setHeader("appsecret", oAuthService.getAppSecret());
        request.setHeader("Content-Type", "application/json; charset=UTF-8");
        request.setHeader("tr_id", "FHKST01010100"); // 주식현재가 시세 요청 시 tr_id 필요
    }

    // 실제 API 응답 데이터를 파싱하는 메서드들
    private List<Map<String, Object>> parseStockData(Map<String, Object> responseBody) {
        // 실제 API 응답 데이터의 구조에 맞춰 파싱 로직을 구현해야 합니다.
        return List.of();
    }

    private Map<String, Object> parseStockListData(Map<String, Object> responseBody, int page, int size) {
        // 실제 API 응답 데이터의 구조에 맞춰 파싱 로직을 구현해야 합니다.
        return Map.of();
    }
}
