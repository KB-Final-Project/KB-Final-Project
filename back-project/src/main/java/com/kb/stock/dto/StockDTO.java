//package com.kb.stock.dto;
//
//import lombok.Data;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@Data
//public class StockDTO {
//    private Long id;
//    private String stockCode;            // 종목 코드 (PDNO)
//    private String stockName;            // 종목명 (상품명)
//    private BigDecimal currentPrice;     // 현재가
//    private BigDecimal priceChange;      // 가격 변화
//    private BigDecimal priceChangePct;   // 등락률
//    private BigDecimal highPrice;        // 최고가
//    private BigDecimal lowPrice;         // 최저가
//    private BigDecimal openingPrice;     // 시가
//    private Long volume;                 // 거래량
//    private LocalDateTime lastUpdated;   // 마지막 업데이트 시간
//    private String industry;             // 업종
//    private BigDecimal w52Hgpr;          // 52주 최고가
//    private BigDecimal w52Lwpr;          // 52주 최저가
//    private BigDecimal htsAvls;          // HTS 시가총액
//
//    //주식상품종목
//    private String prdtTypeCd;           // 상품유형코드
//    private String prdtName120;          // 상품명120
//    private String prdtAbrvName;         // 상품약어명
//    private String prdtEngName;          // 상품영문명
//    private String prdtEngName120;       // 상품영문명120
//    private String prdtEngAbrvName;      // 상품영문약어명
//    private String stdPdno;              // 표준상품번호
//    private String shtnPdno;             // 단축상품번호
//    private String prdtSaleStatCd;       // 상품판매상태코드
//    private String prdtRiskGradCd;       // 상품위험등급코드
//    private String prdtClsfCd;           // 상품분류코드
//    private String prdtClsfName;         // 상품분류명
//    private String saleStrtDt;           // 판매시작일자
//    private String saleEndDt;            // 판매종료일자
//    private String wrapAsstTypeCd;       // 랩어카운트자산유형코드
//    private String ivstPrdtTypeCd;       // 투자상품유형코드
//    private String ivstPrdtTypeCdName;   // 투자상품유형코드명
//    private String frstErlmDt;           // 최초등록일자
//}
