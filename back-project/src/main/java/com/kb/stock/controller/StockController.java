package com.kb.stock.controller;

import com.kb.stock.dto.StockDTO;
import com.kb.stock.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
@Api(tags = {"Stock API"}, description = "주식 관련 API")
public class StockController {

    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

    @Autowired
    private StockListService stockListService;

    @ApiOperation(value = "Access Token을 가져옵니다.", notes = "한국투자증권 API의 Access Token을 가져오는 메서드입니다.")
    @GetMapping("/token")
    public ResponseEntity<String> getAccessToken() {
        logger.info("getAccessToken 메서드 호출됨");
        try {
            String accessToken = stockService.getAccessToken();
            logger.info("발급된 Access Token: {}", accessToken);
            if (accessToken != null) {
                logger.info("토큰 발급 성공");
                return ResponseEntity.ok(accessToken);
            } else {
                logger.error("토큰 발급 실패");
                return ResponseEntity.status(500).body("Failed to get access token");
            }
        } catch (Exception e) {
            logger.error("토큰 발급 중 예외 발생", e);
            return ResponseEntity.status(500).body("Error occurred while getting access token");
        }
    }

    @ApiOperation(value = "WebSocket 승인 키를 가져옵니다.", notes = "Access Token을 이용하여 WebSocket 승인 키를 발급받는 메서드입니다.")
    @PostMapping("/token/websocket-key")
    public ResponseEntity<String> getWebSocketApprovalKey(
            @ApiParam(value = "Access Token", required = true) @RequestBody Map<String, String> requestBody) {
        logger.info("getWebSocketApprovalKey 메서드 호출됨");
        try {
            String accessToken = requestBody.get("accessToken");
            if (accessToken == null) {
                logger.error("accessToken이 요청 바디에 없습니다.");
                return ResponseEntity.badRequest().body("accessToken is required");
            }
            logger.info("요청된 Access Token: {}", accessToken);
            String approvalKey = stockService.getWebSocketApprovalKey(accessToken);
            if (approvalKey != null) {
                logger.info("WebSocket Approval Key 발급 성공: {}", approvalKey);
                return ResponseEntity.ok(approvalKey);
            } else {
                logger.error("WebSocket Approval Key 발급 실패");
                return ResponseEntity.status(500).body("Failed to get WebSocket approval key");
            }
        } catch (Exception e) {
            logger.error("WebSocket Approval Key 발급 중 예외 발생", e);
            return ResponseEntity.status(500).body("Error occurred while getting WebSocket approval key");
        }
    }

    @ApiOperation(value = "특정 종목 코드의 주식 데이터를 조회합니다.", notes = "주어진 종목 코드에 해당하는 주식 데이터를 조회하는 메서드입니다.")
    @GetMapping("/stock/{stockCode}")
    public ResponseEntity<?> getStockPrice(
            @ApiParam(value = "종목 코드", required = true) @PathVariable String stockCode) {
        logger.info("getStockPrice 메서드 호출됨: 종목 코드 = {}", stockCode);

        try {
            Map<String, Object> stockData = stockService.getStockPrice(stockCode);
            if (stockData != null) {
                return ResponseEntity.ok(stockData);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stock data not found for code: " + stockCode);
            }
        } catch (Exception e) {
            logger.error("주식 데이터 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while retrieving stock data: " + e.getMessage());
        }
    }

    @ApiOperation(value = "여러 종목 코드의 주식 데이터를 조회합니다.", notes = "주어진 종목 코드 리스트에 해당하는 여러 종목의 주식 데이터를 조회하는 메서드입니다.")
    @GetMapping("/stocks")
    public ResponseEntity<?> getStockPrices(
            @ApiParam(value = "종목 코드 리스트", required = true) @RequestParam(required = false) List<String> stockCodes) {
        logger.info("getStockPrices GET 메서드 호출됨: 종목 코드 리스트 = {}", stockCodes);

        if (stockCodes == null || stockCodes.isEmpty()) {
            return ResponseEntity.badRequest().body("Stock codes are required");
        }

        try {
            List<Map<String, Object>> stockDataList = stockListService.getStockPrices(stockCodes);
            if (!stockDataList.isEmpty()) {
                return ResponseEntity.ok(stockDataList);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No stock data found for the provided codes");
            }
        } catch (Exception e) {
            logger.error("주식 데이터 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while retrieving stock data: " + e.getMessage());
        }
    }

    // 주식 데이터를 저장하는 API
    @ApiOperation(value = "주식 데이터를 저장합니다.", notes = "POST 요청으로 주식 데이터를 저장합니다.")
    @PostMapping("/stocks")
    public String saveStock(@RequestBody StockDTO stockData) {
        stockService.saveStockData(stockData);
        return "Stock data saved successfully!";
    }

    // 모든 주식 데이터를 조회하는 API
    @GetMapping
    public List<StockDTO> getAllStocks() {
        return stockService.getAllStocks();
    }
}
