package com.kb.funds.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.funds.dto.FundsDTO;
import com.kb.funds.mapper.FundsMapper;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class FundsService {

    private final ObjectMapper objectMapper;
    private final FundsMapper fundsMapper;
    private final RestTemplate restTemplate;
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
                        // Fund ID가 null인 경우, 새 펀드 추가
                        if (fund.getId() == null) {
                            logger.debug("Inserting fund with gijunYmd: {}", fund.getGijunYmd());
                            fundsMapper.insertFund(fund);
//                            logger.info("Inserted new fund: {}", fund.getFundCd());
                        } else {
                            // 기존 펀드 업데이트
                            fundsMapper.updateFund(fund);
                        }

                        // 상세 데이터를 가져와서 펀드 객체에 추가
//                        List<FundsDTO> detailedCharts = crawlDetailedData(fund);
//                        for (FundsDTO detailedChart : detailedCharts) {
//                            // 필요한 필드만 업데이트
//                            fund.setCategory(detailedChart.getCategory());
//                            fund.setEvaluationAmount(detailedChart.getEvaluationAmount());
//                            fund.setWeight(detailedChart.getWeight());
//                        }

                        // 펀드 정보 업데이트 (기본 정보만)
                        logger.debug("Updating fund: {}", fund);
                        fundsMapper.updateFund(fund);

                    } catch (Exception e) {
                        logger.error("Error occurred while processing fund ID: {}", fund.getId(), e);
                    }
                }
                pageNo++;
            }
        }
    }


    private Long generateFundId() {
        // fundId를 생성하는 로직을 구현
        return System.currentTimeMillis(); // 예시: 현재 시간을 사용해 생성
    }

    private List<FundsDTO> crawlFundsFromWebsite(int pageNo) throws JsonProcessingException {
        String url = "https://www.samsungfund.com/api/v1/fund/product.do?graphTerm=1&orderBy=DESC&orderByType=SUIK_RT3&pageNo=" + pageNo;
        String jsonResponse = restTemplate.getForObject(url, String.class);
        List<FundsDTO> fundList = objectMapper.readValue(jsonResponse, new TypeReference<List<FundsDTO>>() {});




        JsonNode rootNode = objectMapper.readTree(jsonResponse);

        for (FundsDTO fund : fundList) {
            Long fundId = fund.getId();

            if (fundId == null) {
                continue;
            }

            JsonNode fundNode = null;
            for (JsonNode node : rootNode) {
                if (node.get("fId").asText().equals(fundId.toString())) {
                    fundNode = node;
                    break;
                }
            }

            if (fundNode != null) {
                System.out.println("gijunYmd: " + fund.getGijunYmd()); // 여기에 추가

                JsonNode suikChartNode = fundNode.get("suikChart");
                if (suikChartNode != null && suikChartNode.isArray() && suikChartNode.size() > 0) {
                    for (JsonNode chart : suikChartNode) {
                        // FundsDTO에 직접 차트 데이터를 추가
//                        String category = chart.get("CATEGORY").asText();
//                        double evaluationAmount = chart.get("EVALUATIONAMOUNT").asDouble();
//                        double weight = chart.get("WEIGHT").asDouble();
//
//                        fund.setCategory(category); // 카테고리 설정
//                        fund.setEvaluationAmount(evaluationAmount); // 평가액 설정
//                        fund.setWeight(weight); // 비중 설정
                    }
                }
            }
        }

        return fundList;
    }



    @Scheduled(fixedRate = 3600000) // 1시간마다 실행
    public void scheduleCrawl() {
        try {
            crawlAndSaveFunds();
        } catch (JsonProcessingException e) {
            logger.error("Error occurred while crawling funds: {}", e.getMessage(), e);
        }
    }

 /*   private List<FundsDTO> crawlDetailedData(FundsDTO fund) {
        List<FundsDTO> suikCharts = new ArrayList<>();
        if (fund == null || fund.getId() == null) {
            logger.error("Invalid fund object or fund ID is null.");
            return suikCharts;
        }

        String fundCd = fund.getFundCd();
        String url = "https://www.samsungfund.com/fund/product/view.do?id=" + fundCd;

        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", "C:/chromeDriver/chromedriver-win64/chromedriver.exe");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.6668.90 Safari/537.36");
        options.addArguments("--verbose");
        options.addArguments("disable-blink-features=AutomationControlled");
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(url);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.assets-weight__table")));

            String htmlResponse = driver.getPageSource();
            Document document = Jsoup.parse(htmlResponse);

            String rawGijunYmdStr = document.select("div.assets-weight__table .noti").text();
            logger.debug("Extracted raw gijunYmdStr: {}", rawGijunYmdStr);

            String gijunYmdStr = null;
            Pattern pattern = Pattern.compile("(\\d{2})\\.\\s*(\\d{1,2})\\.\\s*(\\d{1,2})");
            Matcher matcher = pattern.matcher(rawGijunYmdStr);
            if (matcher.find()) {
                gijunYmdStr = matcher.group(0).replace(" ", "");
                logger.debug("Extracted gijunYmd: {}", gijunYmdStr);
            } else {
                logger.warn("Could not find valid gijunYmd in string: {}", rawGijunYmdStr);
                return suikCharts;
            }

            LocalDate gijunYmd = null;
            if (gijunYmdStr != null && !gijunYmdStr.isEmpty()) {
                try {
                    gijunYmd = LocalDate.parse(gijunYmdStr, DateTimeFormatter.ofPattern("yy.M.d"));
                } catch (DateTimeParseException e) {
                    logger.warn("Invalid gijunYmd format for fund ID: {}. Value: {}", fund.getId(), gijunYmdStr);
                }
            } else {
                logger.warn("Missing gijunYmd for fund ID: {}", fund.getId());
                return suikCharts;
            }

            Elements rows = document.select("div.assets-weight__table tbody tr");
            if (rows.isEmpty()) {
                logger.warn("No rows found in the detailed data for fund ID: {}", fundCd);
            }

            for (Element row : rows) {
                Elements columns = row.select("td");
                logger.debug("Columns: {}", columns.eachText());

                if (columns.size() < 3) {
                    continue;
                }

                FundsDTO suikChart = new FundsDTO();
                suikChart.setFundId(fund.getId());

                String 평가액Str = columns.get(1).text().replaceAll(",", "");
                String 비중Str = columns.get(2).text().replaceAll("%", "").trim();

                double 평가액 = "-".equals(평가액Str) || 평가액Str.isEmpty() ? 0.0 : Double.parseDouble(평가액Str);
                double 비중 = "-".equals(비중Str) || 비중Str.isEmpty() ? 0.0 : Double.parseDouble(비중Str) / 100;

                suikChart.setGijunYmd(gijunYmd);
                suikChart.setCategory(columns.get(0).text());
                suikChart.setEvaluationAmount(평가액);
                suikChart.setWeight(비중);

                suikCharts.add(suikChart);
            }
        } catch (Exception e) {
            logger.error("Error occurred while crawling detailed data for fund ID: {}. Error: {}", fund.getId(), e);
        } finally {
            driver.quit();
        }
        return suikCharts;
    }*/

    public List<FundsDTO> searchFunds(String keyword) {
        return fundsMapper.searchFunds(keyword);
    }

    public List<FundsDTO> findAllFunds() {
        return fundsMapper.findAllFunds();
    }
}
