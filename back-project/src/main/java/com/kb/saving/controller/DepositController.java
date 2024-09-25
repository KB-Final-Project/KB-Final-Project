package com.kb.saving.controller;

import com.kb.saving.dto.Saving;
import com.kb.saving.service.SavingService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/deposit")
@RequiredArgsConstructor
@Slf4j
@Api(value = "DepositController", tags = "예금")

public class DepositController {
    private final SavingService service;

    @GetMapping("")
    public ResponseEntity<List<Saving>> getDepositList(){
        List<Saving> list = service.getDepositList();
        return ResponseEntity.ok(list);
    }
}
