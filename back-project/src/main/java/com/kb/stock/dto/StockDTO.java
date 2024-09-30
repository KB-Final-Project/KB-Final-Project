package com.kb.stock.dto;

public class StockDTO {
    private String code;           // 종목 코드
    private String name;           // 종목 이름
    private String price;          // 현재가
    private String change;         // 전일 대비
    private String changeRate;     // 전일 대비율
    private String tradeVolume;    // 거래량

    // Getter와 Setter 메서드
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getChange() { return change; }
    public void setChange(String change) { this.change = change; }

    public String getChangeRate() { return changeRate; }
    public void setChangeRate(String changeRate) { this.changeRate = changeRate; }

    public String getTradeVolume() { return tradeVolume; }
    public void setTradeVolume(String tradeVolume) { this.tradeVolume = tradeVolume; }
}