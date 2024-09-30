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
    private Long id;
    private String name;
    private String type;
    private Double amount;
}
