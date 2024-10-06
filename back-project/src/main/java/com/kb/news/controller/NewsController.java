package com.kb.news.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@Api(tags = "뉴스 API", description = "네이버 뉴스 검색 API를 이용한 뉴스 조회")
public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    @GetMapping("/api/news")
    @ApiOperation(value = "뉴스 검색", notes = "주어진 쿼리로 뉴스를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 뉴스를 조회함"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 내부 오류")
    })
    public ResponseEntity<String> getNews(
            @ApiParam(value = "검색할 뉴스 키워드", example = "금융", defaultValue = "금융")
            @RequestParam(value = "query", required = false, defaultValue = "금융") String query,
            @ApiParam(value = "표시할 뉴스 개수", example = "10", defaultValue = "10")
            @RequestParam(required = false, defaultValue = "10") int display) {

        logger.info("Received request for news. Query: {}, Display: {}", query, display);

        // 쿼리 인코딩
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + encodedQuery + "&display=" + display;

        // RestTemplate 사용
        RestTemplate restTemplate = new RestTemplate();

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        // HTTP 요청 엔티티 생성
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // URI 생성
            URI uri = new URI(apiURL);
            // API 호출
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

            logger.info("API request successful. Status: {}", response.getStatusCode());
            return response;
        } catch (URISyntaxException e) {
            logger.error("URI Syntax error", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error occurred while fetching news", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}