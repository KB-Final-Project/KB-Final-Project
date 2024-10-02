package com.kb.funds.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuikChartDTO {
    private Long fundId;
    private String gijunYmd; // 기준일
    private Double bmSuikJisu; // 벤치마크 수익 지수
    private Double silhSuikRt; // 실현 수익률
    private Double seoljAek; // 순 자산
}
