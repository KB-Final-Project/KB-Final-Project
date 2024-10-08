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
    List<StockDTO> selectAllStocks();  // 기존에 누락된 부분을 다시 명시

    @Select("SELECT stock_code FROM stock_codes")
    List<String> selectAllStockCodes();

    @Insert({
            "INSERT INTO stock (stock_code, stock_name, current_price, price_change, price_change_pct, high_price, low_price, opening_price, volume, industry, last_updated)",
            "VALUES (#{stockCode}, #{stockName}, #{currentPrice}, #{priceChange}, #{priceChangePct}, #{highPrice}, #{lowPrice}, #{openingPrice}, #{volume}, #{industry}, NOW())",
            "ON DUPLICATE KEY UPDATE",
            "stock_name = #{stockName},",
            "current_price = #{currentPrice},",
            "price_change = #{priceChange},",
            "price_change_pct = #{priceChangePct},",
            "high_price = #{highPrice},",
            "low_price = #{lowPrice},",
            "opening_price = #{openingPrice},",
            "volume = #{volume},",
            "industry = #{industry},",
            "last_updated = NOW()"
    })

    void upsertStock(StockDTO stock);

    @Select("SELECT * FROM stock WHERE stock_code = #{stockCode}")
    StockDTO selectStockByCode(String stockCode);

    @Select("SELECT stock_code FROM stock WHERE last_updated < DATE_SUB(NOW(), INTERVAL 5 MINUTE)")
    List<String> selectStaleStockCodes();
}
