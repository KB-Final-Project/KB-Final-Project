package com.kb.exchange.service;

import com.kb.exchange.dto.Exchange;
import com.kb.exchange.dto.ExchangeApiResponse;
import com.kb.exchange.dto.ExchangeDailyDTO;
import com.kb.exchange.dto.ExchangeParam;
import com.kb.exchange.mapper.ExchangeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;  // java.sql.Date 임포트
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
@Service
@RequiredArgsConstructor
@PropertySource({"classpath:/application.properties"})
public class ExchangeService {
    private static final String API_URL = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON";
    @Value("${EXCHANGE_API_KEY}") String API_KEY;
    @Value("${EXCHANGE_DATA_TYPE}") String DATA_TYPE;

    private static final Map<String, Integer> CURRENCY_MAP = new HashMap<>();
    static {
        CURRENCY_MAP.put("USD", 1);
        CURRENCY_MAP.put("JPY(100)", 2);
        CURRENCY_MAP.put("EUR", 3);
        CURRENCY_MAP.put("GBP", 4);
        CURRENCY_MAP.put("AUD", 5);
        CURRENCY_MAP.put("NZD", 6);
        CURRENCY_MAP.put("CAD", 7);
        CURRENCY_MAP.put("SGD", 8);
        CURRENCY_MAP.put("HKD", 9);
        CURRENCY_MAP.put("CHF", 10);
        CURRENCY_MAP.put("THB", 11);
    }

    private final ExchangeMapper mapper;

    public void fetchAndSaveExchangeRates() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);

        for (int i = 0; i < 1; i++) {
            String searchDate = dateFormat.format(calendar.getTime());

            String apiUrl = API_URL + "?authkey=" + API_KEY + "&searchdate=" + searchDate + "&data=" + DATA_TYPE;
            ExchangeApiResponse[] responses = restTemplate.getForObject(apiUrl, ExchangeApiResponse[].class);

            if (responses != null && responses.length > 0) {
                for (ExchangeApiResponse response : responses) {
                    String curUnit = response.getCurUnit();
                    Integer curNm = CURRENCY_MAP.get(curUnit);
                    if(curNm != null){
                        // Calendar에서 java.sql.Date로 변환
                        Date exchangeRateDate = new Date(calendar.getTimeInMillis());
                        saveExchangeData(curNm, response, exchangeRateDate);
                    }
                    else {
                        log.warn("일치하는 환율 없음");
                    }
                }
            } else {
                log.warn("데이터 없음");
            }
            calendar.add(Calendar.DATE, -1);
        }
    }

    @Scheduled(cron = "0 0 13 * * *")
    public void fetchAndSaveExchangeRatesDaily() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // 토요일, 일요일, 공휴일 확인
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            return;
        }

        if (dayOfWeek == Calendar.MONDAY) {
            for (int i = 0; i < 3; i++) {
                fetchAndSaveExchangeRatesForSpecificDay(calendar);
                calendar.add(Calendar.DATE, -1);
            }
        } else {
            fetchAndSaveExchangeRatesForSpecificDay(calendar);
        }
    }

    public void fetchAndSaveExchangeRatesForSpecificDay(Calendar calendar) {
        RestTemplate restTemplate = new RestTemplate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String searchDate = dateFormat.format(calendar.getTime());

        String apiUrl = API_URL + "?authkey=" + API_KEY + "&searchdate=" + searchDate + "&data=" + DATA_TYPE;

        try {
            // API 호출
            ExchangeApiResponse[] responses = restTemplate.getForObject(apiUrl, ExchangeApiResponse[].class);

            if (responses != null && responses.length > 0) {
                for (ExchangeApiResponse response : responses) {
                    String curUnit = response.getCurUnit();
                    if (curUnit != null) {
                        // Calendar에서 java.sql.Date로 변환
                        Date exchangeRateDate = new Date(calendar.getTimeInMillis());
                        // 데이터를 저장하는 로직
                        saveExchangeData(CURRENCY_MAP.get(curUnit), response, exchangeRateDate);
                    } else {
                        log.warn("일치하는 환율 없음");
                    }
                }
            } else {
                log.warn("응답 데이터가 없음: 날짜");
            }

        } catch (RestClientException e) {
            log.error("환율 API 호출 중 오류 발생 : " + e);

        } catch (Exception e) {
            log.error("알 수 없는 오류 발생 : " + e);
        }
    }

    @Transactional
    public void saveExchangeData(Integer currencyId, ExchangeApiResponse response, Date exchangeRateDate) {
        Exchange exchange = new Exchange();
        exchange.setCurrencyId(currencyId);
        exchange.setReceivingPrice(response.getTtb());
        exchange.setSendingPrice(response.getTts());
        exchange.setBasePrice(response.getDealBasR());
        exchange.setExchangeRateDate(exchangeRateDate);

        log.info(exchange);
        mapper.insertExchangeRate(exchange);
    }

    public List<ExchangeDailyDTO> getDailyExchange(){
        List<ExchangeDailyDTO> list = mapper.getDailyExchange();
        log.info(list);
        return list;
    }

    public List<Exchange> getExchangeListByTerm(ExchangeParam exchangeParam){
        return mapper.getExchangeListByTerm(exchangeParam);
    }
}
