package com.kb.financialTerms.service;


import com.kb.financialTerms.dto.TermsDTO;
import com.kb.financialTerms.mapper.FinancialTermsMapper;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class FinancialTermsService {
    private final FinancialTermsMapper financialTermsMapper;

    public List<TermsDTO> crawlAndGetTerms() {
        List<TermsDTO> termsList = new ArrayList<>();
        String baseUrl = "https://www.fsc.go.kr/in090301?curPage=";
        List<CompletableFuture<List<TermsDTO>>> futures = new ArrayList<>();

        for (int page = 1; ; page++) {
            final int currentPage = page;
            CompletableFuture<List<TermsDTO>> future = CompletableFuture.supplyAsync(() -> {
                List<TermsDTO> pageTermsList = new ArrayList<>();
                String url = baseUrl + currentPage;

                try {
                    Document doc = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0")
                            .get();

                    Elements termElements = doc.select("div.cont > div.subject");

                    if (termElements.isEmpty()) {
                        return pageTermsList; // 빈 리스트 반환
                    }

                    for (Element termElement : termElements) {
                        TermsDTO termsDTO = new TermsDTO();
                        // 필요한 데이터 추출
                        termsDTO.setTermName(termElement.text()); // 제목 세팅
                        pageTermsList.add(termsDTO);
                    }

                    return pageTermsList;
                } catch (Exception e) {
                    e.printStackTrace();
                    return pageTermsList; // 빈 리스트 반환
                }
            });

            futures.add(future);

            // 최대 페이지 수 설정
            if (page >= 10) {
                break; // 최대 10페이지 크롤링
            }
        }

        for (CompletableFuture<List<TermsDTO>> future : futures) {
            List<TermsDTO> pageTermsList = future.join();
            termsList.addAll(pageTermsList);
        }

        System.out.println("Terms List: " + termsList);
        return termsList;
    }
}
