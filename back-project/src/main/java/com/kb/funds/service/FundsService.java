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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FundsService {

    private final ObjectMapper objectMapper;
    private final FundsMapper fundsMapper;
    private final RestTemplate restTemplate;
    private final SuikChartMapper suikChartMapper;

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
                        List<SuikChartDTO> suikCharts = crawlSuikCharts(fund);

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
                                for (SuikChartDTO suikChart : suikCharts) {
                                    if (fund.getId() == null) {
                                        System.out.println("Fund ID is null for fund: " + fund);
                                        continue;
                                    }

                                    suikChart.setFundId(fund.getId()); // fundId 설정
                                    System.out.println("Inserting SuikChart: " + suikChart);
                                    System.out.println("Preparing to insert SuikChart with Fund ID: " + suikChart.getFundId());

                                    try {
                                        suikChartMapper.insertSuikChart(suikChart);
                                        System.out.println("Inserted SuikChart: " + suikChart);
                                    } catch (Exception e) {
                                        System.err.println("Error inserting SuikChart: " + suikChart);
                                        e.printStackTrace();
                                    }
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
                        for (JsonNode chart : suikChartNode) {
                            // 차트 데이터 로직 추가
                        }
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

        return fundList;
    }


    private List<SuikChartDTO> crawlSuikCharts(FundsDTO fund) {
        List<SuikChartDTO> suikCharts = new ArrayList<>(fund.getSuikCharts());
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
