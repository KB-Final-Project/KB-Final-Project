<template>
  <footer>
    <div class="footer">
      <div class="container">
        <ul class="top-row">
          <li class="invetiInfo">
            <h5>서울특별시 서초구 서초대로 99길 12 Around</h5>
            <h5>CS센터 1222-1111 (상담시간 : 평일 오전 9:00~오후 5:00)</h5>
            <h5>서울특별시 서초구 서초대로 99길 12 Around</h5>
          </li>
          <li class="footerKakaoLogo">
            <img src="/img/kakaotalk.png" alt="Kakao Talk Logo">
          </li>
        </ul>
        <br><br>
        <ul class="bottom-row">
          <li class="d-inline">
            <img class="footerLogo" src="/img/inveti.png" alt="Inveti Logo">
          </li>
          <li class="footerInfo d-inline">
            <span>회사소개</span>
            <span>|</span>
            <span><a href="/privacyPage">개인정보처리방침</a></span>
            <span>|</span>
            <span>자주 묻는 질문</span>
            <span>|</span>
            <span>© Around Corp.</span>
          </li>
        </ul>
        <br><br>
      </div>
    </div>
  </footer>
</template>
<style>
footer {
  width: 100%;
  display: flex;
  justify-content: center; /* 수평 가운데 정렬 */
  align-items: flex-start; /* 상단 정렬 */
  font-family: J3;
}

.footer {
  width: 100%;
  background-color: rgba(249, 250, 251, 1);
  padding-top: 5%;
  padding-left: 20%; /* 상하좌우 여백을 줘서 글씨가 1/4 위치에 오도록 설정 */
}

.top-row,
.bottom-row {
  list-style: none; /* 기본 리스트 스타일 제거 */
  padding: 0; /* 패딩 제거 */
  margin: 0; /* 마진 제거 */
  display: flex; /* Flexbox를 사용하여 가로 배치 */
  align-items: center; /* 수직 중앙 정렬 */
}

.footer h5 {
  margin: 5px 0; /* h5 사이의 간격 조정 */
  font-weight: 500;
}

.footerLogo {
  margin-left: auto; /* 왼쪽 공간을 자동으로 채워서 오른쪽으로 이동 */
  margin-right: 100px; /* 오른쪽 여백 추가 */
  width: 100px;
}

.footerInfo span {
  margin: 10px;
  font-size: 20px;
}

.footer a{
  text-decoration: none;
  color: black;
  font-weight: 600;
}

.footerKakaoLogo {
  margin-left: 30%; /* 왼쪽 공간을 자동으로 채워서 오른쪽으로 이동 */
}
</style>

<script setup>
</script>
