CREATE TABLE funds (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,               -- ID 필드
                       fundFnm VARCHAR(255),               -- 기준가
                       gijunGa DECIMAL(19, 4),            -- 기준일 (기준가는 일반적으로 소수점을 가질 수 있음)
                       gijunYmd DATE,                      -- 기준일 (LocalDate에 해당)
                       suikRt1 DECIMAL(19, 4),            -- 1개월 수익률
                       suikRt3 DECIMAL(19, 4),            -- 3개월 수익률
                       suikRt12 DECIMAL(19, 4),           -- 1년 수익률
                       investGrade INT,                -- 투자 위험 등급
                       feeTot DECIMAL(19, 4),              -- 총 유동
                       bmNm VARCHAR(255),                   -- 벤치마크
                       fundCd VARCHAR(100)					-- 펀드코드
);

CREATE TABLE SuikChart (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY, -- 수익 차트 ID (기본 키)
                           fundId BIGINT NOT NULL,                -- 펀드 ID (외래 키)
                           gijunYmd DATE NOT NULL,                -- 기준일 (YYYY-MM-DD 형식)
                           bmSuikJisu DOUBLE,                     -- 벤치마크 수익 지수
                           silhSuikRt DOUBLE,                     -- 실현 수익률
                           seoljAek DOUBLE,                       -- 순 자산
                           FOREIGN KEY (fundId) REFERENCES funds(id) -- 외래 키 제약 조건
);

CREATE TABLE FundsDetail (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 자동 생성 ID
                             fundId BIGINT NOT NULL,                -- 관련된 fund ID
                             gijunYmd DATE NOT NULL,                -- 기준일
                             evaluationAmount DOUBLE NOT NULL,      -- 평가액
                             weight DOUBLE NOT NULL,                  -- 비중
                             category VARCHAR(255),                   -- 카테고리
                             FOREIGN KEY (fundId) REFERENCES Funds(id) -- Funds 테이블과의 외래 키 제약조건
);