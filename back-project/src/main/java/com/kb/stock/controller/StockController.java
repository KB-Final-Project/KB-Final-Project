package com.kb.stock.controller;

import com.kb.stock.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class StockController {

    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

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

    @PostMapping("/token/websocket-key")
    public ResponseEntity<String> getWebSocketApprovalKey(@RequestBody Map<String, String> requestBody) {
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

}