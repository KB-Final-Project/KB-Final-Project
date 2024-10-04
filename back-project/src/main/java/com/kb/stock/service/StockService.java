package com.kb.stock.service;

import com.kb.stock.dto.StockDTO;
import com.kb.stock.mapper.StockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
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

    public String getAccessToken() {
        logger.info("getAccessToken 메서드 시작");
        try {
            // 이미 유효한 토큰이 있는 경우 해당 토큰을 반환
            if (currentAccessToken != null && System.currentTimeMillis() < tokenExpiryTime) {
                logger.info("기존 유효한 토큰 반환");
                return currentAccessToken;
            }

            // 새로운 토큰 발급
            String url = baseUrl + "/oauth2/tokenP";
            logger.info("새 토큰 요청 URL: {}", url);

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
                long expiresIn = (Integer) response.getBody().get("expires_in"); // 토큰 유효기간 (초)
                tokenExpiryTime = System.currentTimeMillis() + (expiresIn * 1000);
                logger.info("새 Access Token 발급 성공: {}", currentAccessToken);
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
        } catch (HttpClientErrorException e) {
            logger.error("API 요청 중 오류 발생: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            // 필요에 따라 특정 오류에 대한 재시도 로직 추가 가능
            return null;
        } catch (Exception e) {
            logger.error("종목 데이터 조회 중 오류 발생", e);
            return null;
        }
    }

    public List<String> getAllStockCodes() {
        return stockMapper.selectAllStockCodes();
    }

    public void fetchAndSaveStockData(List<String> stockCodes) {
        for (String stockCode : stockCodes) {
            logger.info("주식 데이터 가져오기: {}", stockCode);

            // API 호출하여 주식 데이터 가져오기
            Map<String, Object> stockData = getStockPrice(stockCode);

            if (stockData != null) {
                StockDTO stockDTO = mapToStockDTO(stockData);

                // DB에 저장 (새로운 데이터면 insert, 있으면 update)
                saveStockData(stockDTO);
            }
        }
    }

    private StockDTO mapToStockDTO(Map<String, Object> stockData) {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setStockCode((String) stockData.get("stck_shrn_iscd")); // 종목 코드
        stockDTO.setStockName((String) stockData.get("rprs_mrkt_kor_name")); // 종목명
        stockDTO.setCurrentPrice(new BigDecimal(stockData.get("stck_prpr").toString())); // 현재가
        stockDTO.setPriceChange(new BigDecimal(stockData.get("prdy_vrss").toString())); // 전일 대비
        stockDTO.setPriceChangePct(new BigDecimal(stockData.get("prdy_ctrt").toString())); // 전일 대비율
        stockDTO.setHighPrice(new BigDecimal(stockData.get("stck_hgpr").toString())); // 최고가
        stockDTO.setLowPrice(new BigDecimal(stockData.get("stck_lwpr").toString())); // 최저가
        stockDTO.setOpeningPrice(new BigDecimal(stockData.get("stck_oprc").toString())); // 시가
        stockDTO.setVolume(Long.parseLong(stockData.get("acml_vol").toString())); // 거래량
        return stockDTO;
    }

    // DB에 주식 데이터를 저장하는 메서드
    public void saveStockData(StockDTO stockData) {
        StockDTO existingStock = stockMapper.selectStockByCode(stockData.getStockCode());

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
