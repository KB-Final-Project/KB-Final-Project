package com.kb.stock.controller;

import com.kb.stock.dto.StockDTO;
import com.kb.stock.service.WebSocketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/websocket")
@CrossOrigin(origins = "http://localhost:8081")
@Api(tags = {"WebSocket API"}, description = "WebSocket 관련 API")
public class WebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    private final WebSocketService webSocketService;

    @Autowired
    public WebSocketController(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @ApiOperation(value = "WebSocket 승인 키 발급", notes = "appkey와 appsecret을 사용하여 WebSocket 승인 키를 발급합니다.")
    @PostMapping("/approval-key")
    public ResponseEntity<String> getWebSocketApprovalKey() {
        try {
            String approvalKey = webSocketService.getWebSocketApprovalKey();  // WebSocketService에서 승인 키 발급
            if (approvalKey != null) {
                return ResponseEntity.ok(approvalKey);
            } else {
                return ResponseEntity.status(500).body("Approval Key 발급 실패");
            }
        } catch (Exception e) {
            logger.error("Approval Key 발급 중 오류 발생", e);
            return ResponseEntity.status(500).body("Approval Key 발급 중 오류 발생");
        }
    }


    @ApiOperation(value = "WebSocket 연결을 시작합니다.", notes = "발급된 WebSocket 승인 키를 사용하여 WebSocket 연결을 시작하고, 주식 데이터를 구독합니다.")
    @PostMapping("/connect")
    public ResponseEntity<String> connectWebSocket() {
        try {
            webSocketService.connectWebSocket();  // WebSocket 연결 및 주식 구독 처리
            return ResponseEntity.ok("WebSocket 연결 및 주식 구독이 성공적으로 시작되었습니다.");
        } catch (Exception e) {
            logger.error("WebSocket 연결 중 오류 발생", e);
            return ResponseEntity.status(500).body("WebSocket 연결 중 오류가 발생했습니다.");
        }
    }

    @ApiOperation(value = "주식 코드 구독", notes = "WebSocket을 통해 특정 주식 코드를 구독합니다.")
    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribeStocks(@RequestBody List<String> stockCodes) {
        try {
            for (String stockCode : stockCodes) {
                webSocketService.addSubscription(stockCode);  // 주식 코드 구독
            }
            return ResponseEntity.ok("주식 구독 성공");
        } catch (Exception e) {
            logger.error("주식 구독 중 오류 발생", e);
            return ResponseEntity.status(500).body("주식 구독 중 오류 발생");
        }
    }

    @ApiOperation(value = "현재 주식 가격 조회", notes = "WebSocket을 통해 구독한 주식의 최신 가격 정보를 반환합니다.")
    @GetMapping("/prices")
    public ResponseEntity<Map<String, StockDTO>> getStockPrices() {
        Map<String, StockDTO> stockData = webSocketService.getLastStockData();  // 마지막으로 구독한 주식 데이터
        if (stockData.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(stockData);
    }
}
