package com.kb.funds.mapper;

import com.kb.funds.dto.FundsDetailDTO;

import java.util.List;

public interface FundsDetailMapper {
    // FundsDetailDTO 관련 메서드
    void insertFundDetails(List<FundsDetailDTO> fundDetails);

    void deleteFundDetailsByFundId(Long fundId); // 특정 fundId에 대한 상세 정보 삭제
}
