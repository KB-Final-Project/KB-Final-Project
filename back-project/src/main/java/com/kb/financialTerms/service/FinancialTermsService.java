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

@Service
@RequiredArgsConstructor
public class FinancialTermsService {
    private final FinancialTermsMapper financialTermsMapper;

    public List<TermsDTO> crawlAndGetTerms() {
        List<TermsDTO> termsList = new ArrayList<>();
        String baseUrl = "https://www.fsc.go.kr/in090301?curPage=";
        int page = 1;

        while (true) {
            String url = baseUrl + page;

            try {
                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0")
                        .get();

                Elements termElements = doc.select("div.cont > div.subject");

                if (termElements.isEmpty()) {
                    break; // 더 이상 데이터가 없으면 종료
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
                    termsList.add(termsDTO);
                }

                // 다음 페이지 링크 확인 (next page)
                Element nextPageElement = doc.select("a.next").first(); // "next" 클래스를 가진 링크
                if (nextPageElement == null) {
                    break; // 다음 페이지가 없으면 종료
                }

                page++; // 다음 페이지로 이동
            } catch (Exception e) {
                e.printStackTrace(); // 예외 처리
                break; // 예외 발생 시 종료
            }
        }

        System.out.println("Terms List: " + termsList);
        return termsList;
    }
}
