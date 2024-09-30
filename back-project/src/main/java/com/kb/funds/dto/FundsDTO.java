package com.kb.funds.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundsDTO {
    private String fundFnm;
    private double gijunGa;
    private double suikRt3;
    private double suikRt12;
}
