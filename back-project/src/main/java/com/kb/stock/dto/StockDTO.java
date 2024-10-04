package com.kb.stock.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StockDTO {
    private Long id;
    private String stockCode;
    private String stockName;
    private BigDecimal currentPrice;
    private BigDecimal priceChange;
    private BigDecimal priceChangePct;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private BigDecimal openingPrice;
    private Long volume;
    private LocalDateTime timestamp;
}
