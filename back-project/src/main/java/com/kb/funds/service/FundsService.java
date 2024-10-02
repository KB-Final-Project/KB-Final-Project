package com.kb.funds.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.funds.dto.FundsDTO;
import com.kb.funds.dto.SuikChartDTO;
import com.kb.funds.mapper.FundsMapper;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class FundsService {

    private final ObjectMapper objectMapper;
    private final FundsMapper fundsMapper;
    private final RestTemplate restTemplate;


    // 생성자에서 모든 필드를 초기화
    public FundsService(ObjectMapper objectMapper, FundsMapper fundsMapper, RestTemplate restTemplate) {
        this.objectMapper = objectMapper;
        this.fundsMapper = fundsMapper;
        this.restTemplate = restTemplate;
    }

    // 클래스 필드에 WebDriver를 두어 재사용
    private WebDriver driver;

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
//                        List<SuikChartDTO> suikCharts = crawlSuikCharts(fund); // 수익 차트 데이터 크롤링

                        if (fundsMapper.existsById(fund.getId())) { // 펀드가 존재하는지 확인
                            fundsMapper.updateFund(fund); // 존재하면 업데이트

                            // 기존 수익 차트 데이터 삭제
//                            fundsMapper.deleteSuikChartByFundId(fund.getId());

                            // 새로운 수익 차트 데이터 삽입
//                            for (SuikChartDTO suikChart : suikCharts) {
//                                fundsMapper.insertSuikChart(suikChart);
//                            }
                        } else {
                            fundsMapper.insertFund(fund); // 존재하지 않으면 삽입

                            // 새로 생성된 펀드 ID를 수익 차트에 설정
//                            for (SuikChartDTO suikChart : suikCharts) {
//                                suikChart.setFundId(fund.getId()); // 펀드 ID 설정
//                                fundsMapper.insertSuikChart(suikChart);
//                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                pageNo++;
            }
        }
    }

    private List<FundsDTO> crawlFundsFromWebsite(int pageNo) throws JsonProcessingException {
        String url = "https://www.samsungfund.com/api/v1/fund/product.do?graphTerm=1&orderBy=DESC&orderByType=SUIK_RT3&pageNo=" + pageNo;

        // JSON 데이터를 가져오기
        String jsonResponse = restTemplate.getForObject(url, String.class);

        // JSON 데이터를 DTO로 변환
        List<FundsDTO> fundList = objectMapper.readValue(jsonResponse, new TypeReference<List<FundsDTO>>() {});
        System.out.println("Crawled Funds: " + fundList);
        return fundList;
    }

//    @PostConstruct
//    public void init() {
//        System.setProperty("webdriver.chrome.driver", "C:\\Tools\\chromedriver\\chromedriver.exe");
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
//        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");
//
//        driver = new ChromeDriver(options);
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//    }
//
//    @PreDestroy
//    public void cleanup() {
//        if (driver != null) {
//            driver.quit();
//        }
//        try {
//            DriverManager.deregisterDriver(DriverManager.getDriver("jdbc:mysql://127.0.0.1:3306/scoula_db"));
//        } catch (SQLException e) {
//            // 로깅 라이브러리 사용
//            e.printStackTrace();
//        }
//    }

//    private List<SuikChartDTO> crawlSuikCharts(FundsDTO fund) {
//        List<SuikChartDTO> suikCharts = new ArrayList<>();
//
//        try {
//            driver.get("https://www.samsungfund.com/fund/product/detail.do?id=" + fund.getId());
//
//            List<WebElement> chartElements = driver.findElements(By.cssSelector(".prd-item__summary fund")); // 예시 CSS 선택자
//
//            for (WebElement chartElement : chartElements) {
//                String gijunYmd = chartElement.findElement(By.cssSelector("selector_for_gijunYmd")).getText();
//                BigDecimal seoljAek = new BigDecimal(chartElement.findElement(By.cssSelector(".title_30-20 val")).getText());
//
//                SuikChartDTO suikChart = new SuikChartDTO();
//                suikChart.setFundId(fund.getId());
//                suikChart.setGijunYmd(gijunYmd);
//                suikChart.setSeoljAek(seoljAek);
//
//                suikCharts.add(suikChart);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return suikCharts;
//    }




    public List<FundsDTO> searchFunds(String keyword) {
        return fundsMapper.searchFunds(keyword);
    }

    public List<FundsDTO> findAllFunds() {
        return fundsMapper.findAllFunds();
    }
}
