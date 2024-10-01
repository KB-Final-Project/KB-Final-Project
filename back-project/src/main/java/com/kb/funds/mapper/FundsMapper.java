package com.kb.funds.mapper;

import com.kb.funds.dto.FundsDTO;

import java.util.List;

public interface FundsMapper {
    void insertFund(FundsDTO fund);
    void updateFund(FundsDTO fund); // 업데이트 메서드 추가
    List<FundsDTO> searchFunds(String keyword);
    List<FundsDTO> findAllFunds();
}
