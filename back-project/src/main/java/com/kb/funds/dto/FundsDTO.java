package com.kb.funds.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kb._config.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FundsDTO {
    private Long id; // ID 필드 추가

    private String fundFnm;

    // 기준가
    private BigDecimal gijunGa;

    // 기준일
    @JsonDeserialize(using = LocalDateDeserializer.class) //
    private LocalDate gijunYmd;

    // 1개월 수익률
    private BigDecimal suikRt1;

    // 3개월 수익률
    private BigDecimal suikRt3;

    // 1년 수익률
    private BigDecimal suikRt12;

    // 투자 위험 등급
    private int investGrade;

    // 총 보수
    private BigDecimal feeTot;

    // 수익 차트 리스트 초기화
    @Builder.Default
    private List<SuikChartDTO> suikChart = new ArrayList<>(); // SuikChartDTO 리스트

    // 벤치마크
    private String bmNm;
}
