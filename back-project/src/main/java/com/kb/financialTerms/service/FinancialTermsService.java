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
                        // 제목 가져오기
                        Element linkElement = termElement.select("a").first();
                        String termName = linkElement != null ? linkElement.text() : null;

                        // 형제 요소에서 내용 가져오기
                        Element descriptionElement = termElement.nextElementSibling(); // 다음 형제 요소
                        String termDescription = descriptionElement != null && "info2".equals(descriptionElement.className()) ?
                                descriptionElement.text() : null;

                        // TermsDTO 객체 생성
                        TermsDTO termsDTO = new TermsDTO();
                        termsDTO.setTermName(termName);
                        termsDTO.setTermDescription(termDescription); // 설명 추가

                        // 리스트에 추가
                        pageTermsList.add(termsDTO);
                    }

                    return pageTermsList;
                } catch (Exception e) {
                    e.printStackTrace();
                    return pageTermsList; // 빈 리스트를 반환
                }
            });

            futures.add(future);

            // 최대 페이지 수 설정
            if (page >= 30) {
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
