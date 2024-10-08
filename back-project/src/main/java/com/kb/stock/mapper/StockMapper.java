package com.kb.stock.mapper;

import com.kb.stock.dto.StockDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StockMapper {

    @Select("SELECT stock_name FROM stock_codes WHERE stock_code = #{stockCode}")
    String selectStockNameByCode(String stockCode);

    @Select("SELECT 1")
    int checkDatabaseConnection();

    @Select("SELECT * FROM stock ORDER BY last_updated DESC")
    List<StockDTO> selectAllStocks();  // 전체 종목 목록을 조회

    @Select("SELECT stock_code FROM stock_codes")
    List<String> selectAllStockCodes();

    @Insert({
            "INSERT INTO stock (",
            "  stock_code, stock_name, current_price, price_change, price_change_pct, high_price, low_price, opening_price, volume, industry, last_updated,",
            "  prdt_type_cd, prdt_name120, prdt_abrv_name, prdt_eng_name, prdt_eng_name120, prdt_eng_abrv_name,",
            "  std_pdno, shtn_pdno, prdt_sale_stat_cd, prdt_risk_grad_cd, prdt_clsf_cd, prdt_clsf_name, sale_strt_dt, sale_end_dt, wrap_asst_type_cd,",
            "  ivst_prdt_type_cd, ivst_prdt_type_cd_name, frst_erlm_dt",
            "  w52_hgpr, w52_lwpr, hts_avls",
            ") VALUES (",
            "  #{stockCode}, #{stockName}, #{currentPrice}, #{priceChange}, #{priceChangePct}, #{highPrice}, #{lowPrice}, #{openingPrice}, #{volume}, #{industry}, NOW(),",
            "  #{prdtTypeCd}, #{prdtName120}, #{prdtAbrvName}, #{prdtEngName}, #{prdtEngName120}, #{prdtEngAbrvName},",
            "  #{stdPdno}, #{shtnPdno}, #{prdtSaleStatCd}, #{prdtRiskGradCd}, #{prdtClsfCd}, #{prdtClsfName}, #{saleStrtDt}, #{saleEndDt}, #{wrapAsstTypeCd},",
            "  #{ivstPrdtTypeCd}, #{ivstPrdtTypeCdName}, #{frstErlmDt}",
            "  #{w52Hgpr}, #{w52Lwpr}, #{htsAvls}",

            ") ON DUPLICATE KEY UPDATE ",
            "  stock_name = #{stockName},",
            "  current_price = #{currentPrice},",
            "  price_change = #{priceChange},",
            "  price_change_pct = #{priceChangePct},",
            "  high_price = #{highPrice},",
            "  low_price = #{lowPrice},",
            "  opening_price = #{openingPrice},",
            "  volume = #{volume},",
            "  industry = #{industry},",

            "  w52_hgpr = #{w52Hgpr},",
            "  w52_lwpr = #{w52Lwpr},",
            "  hts_avls = #{htsAvls},",
            "  prdt_type_cd = #{prdtTypeCd},",
            "  prdt_name120 = #{prdtName120},",
            "  prdt_abrv_name = #{prdtAbrvName},",
            "  prdt_eng_name = #{prdtEngName},",
            "  prdt_eng_name120 = #{prdtEngName120},",
            "  prdt_eng_abrv_name = #{prdtEngAbrvName},",
            "  std_pdno = #{stdPdno},",
            "  shtn_pdno = #{shtnPdno},",
            "  prdt_sale_stat_cd = #{prdtSaleStatCd},",
            "  prdt_risk_grad_cd = #{prdtRiskGradCd},",
            "  prdt_clsf_cd = #{prdtClsfCd},",
            "  prdt_clsf_name = #{prdtClsfName},",
            "  sale_strt_dt = #{saleStrtDt},",
            "  sale_end_dt = #{saleEndDt},",
            "  wrap_asst_type_cd = #{wrapAsstTypeCd},",
            "  ivst_prdt_type_cd = #{ivstPrdtTypeCd},",
            "  ivst_prdt_type_cd_name = #{ivstPrdtTypeCdName},",
            "  frst_erlm_dt = #{frstErlmDt},",
            "  last_updated = NOW()"
    })
    void upsertStock(StockDTO stock);  // 종목 데이터를 삽입하거나 업데이트

    @Select("SELECT * FROM stock WHERE stock_code = #{stockCode}")
    StockDTO selectStockByCode(String stockCode);  // 특정 종목 조회

    @Select("SELECT stock_code FROM stock WHERE last_updated < DATE_SUB(NOW(), INTERVAL 5 MINUTE)")
    List<String> selectStaleStockCodes();  // 갱신되지 않은 종목 코드 조회
}