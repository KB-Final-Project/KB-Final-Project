package com.kb.funds.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.funds.dto.FundsDTO;
import com.kb.funds.dto.SuikChartDTO;
import com.kb.funds.mapper.FundsMapper;
import com.kb.funds.mapper.SuikChartMapper;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FundsService {

    private final ObjectMapper objectMapper;
    private final FundsMapper fundsMapper;
    private final RestTemplate restTemplate;
    private final SuikChartMapper suikChartMapper;
    private static final Logger logger = LoggerFactory.getLogger(FundsService.class);


    @Transactional
    public void crawlAndSaveFunds() throws JsonProcessingException {
        int pageNo = 1;
        boolean hasMoreFunds = true;

        while (hasMoreFunds) {
            List<FundsDTO> funds = crawlFundsFromWebsite(pageNo);

            if (funds.isEmpty()) {
                hasMoreFunds = false;
            } else {
                for (FundsDTO fund : funds) {
                    try {
                        System.out.println("Processing fund: " + fund);
                        List<SuikChartDTO> suikCharts = crawlSuikChart(fund);

                        if (fundsMapper.existsById(fund.getId())) {
                            fundsMapper.updateFund(fund);
                            suikChartMapper.deleteSuikChartByFundId(fund.getId());
                        } else {
                            // 새로운 펀드 삽입
                            try {
                                fundsMapper.insertFund(fund);
                                System.out.println("Inserted fund ID: " + fund.getId());

                                // 새로운 수익 차트 데이터 삽입
                                System.out.println("SuikCharts size: " + suikCharts.size());

                                if (fund.getId() == null) {
                                    System.out.println("Fund ID is null for fund: " + fund);
                                    // Fund ID가 null인 경우에 대한 처리 추가
                                    continue;
                                } else {
                                    // 정상적으로 ID가 있는 경우 수익 차트 삽입
                                    for (SuikChartDTO suikChart : suikCharts) {
                                        suikChart.setFundId(fund.getId());
                                    }
                                    suikChartMapper.insertSuikCharts(suikCharts);
                                    System.out.println("Inserted SuikCharts for Fund ID: " + fund.getId());
                                }
                            } catch (Exception e) {
                                System.err.println("Error inserting fund: " + fund);
                                e.printStackTrace();
                                continue;
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Error occurred while processing fund ID: " + fund.getId());
                        e.printStackTrace();
                    }
                }
                pageNo++;
            }
        }
    }

    private List<FundsDTO> crawlFundsFromWebsite(int pageNo) throws JsonProcessingException {
        String url = "https://www.samsungfund.com/api/v1/fund/product.do?graphTerm=1&orderBy=DESC&orderByType=SUIK_RT3&pageNo=" + pageNo;
        String jsonResponse = restTemplate.getForObject(url, String.class);
        System.out.println("JSON Response: " + jsonResponse); // JSON 응답 로그

        List<FundsDTO> fundList = objectMapper.readValue(jsonResponse, new TypeReference<List<FundsDTO>>() {});

        JsonNode rootNode = objectMapper.readTree(jsonResponse);

        for (FundsDTO fund : fundList) {
            Long fundId = fund.getId(); // fund.getId()가 정확한지 확인 필요

            if (fundId == null) {
                System.out.println("Fund ID is null for fund: " + fund);
                continue;
            }

            JsonNode fundNode = null;

            // fundNode를 찾는 과정에서 fundId 대신 fId 사용
            for (JsonNode node : rootNode) {
                if (node.get("fId").asText().equals(fundId.toString())) {
                    fundNode = node;
                    break;
                }
            }

            if (fundNode != null) {
                System.out.println("Fund Node found for fund ID: " + fundId);
                System.out.println("Fund Node: " + fundNode.toString()); // fundNode 내용 로그

                JsonNode suikChartNode = fundNode.get("suikChart");

                if (suikChartNode != null) {
                    System.out.println("suikChartNode found for fund ID: " + fundId);
                    System.out.println("suikChartNode content: " + suikChartNode.toString()); // suikChartNode 내용 로그
                    if (suikChartNode.isArray() && suikChartNode.size() > 0) {
                        System.out.println("suikChartNode has size: " + suikChartNode.size());
                        // 차트 데이터 처리 로직
                        List<SuikChartDTO> suikCharts = new ArrayList<>();
                        for (JsonNode chart : suikChartNode) {
                            SuikChartDTO suikChart = objectMapper.treeToValue(chart, SuikChartDTO.class);

                            // fundId 설정
                            suikChart.setFundId(fundId);
                            suikCharts.add(suikChart);
                        }
                        fund.setSuikChart(suikCharts); // Fund에 suikChart 설정
                    } else {
                        System.out.println("suikChartNode is empty for fund ID: " + fundId);
                    }
                } else {
                    System.out.println("suikChartNode is null for fund ID: " + fundId);
                }
            } else {
                System.out.println("Fund data not found for fund ID: " + fundId);
            }
        }

        fundList.addAll(crawlFundsWithSelenium());
        return fundList;
    }

    private List<FundsDTO> crawlFundsWithSelenium() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        WebDriver driver = new ChromeDriver();
        List<FundsDTO> fundList = new ArrayList<>();

        try {
            driver.get("https://www.samsungfund.com/fund/page");
            // 더보기 버튼 클릭 및 데이터 수집 로직

            // 더보기 버튼 클릭
            boolean hasMore = true;
            while (hasMore) {
                try {
                    WebElement loadMoreButton = driver.findElement(By.cssSelector("selector-for-load-more-button"));
                    loadMoreButton.click();
                    Thread.sleep(2000); // 데이터 로드 대기
                } catch (Exception e) {
                    hasMore = false; // 더 이상 더보기 버튼이 없으면 루프 종료
                }
            }

            // 데이터 수집
            List<WebElement> rows = driver.findElements(By.cssSelector("selector-for-fund-rows"));
            for (WebElement row : rows) {
                FundsDTO fund = new FundsDTO();
                fund.setId(Long.valueOf(row.findElement(By.cssSelector("selector-for-fund-id")).getText()));
                // 나머지 필드도 설정
                fundList.add(fund);
            }

        } finally {
            driver.quit(); // 드라이버 종료
        }

        return fundList;
    }

    @Scheduled(fixedRate = 3600000) // 1시간마다 실행
    public void scheduleCrawl() {
        try {
            logger.info("Scheduled crawl started at: {}", LocalDateTime.now());
            crawlAndSaveFunds();
        } catch (JsonProcessingException e) {
            logger.error("Error occurred while crawling funds: {}", e.getMessage(), e);
        }
    }

    private List<SuikChartDTO> crawlSuikChart(FundsDTO fund) {
        List<SuikChartDTO> suikCharts = new ArrayList<>(fund.getSuikChart());
        suikCharts.forEach(suikChart -> {
            if (fund.getId() != null) {
                suikChart.setFundId(fund.getId());
            }
        });
        return suikCharts;
    }



    public List<FundsDTO> searchFunds(String keyword) {
        return fundsMapper.searchFunds(keyword);
    }

    public List<FundsDTO> findAllFunds() {
        return fundsMapper.findAllFunds();
    }
}
