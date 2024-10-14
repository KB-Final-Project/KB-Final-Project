package com.kb.saving.controller;

import com.kb.saving.dto.*;
import com.kb.saving.service.SavingService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saving")
@RequiredArgsConstructor
@Slf4j
@Api(value = "SavingController", tags = "적금")

public class SavingController {
    private final SavingService service;

    @PostMapping("")
    public ResponseEntity<SavingListResponseDTO> getSavingListDefault(
            @RequestBody SavingFilterRequestDTO filterRequest) {
        SavingParam savingParam = filterRequest.toSavingParam();
        savingParam.setFinCategoryId(2);
        SavingListResponseDTO response = service.getProductList(savingParam);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/detail/{savingId}")
    public ResponseEntity<Saving> getSavingProductById(@PathVariable int savingId,
                                                       @RequestParam(required = false) Integer userId,
                                                       @RequestParam(required = false) String wmtiType){
        log.info(String.valueOf(userId));
        log.info(wmtiType);
        if(userId == null || wmtiType == null || wmtiType.isEmpty()){
            return ResponseEntity.ok(service.getProductDetail(2, savingId));
        }
        service.logProductViewAndUpdateViewCount(SavingProductViewLogDto
                .builder()
                .savingId(savingId)
                .userId(userId)
                .wmtiType(wmtiType)
                .build());
        return ResponseEntity.ok(service.getProductDetail(2, savingId));
    }


    @GetMapping("/top")
    public ResponseEntity<List<SavingListDTO>> getTopSavings() {
        List<SavingListDTO> response = service.getTopSavingsProductList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category")
    public ResponseEntity<SavingCategory> getSavingCategory(){
        return ResponseEntity.ok(service.getCategoryList());
    }
}
