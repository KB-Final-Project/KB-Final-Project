package com.kb.financialTerms.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class TermsDTO {
    private Integer termId;
    private String termName;
    private String termDescription;
}
