package com.kb.exchange.mapper;


import com.kb.exchange.dto.Exchange;
import com.kb.exchange.dto.ExchangeDailyDTO;
import com.kb.exchange.dto.ExchangeParam;

import java.util.List;

public interface ExchangeMapper {
    void insertExchangeRate(Exchange exchange);
    List<ExchangeDailyDTO> getDailyExchange();
    List<Exchange> getExchangeListByTerm(ExchangeParam exchangeParam);
}
