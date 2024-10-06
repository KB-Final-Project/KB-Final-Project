package com.kb.funds.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kb._config.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuikChartDTO {
    private Long fundId;

    @JsonDeserialize(using = LocalDateDeserializer.class) //
    @JsonProperty("GIJUN_YMD") // JSON 필드 매핑
    private LocalDate gijunYmd;

    @JsonProperty("BM_SUIK_JISU")
    private double bmSuikJisu;

    @JsonProperty("SILH_SUIK_RT")
    private double silhSuikRt;

    @JsonProperty("SEOLJ_AEK")
    private double seoljAek;

    @JsonProperty("CATEGORY") // 구분을 나타내는 필드
    private String category;

    @JsonProperty("EVALUATIONAMOUNT") // 새로운 평가액 필드
    private double evaluationAmount; // 평가액(억원)

    @JsonProperty("WEIGHT") // 새로운 비중 필드
    private double weight; // 비중(%)
}
