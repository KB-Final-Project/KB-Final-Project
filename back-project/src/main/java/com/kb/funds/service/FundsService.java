package com.kb.funds.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.funds.dto.FundsDTO;
import com.kb.funds.dto.FundsDetailDTO;
import com.kb.funds.dto.SuikChartDTO;
import com.kb.funds.mapper.FundsMapper;
import com.kb.funds.mapper.SuikChartMapper;
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
                        logger.info("Processing fund: {}", fund);

                        if (fund.getId() == null) {
                            fundsMapper.insertFund(fund);
                            logger.info("Inserted new fund ID: {}", fund.getFundCd());

                            // 펀드 ID 업데이트 확인
                            if (fund.getId() == null) {
                                logger.error("Fund ID is still null after insert.");
                                return; // 오류 처리
                            }

                            // 상세 데이터 수집 및 저장
                            List<FundsDetailDTO> fundDetails = crawlFundDetails(fund);
                            saveFundDetails(fundDetails, fund.getId());

                            // SuikChart 저장
                            List<SuikChartDTO> suikCharts = fund.getSuikChart();
                            saveSuikCharts(suikCharts, fund.getId());
                        } else {
                            fundsMapper.updateFund(fund);
                            suikChartMapper.deleteSuikChartByFundId(fund.getId()); // 기존 데이터 삭제

                            // 상세 데이터 수집 및 저장
                            List<FundsDetailDTO> fundDetails = crawlFundDetails(fund);
                            saveFundDetails(fundDetails, fund.getId());

                            // SuikChart 저장
                            List<SuikChartDTO> suikCharts = fund.getSuikChart();
                            saveSuikCharts(suikCharts, fund.getId());
                        }
                    } catch (Exception e) {
                        logger.error("Error occurred while processing fund ID: {}", fund.getId(), e);
                    }
                }
                pageNo++;
            }
        }
    }

    private void saveFundDetails(List<FundsDetailDTO> fundDetails, Long fundId) {
        if (fundDetails != null && !fundDetails.isEmpty()) {
            fundsMapper.deleteFundDetailsByFundId(fundId); // 기존 상세 정보 삭제
            for (FundsDetailDTO detail : fundDetails) {
                detail.setFundId(fundId); // 펀드 ID 설정
            }
            fundsMapper.insertFundDetails(fundDetails);
        } else {
            logger.warn("No fund details available to save for fund ID: {}", fundId);
        }
    }

    private void saveSuikCharts(List<SuikChartDTO> charts, Long fundId) {
        if (charts != null && !charts.isEmpty()) {
            for (SuikChartDTO chart : charts) {
                chart.setFundId(fundId);
                logger.debug("Preparing to save SuikChart with fund ID: {}, bmSuikJisu: {}, silhSuikRt: {}, seoljAek: {}",
                        chart.getFundId(), chart.getBmSuikJisu(), chart.getSilhSuikRt(), chart.getSeoljAek());
            }
            suikChartMapper.insertSuikCharts(charts);
        } else {
            logger.warn("No charts available to save for fund ID: {}", fundId);
        }
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
                JsonNode suikChartNode = fundNode.get("suikChart");

                if (suikChartNode != null && suikChartNode.isArray() && suikChartNode.size() > 0) {
                    List<SuikChartDTO> suikCharts = new ArrayList<>();
                    for (JsonNode chart : suikChartNode) {
                        SuikChartDTO suikChart = objectMapper.treeToValue(chart, SuikChartDTO.class);
                        suikChart.setFundId(fundId);
                        suikCharts.add(suikChart);
                    }
                    fund.setSuikChart(suikCharts);
                }
            }
        }

        return fundList;
    }

    private List<FundsDetailDTO> crawlFundDetails(FundsDTO fund) {
        List<FundsDetailDTO> fundDetails = new ArrayList<>();
        if (fund == null || fund.getId() == null) {
            logger.warn("Invalid fund or fund ID is null, skipping fund details for fund: {}", fund != null ? fund.getFundCd() : "null");
            return fundDetails;
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
                return fundDetails; // gijunYmd가 없으면 빈 리스트 반환
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
                return fundDetails; // gijunYmd가 null일 경우 빈 리스트 반환
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

                FundsDetailDTO fundDetail = new FundsDetailDTO(); // FundsDetailDTO 객체 생성
                fundDetail.setFundId(fund.getId()); // Fund ID 설정

                String 평가액Str = columns.get(1).text().replaceAll(",", "");
                String 비중Str = columns.get(2).text().replaceAll("%", "").trim();

                logger.debug("Columns: {}, 평가액Str: {}, 비중Str: {}", columns, 평가액Str, 비중Str);

                double 평가액 = 0.0;
                double 비중 = 0.0;

                if ("-".equals(평가액Str) || 평가액Str.isEmpty()) {
                    평가액 = 0.0;
                    logger.warn("평가액이 '-'로 설정되어 0.0으로 변환되었습니다. fund ID: {}", fund.getId());
                } else {
                    try {
                        평가액 = Double.parseDouble(평가액Str);
                    } catch (NumberFormatException e) {
                        logger.warn("Invalid format for 평가액: {}", 평가액Str);
                    }
                }

                if ("-".equals(비중Str) || 비중Str.isEmpty()) {
                    비중 = 0.0;
                    logger.warn("비중이 '-'로 설정되어 0.0으로 변환되었습니다. fund ID: {}", fund.getId());
                } else {
                    try {
                        비중 = Double.parseDouble(비중Str) / 100;
                    } catch (NumberFormatException e) {
                        logger.warn("Invalid format for 비중: {}", 비중Str);
                    }
                }

                fundDetail.setGijunYmd(gijunYmd); // 기준일 설정
                fundDetail.setCategory(columns.get(0).text()); // 카테고리 설정
                fundDetail.setEvaluationAmount(평가액); // 평가액 설정
                fundDetail.setWeight(비중); // 비중 설정

                fundDetails.add(fundDetail); // 리스트에 추가
            }
        } catch (Exception e) {
            logger.error("Error occurred while crawling detailed data for fund ID: {}. Error: {}", fund.getId(), e);
        } finally {
            driver.quit();
        }
        return fundDetails; // List<FundsDetailDTO> 반환
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

    public List<FundsDTO> searchFunds(String keyword) {
        return fundsMapper.searchFunds(keyword);
    }

    public List<FundsDTO> findAllFunds() {
        return fundsMapper.findAllFunds();
    }
}
