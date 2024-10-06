package com.kb.funds.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundsDetailDTO {
    private Long id; // 자동 생성 ID
    private Long fundId; // 관련된 fund ID
    private LocalDate gijunYmd; // 기준일
    private double evaluationAmount; // 평가액
    private double weight; // 비중
    private String category; // 카테고리
}