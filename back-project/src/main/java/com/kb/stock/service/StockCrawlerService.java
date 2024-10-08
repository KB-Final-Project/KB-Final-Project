//package com.kb.stock.service;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.springframework.stereotype.Service;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import java.io.IOException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@Service
//public class StockCrawlerService {
//    private static final Logger logger = LoggerFactory.getLogger(StockCrawlerService.class);
//
//
//
//    private static final String KOSPI_URL = "https://finance.naver.com/sise/sise_index.naver?code=KOSPI";
//    private static final String KOSDAQ_URL = "https://finance.naver.com/sise/sise_index.naver?code=KOSDAQ";
//    private static final String KOSPI200_URL = "https://finance.naver.com/sise/sise_index.naver?code=KPI200";
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    public StockCrawlerService() {
//        objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, false);
//    }
//
//    public String getKOSPI() throws IOException {
//        return getIndexFromUrl(KOSPI_URL, "코스피");
//    }
//
//    public String getKOSDAQ() throws IOException {
//        return getIndexFromUrl(KOSDAQ_URL, "코스닥");
//    }
//
//    public String getKOSPI200() throws IOException {
//        return getIndexFromUrl(KOSPI200_URL, "코스피200");
//    }
//
//    private String getIndexFromUrl(String url, String indexName) throws IOException {
//        try {
//            Document doc = Jsoup.connect(url)
//                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
//                    .get();
//
//            Element nowValue = doc.selectFirst("#now_value");   // 현재 지수
//            Element changeValueAndRate = doc.selectFirst("#change_value_and_rate");  // 변동 값과 변동률
//            Element quant = doc.selectFirst("#quant");  // 거래량
//            Element amount = doc.selectFirst("#amount"); // 거래대금
//
//            ObjectNode jsonNode = objectMapper.createObjectNode();
//            jsonNode.put(indexName, nowValue != null ? nowValue.text() : "N/A");
//            jsonNode.put("변동", changeValueAndRate != null ? changeValueAndRate.text() : "N/A");
//            jsonNode.put("거래량", quant != null ? quant.text() : "N/A");
//            jsonNode.put("거래대금", amount != null ? amount.text() : "N/A");
//
//            return objectMapper.writeValueAsString(jsonNode);
//
//        } catch (IOException e) {
//            throw new IOException(indexName + " 데이터를 가져오는 데 실패했습니다: " + e.getMessage(), e);
//        }
//    }
//
//    private String getTextSafely(Element parent, String selector) {
//        Element element = parent.selectFirst(selector);
//        if (element == null) {
//            logger.warn("Element not found for selector: {}", selector);
//            return "N/A";
//        }
//        return element.text();
//    }
//}