package com.kb.saving.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SavingParam {
    private String searchValue;
    private String interestRateType;
    private Integer bankId;
    private Integer saveTerm;
    private Integer finCategoryId;

    private int page = 1;
    private int limit;
    private int offset;
}
