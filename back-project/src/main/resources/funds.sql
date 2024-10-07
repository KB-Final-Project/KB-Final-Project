CREATE TABLE funds (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,               -- ID 필드
                       fundFnm VARCHAR(255),               -- 기준가
                       gijunGa DECIMAL(19, 4),            -- 기준일 (기준가는 일반적으로 소수점을 가질 수 있음)
                       gijunYmd DATE,                      -- 기준일 (LocalDate에 해당)
                       suikRt1 DECIMAL(19, 4),            -- 1개월 수익률
                       suikRt3 DECIMAL(19, 4),            -- 3개월 수익률
                       suikRt6 DECIMAL(19, 4),            -- 6개월 수익률
                       suikRt12 DECIMAL(19, 4),           -- 1년 수익률
                       investGrade INT,                -- 투자 위험 등급
                       feeTot DECIMAL(19, 4),              -- 총 유동
                       bmNm VARCHAR(255)                   -- 벤치마크
);