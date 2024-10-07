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
}
