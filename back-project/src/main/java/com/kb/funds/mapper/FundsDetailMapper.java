package com.kb.funds.mapper;

import com.kb.funds.dto.FundsDetailDTO;

import java.util.List;

public interface FundsDetailMapper {
    void insertFundsDetails(List<FundsDetailDTO> fundDetails);
}
