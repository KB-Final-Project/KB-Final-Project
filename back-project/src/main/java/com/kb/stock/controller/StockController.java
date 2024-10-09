package com.kb.stock.controller;

import com.kb.stock.dto.StockDTO;
import com.kb.stock.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/stocks")
@CrossOrigin(origins = "http://localhost:8081")
@Api(tags = {"Stock API"}, description = "주식 관련 API")
public class StockController {

    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

    // 모든 주식 데이터를 조회합니다.
    @ApiOperation(value = "모든 주식 데이터를 조회합니다.", notes = "데이터베이스에 저장된 모든 주식 데이터를 조회합니다.")
    @GetMapping("/all")
    public ResponseEntity<List<StockDTO>> getAllStocks() {
        logger.info("getAllStocks 메서드 호출됨");
        try {
            List<StockDTO> stocks = stockService.getAllStocks();
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            logger.error("모든 주식 데이터 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 모든 주식 코드를 조회합니다.
    @ApiOperation(value = "모든 주식 코드를 조회합니다.", notes = "데이터베이스에 저장된 모든 주식 코드를 조회합니다.")
    @GetMapping("/codes")
    public ResponseEntity<List<String>> getAllStockCodes() {
        logger.info("getAllStockCodes 메서드 호출됨");
        try {
            List<String> stockCodes = stockService.getAllStockCodes();
            return ResponseEntity.ok(stockCodes);
        } catch (Exception e) {
            logger.error("모든 주식 코드 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 카테고리별 평균 등락률 조회
    @ApiOperation(value = "카테고리별 평균 등락률을 조회합니다.", notes = "각 카테고리의 평균 등락률과 관련된 데이터를 조회합니다.")
    @GetMapping("/categories")
    public ResponseEntity<List<Map<String, Object>>> getCategoryRankings() {
        logger.info("getCategoryRankings 메서드 호출됨");
        try {
            List<Map<String, Object>> categoryRankings = stockService.getCategoryRankings();
            return ResponseEntity.ok(categoryRankings);
        } catch (Exception e) {
            logger.error("카테고리별 평균 등락률 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}