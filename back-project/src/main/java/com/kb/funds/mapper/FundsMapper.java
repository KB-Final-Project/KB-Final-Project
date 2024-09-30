package com.kb.funds.mapper;

import com.kb.funds.dto.FundsDTO;

import java.util.List;

public interface FundsMapper {
    void insertFund(FundsDTO fund);
    List<FundsDTO> searchFunds(String keyword);
    List<FundsDTO> findAllFunds();
}
