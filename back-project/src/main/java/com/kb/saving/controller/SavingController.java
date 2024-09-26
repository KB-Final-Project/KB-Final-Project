package com.kb.saving.controller;

import com.kb.saving.dto.Saving;
import com.kb.saving.dto.SavingListDTO;
import com.kb.saving.service.SavingService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/saving")
@RequiredArgsConstructor
@Slf4j
@Api(value = "SavingController", tags = "적금")

public class SavingController {
    private final SavingService service;

    @GetMapping
    public ResponseEntity<List<SavingListDTO>> getDepositListDefault() {
        return ResponseEntity.ok(service.getProductList(2, 36));
    }

    @GetMapping("/{saveTerm}")
    public ResponseEntity<List<SavingListDTO>> getDepositList(@PathVariable int saveTerm) {
        return ResponseEntity.ok(service.getProductList(2, saveTerm));
    }

    @GetMapping("/detail/{savingId}")
    public ResponseEntity<Saving> getDepositProductById(@PathVariable int savingId) {
        return ResponseEntity.ok(service.getProductDetail(2, savingId));
    }

}
