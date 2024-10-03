package com.kb.funds.mapper;

import com.kb.funds.dto.SuikChartDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SuikChartMapper {
    boolean existsByFundIdAndGijunYmd(@Param("fundId") Long fundId, @Param("gijunYmd") String gijunYmd);

    void insertSuikCharts(@Param("suikCharts") List<SuikChartDTO> suikCharts);

    void deleteSuikChartByFundId(Long fundId);

    List<SuikChartDTO> findSuikChartByFundId(Long fundId);
}
