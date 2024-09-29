package com.kb.funds.service;

import com.kb.funds.dto.FundsDTO;
import com.kb.funds.mapper.FundsMapper;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FundsService {

    private final FundsMapper fundsMapper;

    public void crawlAndSaveFunds() {
        int pageNo = 1;
        boolean hasMoreFunds = true;

        while (hasMoreFunds) {
            List<FundsDTO> funds = crawlFundsFromWebsite(pageNo);

            // 크롤링한 펀드가 없으면 종료
            if (funds.isEmpty()) {
                hasMoreFunds = false;
            } else {
                for (FundsDTO fund : funds) {
                    fundsMapper.insertFund(fund);
                }
                pageNo++; // 다음 페이지로 이동
            }
        }
    }


    private List<FundsDTO> crawlFundsFromWebsite(int pageNo) {
        List<FundsDTO> fundList = new ArrayList<>();
        String url = "https://www.samsungfund.com/api/v1/fund/product.do?graphTerm=1&orderBy=DESC&orderByType=SUIK_RT3&pageNo=" + pageNo;

        try {
            Document doc = Jsoup.connect(url).get();
            Elements strongElements = doc.select(".prod__cont strong");

            for (Element strong : strongElements) {
                FundsDTO fund = new FundsDTO();
                fund.setName(strong.text());
                fundList.add(fund);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fundList;
    }

    public List<FundsDTO> searchFunds(String keyword) {
        return fundsMapper.searchFunds(keyword);
    }

    public List<FundsDTO> findAllFunds() {
        return fundsMapper.findAllFunds();
    }
}
