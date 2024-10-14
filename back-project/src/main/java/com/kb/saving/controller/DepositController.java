package com.kb.saving.controller;

import com.kb.saving.dto.*;
import com.kb.saving.service.SavingService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/api/deposit")
@RequiredArgsConstructor
@Slf4j
@Api(value = "DepositController", tags = "예금")

public class DepositController {
    private final SavingService service;

    @PostMapping("")
    public ResponseEntity<SavingListResponseDTO> getDepositListDefault(
            @RequestBody SavingFilterRequestDTO filterRequest) {
        SavingParam savingParam = filterRequest.toSavingParam();
        savingParam.setFinCategoryId(1);
        SavingListResponseDTO response = service.getProductList(savingParam);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/top")
    public ResponseEntity<List<SavingListDTO>> getTopDeposits() {
        List<SavingListDTO> response = service.getTopProductList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/detail/{savingId}")
    public ResponseEntity<Saving> getDepositProductById(@PathVariable int savingId,
                                                        @RequestParam(required = false) int userId,
                                                        @RequestParam(required = false) String wmtiType) {
        if(wmtiType != null && !wmtiType.isEmpty()){
            service.logProductViewAndUpdateViewCount(SavingProductViewLogDto
                    .builder()
                    .savingId(savingId)
                    .userId(userId)
                    .wmtiType(wmtiType)
                    .build());
        }
        return ResponseEntity.ok(service.getProductDetail(1, savingId));
    }

    @GetMapping("/category")
    public ResponseEntity<SavingCategory> getDepositCategory(){
        return ResponseEntity.ok(service.getCategoryList());
    }

}
