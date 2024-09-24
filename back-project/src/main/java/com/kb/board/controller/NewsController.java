package com.kb.board.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@CrossOrigin(origins = "http://localhost:8081") // Vue.js 개발 서버 주소
public class NewsController {

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    @GetMapping("/api/news")
    public ResponseEntity<String> getNews(
            @RequestParam(value = "query", required = false, defaultValue = "금융") String query,
            @RequestParam(required = false, defaultValue = "10") int display) {

        // 쿼리 인코딩 처리
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + encodedQuery + "&display=" + display;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            URI uri = new URI(apiURL);
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

            return response;
        } catch (URISyntaxException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
