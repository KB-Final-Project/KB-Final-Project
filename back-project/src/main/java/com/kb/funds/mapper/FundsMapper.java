package com.kb.funds.mapper;

import com.kb.funds.dto.FundsDTO;

import java.util.List;

public interface FundsMapper {
    void insertFund(FundsDTO fund);
    void updateFund(FundsDTO fund); // 업데이트 메서드 추가

    // ID로 펀드 존재 여부 확인
    boolean exists(Long id);

    List<FundsDTO> searchFunds(String keyword);
    List<FundsDTO> findAllFunds();


}
