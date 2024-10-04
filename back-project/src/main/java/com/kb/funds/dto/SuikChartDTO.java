package com.kb.funds.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("GIJUN_YMD") // JSON 필드 매핑
    private String gijunYmd;

    @JsonProperty("BM_SUIK_JISU")
    private double bmSuikJisu;

    @JsonProperty("SILH_SUIK_RT")
    private double silhSuikRt;

    @JsonProperty("SEOLJ_AEK")
    private double seoljAek;
}
