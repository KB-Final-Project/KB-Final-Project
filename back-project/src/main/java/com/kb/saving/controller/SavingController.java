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
@RequestMapping("/saving")
@RequiredArgsConstructor
@Slf4j
@Api(value = "SavingController", tags = "적금")

public class SavingController {
    private final SavingService service;

    @GetMapping("")
    public ResponseEntity<List<Saving>> getSavingList(){
        List<Saving> list = service.getSavingList();
        return ResponseEntity.ok(list);
    }
}
