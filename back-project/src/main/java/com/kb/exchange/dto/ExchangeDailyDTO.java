package com.kb.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExchangeDailyDTO {
    private Exchange exchange;
    private double dailyChangeRate;
}
