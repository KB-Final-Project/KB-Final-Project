package com.kb.funds.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kb.funds.dto.FundsDTO;
import com.kb.funds.mapper.FundsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FundsService {

    private final FundsMapper fundsMapper;
    private final RestTemplate restTemplate;
    private final Gson gson;

    public void crawlAndSaveFunds() {
        int pageNo = 1;
        boolean hasMoreFunds = true;

        while (hasMoreFunds) {
            List<FundsDTO> funds = crawlFundsFromWebsite(pageNo);

            if (funds.isEmpty()) {
                hasMoreFunds = false;
            } else {
                for (FundsDTO fund : funds) {
                    try {
                        if (fundsMapper.exists(fund.getId())) { // ID가 존재하는지 확인
                            fundsMapper.updateFund(fund); // 업데이트
                        } else {
                            fundsMapper.insertFund(fund); // 삽입
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                pageNo++;
            }
        }
    }

    private List<FundsDTO> crawlFundsFromWebsite(int pageNo) {
        String url = "https://www.samsungfund.com/api/v1/fund/product.do?graphTerm=1&orderBy=DESC&orderByType=SUIK_RT3&pageNo=" + pageNo;

        // JSON 데이터를 가져오기
        String jsonResponse = restTemplate.getForObject(url, String.class);

        // JSON 데이터를 DTO로 변환
        Type fundListType = new TypeToken<List<FundsDTO>>() {}.getType();
        List<FundsDTO> fundList = gson.fromJson(jsonResponse, fundListType);
        System.out.println("Crawled Funds: " + fundList);
        return fundList;
    }

//    @Scheduled(fixedRate = 3600000) // 1시간마다 실행
//    public void scheduleCrawl() {
//        crawlAndSaveFunds();
//    }

    public List<FundsDTO> searchFunds(String keyword) {
        return fundsMapper.searchFunds(keyword);
    }

    public List<FundsDTO> findAllFunds() {
        return fundsMapper.findAllFunds();
    }
}
