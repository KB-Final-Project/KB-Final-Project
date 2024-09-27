package com.kb.saving.controller;

import com.kb.saving.dto.Saving;
import com.kb.saving.dto.SavingListResponseDTO;
import com.kb.saving.dto.SavingParam;
import com.kb.saving.service.SavingService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/deposit")
@RequiredArgsConstructor
@Slf4j
@Api(value = "DepositController", tags = "예금")

public class DepositController {
    private final SavingService service;
    @GetMapping("")
    public ResponseEntity<SavingListResponseDTO> getDepositListDefault(
            @RequestParam(value = "searchValue", required = false) String searchValue,
            @RequestParam(value = "bankId", required = false) Integer bankId,
            @RequestParam(value = "saveTerm", defaultValue = "36") Integer saveTerm,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "interestRateType", defaultValue = "단리") String interestRateType){

        SavingParam savingParam = new SavingParam();
        savingParam.setSearchValue(searchValue);
        savingParam.setBankId(bankId);
        savingParam.setSaveTerm(saveTerm);
        savingParam.setFinCategoryId(1);
        savingParam.setPage(page);
        savingParam.setInterestRateType(interestRateType);

        SavingListResponseDTO response = service.getProductList(savingParam);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/detail/{savingId}")
    public ResponseEntity<Saving> getDepositProductById(@PathVariable int savingId) {
        return ResponseEntity.ok(service.getProductDetail(1, savingId));
    }
}
