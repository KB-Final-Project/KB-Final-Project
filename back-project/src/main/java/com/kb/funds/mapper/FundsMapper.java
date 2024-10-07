package com.kb.funds.mapper;

import com.kb.funds.dto.FundsDTO;
import com.kb.funds.dto.FundsDetailDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FundsMapper {
    @Transactional
    void insertFund(FundsDTO fund);

    void updateFund(FundsDTO fund); // 업데이트 메서드

    // ID로 펀드 존재 여부 확인
    boolean existsById(Long id);

    List<FundsDTO> searchFunds(String keyword);

    List<FundsDTO> findAllFunds();

    // FundsDetailDTO 관련 메서드
    void insertFundDetails(List<FundsDetailDTO> fundDetails);

    void deleteFundDetailsByFundId(Long fundId); // 특정 fundId에 대한 상세 정보 삭제
}
