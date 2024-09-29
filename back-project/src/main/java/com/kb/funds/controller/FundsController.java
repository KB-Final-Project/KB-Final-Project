package com.kb.funds.controller;

import com.kb.funds.dto.FundsDTO;
import com.kb.funds.service.FundsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/funds")
public class FundsController {

    private final FundsService fundsService;

    @GetMapping("/crawl")
    public String crawlFunds() {
        fundsService.crawlAndSaveFunds();
        return "Funds crawled and saved successfully!";
    }

    @GetMapping("/search")
    public List<FundsDTO> searchFunds(@RequestParam String keyword) {
        return fundsService.searchFunds(keyword);
    }
}
