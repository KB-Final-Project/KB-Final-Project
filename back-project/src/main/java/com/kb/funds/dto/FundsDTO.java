package com.kb.funds.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundsDTO {
    private Long id; // ID 필드 추가

    private String fundFnm;

    // 기준가
    private BigDecimal gijunGa;

    // 기준일
    private LocalDate gijunYmd;

    // 3개월 수익률
    private BigDecimal suikRt3;

    // 1년 수익률
    private BigDecimal suikRt12;

    // 투자 위험 등급
    private int investGrade;

    // 총 보수
    private BigDecimal feeTot;

    // 순 자산
    private BigDecimal SEOLJ_AEK;

    // 벤치마크
    private String bmNm;
}
