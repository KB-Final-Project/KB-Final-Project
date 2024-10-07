package com.kb._config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String date = p.getText();
        System.out.println("Parsing date: " + date); // 디버깅 로그


        try {
            // "yyyy.MM.dd" 형식으로 날짜를 파싱하여 LocalDate로 변환
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new IOException("Invalid date format: " + date, e);
        }
    }

//        // 형식이 "yyyyMMdd"일 경우
//        if (date.length() == 8) {
//            return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
//        }
//
//        // 기본 형식은 "yyyy.MM.dd"
//        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
//    }
}
