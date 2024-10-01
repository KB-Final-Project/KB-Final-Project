package com.kb.funds.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.funds.dto.FundsDTO;
import com.kb.funds.dto.WeightDTO; // 추가
import com.kb.funds.mapper.FundsMapper;
import lombok.RequiredArgsConstructor;
<<<<<<< Updated upstream
=======
import org.openqa.selenium.WebElement;
import org.springframework.scheduling.annotation.Scheduled;
>>>>>>> Stashed changes
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FundsService {

    private final FundsMapper fundsMapper;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper; // ObjectMapper 추가


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
                        fundsMapper.insertFund(fund);
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

<<<<<<< Updated upstream
=======
    private List<WeightDTO> crawlStructureWeights(FundsDTO fund) {
        List<WeightDTO> weights = new ArrayList<>();

        // Selenium 사용하여 하위 요소 크롤링
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver"); // ChromeDriver 경로 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // 브라우저를 표시하지 않으려면 이 줄을 추가합니다.
        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.samsungfund.com/fund/product/detail.do?id=" + fund.getId()); // 상세 페이지 URL로 변경
            List<WebElement> liElements = driver.findElements(By.cssSelector(".structure-weight__list ul li")); // ul 아래의 li 요소 선택

            for (WebElement liElement : liElements) {
                String category = liElement.getText(); // li의 텍스트
                List<WebElement> spanElements = liElement.findElements(By.tagName("span")); // 각 li 아래의 span 요소들

                for (WebElement spanElement : spanElements) {
                    String value = spanElement.getText(); // span 값
                    weights.add(new WeightDTO(category, value)); // WeightDTO 객체 생성 후 리스트에 추가
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        return weights;
    }

//    @Scheduled(fixedRate = 3600000) // 1시간마다 실행
//    public void scheduleCrawl() {
//        crawlAndSaveFunds();
//    }

>>>>>>> Stashed changes
    public List<FundsDTO> searchFunds(String keyword) {
        return fundsMapper.searchFunds(keyword);
    }

    public List<FundsDTO> findAllFunds() {
        return fundsMapper.findAllFunds();
    }
}
