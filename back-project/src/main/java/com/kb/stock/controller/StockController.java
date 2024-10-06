//package com.kb.stock.controller;
//
//import com.kb.stock.dto.StockDTO;
//import com.kb.stock.service.StockService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/stocks")
//@CrossOrigin(origins = "http://localhost:8081")
//@Api(tags = {"Stock API"}, description = "주식 관련 API")
//public class StockController {
//
//    private static final Logger logger = LoggerFactory.getLogger(StockController.class);
//
//    @Autowired
//    private StockService stockService;
//
//    @ApiOperation(value = "모든 주식 데이터를 조회합니다.", notes = "데이터베이스에 저장된 모든 주식 데이터를 조회합니다.")
//    @GetMapping("/all")
//    public ResponseEntity<List<StockDTO>> getAllStocks() {
//        logger.info("getAllStocks 메서드 호출됨");
//        try {
//            List<StockDTO> stocks = stockService.getAllStocks();
//            return ResponseEntity.ok(stocks);
//        } catch (Exception e) {
//            logger.error("모든 주식 데이터 조회 중 오류 발생", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @ApiOperation(value = "모든 주식 코드를 조회합니다.", notes = "데이터베이스에 저장된 모든 주식 코드를 조회합니다.")
//    @GetMapping("/codes")
//    public ResponseEntity<List<String>> getAllStockCodes() {
//        logger.info("getAllStockCodes 메서드 호출됨");
//        try {
//            List<String> stockCodes = stockService.getAllStockCodes();
//            return ResponseEntity.ok(stockCodes);
//        } catch (Exception e) {
//            logger.error("모든 주식 코드 조회 중 오류 발생", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//    @ApiOperation(value = "주식 기본 정보를 조회합니다.", notes = "해당 주식 코드를 통해 한국투자증권 API로부터 주식 기본 정보를 가져옵니다.")
//    @GetMapping("/basic-info/{stockCode}")
//    public ResponseEntity<StockDTO> getStockBasicInfo(
//            @ApiParam(value = "조회할 주식 종목 코드", required = true)
//            @PathVariable String stockCode) {
//        logger.info("getStockBasicInfo 메서드 호출됨, 종목 코드: {}", stockCode);
//        try {
//            StockDTO stockInfo = stockService.getStockBasicInfo(stockCode);
//            if (stockInfo != null) {
//                return ResponseEntity.ok(stockInfo);
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // 주식 정보를 찾을 수 없을 경우 404 반환
//            }
//        } catch (Exception e) {
//            logger.error("주식 기본 정보 조회 중 오류 발생: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//}
