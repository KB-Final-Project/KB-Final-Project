package com.kb.funds.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< Updated upstream
=======
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

>>>>>>> Stashed changes
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FundsDTO {
    private String fundFnm;
<<<<<<< Updated upstream
    private double gijunGa;
    private double suikRt3;
    private double suikRt12;
=======

    // 기준가
    private BigDecimal gijunGa;

    // 기준일
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate gijunYmd;

    // 1개월 수익률
    private String suikRt1;

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

    private List<WeightDTO> structureWeights; // WeightDTO를 담는 리스트
>>>>>>> Stashed changes
}
