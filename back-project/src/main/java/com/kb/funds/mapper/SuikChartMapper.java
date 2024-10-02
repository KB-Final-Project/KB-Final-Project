package com.kb.funds.mapper;

import com.kb.funds.dto.SuikChartDTO;

import java.util.List;

public interface SuikChartMapper {

    int insertSuikChart(SuikChartDTO suikChart);

    void deleteSuikChartByFundId(Long fundId);

    List<SuikChartDTO> findSuikChartsByFundId(Long fundId);
}
