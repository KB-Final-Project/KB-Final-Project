package com.kb.stock.controller;

import com.kb.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class StockController {

    @Autowired
    private StockService stockService;

    // 현재 증시 데이터를 가져오는 API 엔드포인트
    @GetMapping("/stocks/current")
    public ResponseEntity<List<Map<String, Object>>> getCurrentStocks() {
        return ResponseEntity.ok(stockService.getCurrentStocks());
    }

    // 상위 3개의 주식을 가져오는 API 엔드포인트
    @GetMapping("/stocks/top3")
    public ResponseEntity<List<Map<String, Object>>> getTop3Stocks() {
        return ResponseEntity.ok(stockService.getTop3Stocks());
    }

    // 주식 리스트를 페이징 처리하여 가져오는 API 엔드포인트
    @GetMapping("/stocks")
    public ResponseEntity<Map<String, Object>> getStocks(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(stockService.getStocks(keyword, page, size));
    }

    // 특정 종목의 현재가 시세를 가져오는 API 엔드포인트
    @GetMapping("/stocks/price")
    public ResponseEntity<Map<String, Object>> getStockPrice(@RequestParam String code) {
        return ResponseEntity.ok(stockService.getStockPrice(code));
    }

    // 웹소켓 접속키 발급 API 엔드포인트
    @PostMapping("/websocket-key")
    public ResponseEntity<Map<String, String>> getWebSocketKey() {
        return ResponseEntity.ok(stockService.getWebSocketKey());
    }
}
