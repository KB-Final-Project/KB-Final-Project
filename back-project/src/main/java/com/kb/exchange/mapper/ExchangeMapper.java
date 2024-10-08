package com.kb.exchange.mapper;


import com.kb.exchange.dto.*;

import java.sql.Date;
import java.util.List;

public interface ExchangeMapper {
    void insertExchangeRate(List<Exchange> list);
    List<ExchangeDailyDTO> getDailyExchange(Date date);
    List<ExchangeListByTerm> getExchangeDetailByTerm(ExchangeParam exchangeParam);
    List<ExchangeFee> getExchangeFeeByCurrency(int currencyId);
    void batchUpdateExchangeFees(List<ExchangeFee> updateList);
    void batchInsertExchangeFees(List<ExchangeFee> saveList);
    int checkExistExchangeFee(ExchangeFee exchangeFeeDTO);
    int checkSameExchangeFee(ExchangeFee exchangeFeeDTO);
    int getCurrenyIdByCurUnit(String curUnit);
    List<Currency> getExchangeBankCategory();
    int checkCurrencyExists(String currencyCode);

}

