package com.kb.stock.mapper;

import com.kb.stock.dto.StockDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StockMapper {

    @Insert("INSERT INTO stock (stock_code, stock_name, current_price, price_change, price_change_pct, high_price, low_price, opening_price, volume, timestamp) " +
            "VALUES (#{stockCode}, #{stockName}, #{currentPrice}, #{priceChange}, #{priceChangePct}, #{highPrice}, #{lowPrice}, #{openingPrice}, #{volume}, NOW())")
    void insertStock(StockDTO stock);

    @Select("SELECT * FROM stock ORDER BY timestamp DESC")
    List<StockDTO> selectAllStocks();

    @Update("UPDATE stock SET current_price=#{currentPrice}, price_change=#{priceChange}, price_change_pct=#{priceChangePct}, high_price=#{highPrice}, low_price=#{lowPrice}, volume=#{volume}, timestamp=NOW() " +
            "WHERE stock_code=#{stockCode}")
    void updateStock(StockDTO stock);

    // 주식 코드만 가져오는 메서드 (700개 종목 가져오기)
    @Select("SELECT stock_code FROM stock")
    List<String> selectAllStockCodes();

    @Select("SELECT * FROM stock WHERE stock_code = #{stockCode}")
    StockDTO selectStockByCode(String stockCode);
}
