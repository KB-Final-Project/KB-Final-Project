package com.kb.gold.controller;

import com.kb.gold.service.GoldService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gold")
@Api(value = "GoldController", tags = "금 정보")
@PropertySource({"classpath:/application.properties"})
public class GoldController {
    private final GoldService goldService;

    @GetMapping("/fetchGoldData")
    public ResponseEntity<String> fetchGoldData(@RequestParam String day) {
        try {
            goldService.fetchAndSaveGoldDataFromSpecificDate();
            return ResponseEntity.ok("금 데이터가 성공적으로 가져와지고 저장되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("데이터 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
