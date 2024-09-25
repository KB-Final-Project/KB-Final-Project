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
        String url = "https://www.fsc.go.kr/in090301";

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .get();

            Elements termElements = doc.select("div.cont div.subject");
            for (Element termElement : termElements) {
                // a 태그에서 제목과 링크 가져오기
                Element linkElement = termElement.select("a").first();
                if (linkElement != null) {
                    String termName = linkElement.text(); // 용어 이름
                    String termLink = linkElement.attr("href"); // 링크

                    // 필요한 경우, 절대 URL로 변환
                    String absoluteLink = url + termLink;

                    // TermsDTO 객체 생성
                    TermsDTO termsDTO = new TermsDTO();
                    termsDTO.setTermName(termName);
                    termsDTO.setTermDescription(absoluteLink); // 설명 대신 링크를 사용할 경우

                    // 리스트에 추가
                    termsList.add(termsDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // 예외 처리
        }
        System.out.println("Terms List: " + termsList);
        return termsList;
    }
}
