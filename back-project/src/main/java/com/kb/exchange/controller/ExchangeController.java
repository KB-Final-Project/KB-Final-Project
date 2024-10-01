package com.kb.exchange.controller;


import com.kb.exchange.dto.Exchange;
import com.kb.exchange.dto.ExchangeDailyDTO;
import com.kb.exchange.dto.ExchangeParam;
import com.kb.exchange.service.ExchangeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchange")
@RequiredArgsConstructor
@Slf4j
@Api(value = "ExchangeController", tags = "예금")
public class ExchangeController {
    private final ExchangeService service;

//    @PostMapping("/fetch-exchange-rates")
//    public String fetchExchangeRates() {
//        service.fetchAndSaveExchangeRates();
//        return "저장 완료";
//    }

    @GetMapping("")
    public ResponseEntity<List<ExchangeDailyDTO>> getDailyExchangeList(){
        return ResponseEntity.ok(service.getDailyExchange());
    }

    @GetMapping("/detail") // 1month, 3months, 6months, 1year 변수
    public ResponseEntity<List<Exchange>> getExchangeListByTerm(
            @RequestParam(value = "currenyId", defaultValue = "1") int currenyId,
            @RequestParam(value = "term", defaultValue = "3months") String term){
        ExchangeParam exchangeParam = new ExchangeParam(currenyId, term);
        return ResponseEntity.ok(service.getExchangeListByTerm(exchangeParam));
    }
}
