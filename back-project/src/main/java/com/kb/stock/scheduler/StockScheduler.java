//package com.kb.stock.scheduler;
//
//import com.kb.stock.service.StockService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//
//@Component
//public class StockScheduler {
//
//    private static final Logger logger = LoggerFactory.getLogger(StockScheduler.class);
//
//    @Autowired
//    private StockService stockService;
//
//    @Scheduled(cron = "0 */1 * * * *") // 매 1분마다 실행
//    public void updateAllStocksScheduled() {
//        logger.info("Starting scheduled update of all stocks at {}", LocalDateTime.now());
//        try {
//            stockService.updateAllStocks();
//            logger.info("Completed scheduled update of all stocks at {}", LocalDateTime.now());
//        } catch (Exception e) {
//            logger.error("Error during scheduled update of stocks", e);
//        }
//    }
//
//}