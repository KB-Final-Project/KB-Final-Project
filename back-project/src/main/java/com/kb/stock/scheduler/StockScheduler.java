//package com.kb.stock.scheduler;
//
//import com.kb.stock.service.StockService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class StockScheduler {
//
//    private static final Logger logger = LoggerFactory.getLogger(StockScheduler.class);
//
//    @Autowired
//    private StockService stockService;
//
//    // 스케줄러를 30초마다 실행
//    @Scheduled(fixedRate = 30000)
//    public void fetchAndSaveStocks() {
//        logger.info("스케줄러 시작 - 주식 데이터 가져오기");
//
//        try {
//            // 주식 코드 리스트 가져오기 (700개)
//            List<String> stockCodes = stockService.getAllStockCodes();
//            int batchSize = 20;
//            int totalStocks = stockCodes.size();
//
//            // 주식 코드 20개씩 나누어 API 요청
//            for (int i = 0; i < totalStocks; i += batchSize) {
//                int endIndex = Math.min(i + batchSize, totalStocks);
//                List<String> batchStockCodes = stockCodes.subList(i, endIndex);
//
//                // 주식 데이터를 가져와서 DB에 저장
//                stockService.fetchAndSaveStockData(batchStockCodes);
//
//                // 1-2초 대기
//                Thread.sleep(1000); // 1초
//            }
//
//            logger.info("스케줄러 완료 - 주식 데이터 저장 완료");
//        } catch (Exception e) {
//            logger.error("주식 데이터 가져오는 중 오류 발생", e);
//        }
//    }
//}
