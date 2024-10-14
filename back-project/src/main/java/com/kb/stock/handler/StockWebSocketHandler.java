package com.kb.stock.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.stock.dto.StockDTO;
import com.kb.stock.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StockWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final StockService stockService;
    private final Map<WebSocketSession, Set<String>> sessionSubscriptions = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(StockWebSocketHandler.class);

    @Autowired
    public StockWebSocketHandler(StockService stockService) {
        this.stockService = stockService;
        this.stockService.setWebSocketHandler(this);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionSubscriptions.put(session, new HashSet<>());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.debug("받은 메시지: {}", payload);
        Map<String, Object> stockData = objectMapper.readValue(payload, Map.class);

        String action = (String) stockData.get("action");
        String stockCode = (String) stockData.get("stockCode");

        if ("subscribe".equals(action)) {
            subscribeStock(session, stockCode);
        } else if ("unsubscribe".equals(action)) {
            unsubscribeStock(session, stockCode);
        }
    }

    private void subscribeStock(WebSocketSession session, String stockCode) {
        sessionSubscriptions.get(session).add(stockCode);
        stockService.addSubscription(stockCode);
        sendInitialStockData(session, stockCode);
    }

    private void unsubscribeStock(WebSocketSession session, String stockCode) {
        sessionSubscriptions.get(session).remove(stockCode);
        stockService.removeSubscription(stockCode);
    }

    private void sendInitialStockData(WebSocketSession session, String stockCode) {
        try {
            StockDTO stockData = stockService.getStockData(stockCode);
            if (stockData != null) {
                String jsonStockData = objectMapper.writeValueAsString(stockData);
                session.sendMessage(new TextMessage(jsonStockData));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendStockData(String stockCode, StockDTO stockData) {
        String jsonStockData;
        try {
            jsonStockData = objectMapper.writeValueAsString(stockData);
            logger.info("Sending stock data: {}", jsonStockData);  // 여기에 로그 추가
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        for (Map.Entry<WebSocketSession, Set<String>> entry : sessionSubscriptions.entrySet()) {
            WebSocketSession session = entry.getKey();
            Set<String> subscriptions = entry.getValue();
            if (session.isOpen() && subscriptions.contains(stockCode)) {
                try {
                    session.sendMessage(new TextMessage(jsonStockData));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Set<String> subscriptions = sessionSubscriptions.remove(session);
        if (subscriptions != null) {
            for (String stockCode : subscriptions) {
                stockService.removeSubscription(stockCode);
            }
        }
    }
}