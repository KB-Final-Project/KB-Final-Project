package com.kb.saving.dto;

import com.kb.board.dto.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class SavingFilterRequestDTO {
    private String searchValue;
    private List<String> bankIdList;
    private Integer saveTerm;
    private int page = 1;
    private String interestRateType = "단리";

    public SavingParam toSavingParam(){
        return SavingParam.builder()
                .searchValue(searchValue)
                .bankIdList(bankIdList)
                .saveTerm(saveTerm)
                .page(page)
                .interestRateType(interestRateType)
                .build();
    }

}
