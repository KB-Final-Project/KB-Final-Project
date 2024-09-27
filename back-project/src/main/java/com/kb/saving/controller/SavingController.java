package com.kb.saving.controller;

import com.kb.saving.dto.Saving;
import com.kb.saving.dto.SavingListDTO;
import com.kb.saving.service.SavingService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/saving")
@RequiredArgsConstructor
@Slf4j
@Api(value = "SavingController", tags = "적금")

public class SavingController {
    private final SavingService service;

    @GetMapping("")
    public ResponseEntity<List<SavingListDTO>> getDepositListDefault(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "bankId", required = false) String bankId,
            @RequestParam(value = "saveTerm", required = false) Integer saveTerm) {

        return ResponseEntity.ok(service.getProductList(2, search, bankId, saveTerm));
    }

    @GetMapping("/detail/{savingId}")
    public ResponseEntity<Saving> getDepositProductById(@PathVariable int savingId) {
        return ResponseEntity.ok(service.getProductDetail(2, savingId));
    }

}
