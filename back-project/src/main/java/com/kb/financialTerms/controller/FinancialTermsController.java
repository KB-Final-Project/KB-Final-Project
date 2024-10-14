package com.kb.financialTerms.controller;

import com.kb.financialTerms.dto.TermsDTO;
import com.kb.financialTerms.service.FinancialTermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/terms")
public class FinancialTermsController {
    private final FinancialTermsService financialTermsService;

    @GetMapping("/save-terms")
    public String getTerms() {
        financialTermsService.crawlAndSaveTerms();
        return "저장 완료";
    }
}