# 📈 iNVeTI
- KB국민은행에서 주관한 KB IT's Your Life 교육과정에서 진행한 금융 웹프로젝트
![image](https://github.com/user-attachments/assets/2e3b5b73-7cba-4d86-b4f0-2330a6551328)

### iNVeTI
: 투자 입문자들을 위한 통합된 금융 정보와 각 성향에 맞춘 투자 상품 제공

## 📆 개발 기간
2024.08.28 ~ 2024.10.16

## 👨‍👨‍👧‍👦 팀 구성
웹 프론트엔드 3, 웹 백엔드 3

## 📌 주요 기능
1. 개인 투자성향 분석 기반 투자상품 추천 서비스
2. 10개의 질문을 바탕으로 16가지 금융 mbti 분석
3. 성향 기반으로 예적금, 주식, 펀드, ISA 등 다양한 금융상품 맞춤 추천
4. 커뮤니티, 용어사전, 금, 환율에 대한 부가적인 정보 제공


## 🛠 기술 스택
![image](https://github.com/user-attachments/assets/cbe3a4b5-2c42-45e9-9e4b-4ce4c1c61df4)


## 💡 구현 설명
1. 국민은행 WMTI 기반으로 질문 구성
2. 한국투자증권 API를 사용하여 웹소켓 10개는 실시간 변동, 나머지는 DB에 저장 후 업데이트
3. FinanceDataReader 사용하여 5년치 주가 차트 제공 
4. 네이버 뉴스 API를 사용하여 각 금융 상품에 대한 뉴스 제공
5. 각 주가지수 크롤링
6. 금융감독원 API를 사용하여 예적금 정보제공
7. 예적금을 정렬할 때 동일한 투자 성향을 가진 사람들이 많이 조회한 순서대로 정렬
8. 환율 크롤링 데이터를 각 나라 별로 제공, 각 은행별로 환전수수료율 제공

## 📲 업데이트 예정
- [ ] 투자성향테스트 결과 페이지에 금융 상품 추가
- [ ] 주가 지수 차트 수정
- [ ] 네이버 뉴스 성향 별로 제공
- [ ] 펀드 크롤링 방식 수정
- [ ] 카카오 로그인 수정

## 📱 실행 화면
![image](https://github.com/user-attachments/assets/cc2e66ab-69ad-4020-b67d-9a97376ef11c)
![image](https://github.com/user-attachments/assets/4e5239f6-6bf6-45d0-9c58-890244bfe635)
![image](https://github.com/user-attachments/assets/fe639ebc-8f63-4a1a-b6ac-fe2f7ae5218f)
![image](https://github.com/user-attachments/assets/95e5fa83-7671-4920-b966-f5e7fb51ae0c)
![image](https://github.com/user-attachments/assets/d89dbbf6-68f9-4227-ae49-b9d8968514d4)
![image](https://github.com/user-attachments/assets/301ff5c0-bcca-4278-88df-9c3c439683f0)
![image](https://github.com/user-attachments/assets/69f72fde-87f1-4818-b29e-d5c82849f276)
![image](https://github.com/user-attachments/assets/197bf004-db96-4a7c-a42d-65060729f911)
![image](https://github.com/user-attachments/assets/f81a9db7-07c6-4c42-8124-bd90bcb7acf5)
![image](https://github.com/user-attachments/assets/b7563208-c9aa-4c6d-94e4-0cfbbea01b57)








