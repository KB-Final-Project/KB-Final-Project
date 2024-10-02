package com.kb.funds.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeightDTO {
    private String category; // li 항목의 이름
    private String value; // span의 값
}