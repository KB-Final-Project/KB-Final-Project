# 📈 iNVeTI
- KB국민은행에서 주관한 KB IT's Your Life 교육과정에서 진행한 금융 웹프로젝트
![image](https://github.com/user-attachments/assets/c10bb112-2a17-45ac-8202-745e2b7f268d)


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
![image](https://github.com/user-attachments/assets/013f2f88-d64e-4cc3-88b9-c09d2405c628)



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
![image](https://github.com/user-attachments/assets/c11f8d59-708a-4323-8239-e0f4669107fa)
![image](https://github.com/user-attachments/assets/7b65f493-9119-4c31-b242-52b76515725a)
![image](https://github.com/user-attachments/assets/04887aec-dab0-45be-973f-20959bf8d6a3)
![image](https://github.com/user-attachments/assets/806f4f75-ee59-48b0-b6dd-74262da4baf3)
![image](https://github.com/user-attachments/assets/b78d1f53-8f24-48a6-9334-cf82efc7831f)
![image](https://github.com/user-attachments/assets/1790b8c8-5740-4944-9795-fbdb87801e49)
![image](https://github.com/user-attachments/assets/6e1db672-46a7-41cb-a8f4-ec288110ecbe)
![image](https://github.com/user-attachments/assets/4d6c6af3-c43c-4db9-9139-76b67dbae3b1)
![image](https://github.com/user-attachments/assets/260eb53d-06ed-4d6e-990b-9f15aa054014)
![image](https://github.com/user-attachments/assets/a4c0f549-ef12-45a4-8cda-6b43ad17dae2)








