package com.kb.exchange.controller;


import com.kb.exchange.service.ExchangeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exchange")
@RequiredArgsConstructor
@Slf4j
@Api(value = "ExchangeController", tags = "예금")
public class ExchangeController {
    private final ExchangeService service;
//
//    @PostMapping("/fetch-exchange-rates")
//    public String fetchExchangeRates() {
//        service.fetchAndSaveExchangeRates();
//        return "저장 완료";
//    }
}
