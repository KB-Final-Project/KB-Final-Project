<template>
  <section>
    <br><br><br><br>
    <div class="f4_trendMain">
      <!-- trendMenu -->
      <div class="trendMenu">
          <button class="trendBtn d-inline text-start p-1">국내주식</button>
          <h1 class="d-inline">트렌드<br>한눈에 보기</h1><br>
          <br><h3>전체 운용상품 보러가기 ></h3>
        <img src="/img/trend.png" class="trendImage">
      </div>

      <!-- trendGraph -->
      <div class="trendGraph">
        <div class="btn1 text-start">
          <img src="/img/imsi.png">
          <span>삼성전자</span>
          <img src="/img/graph.png">
        </div>
        <div class="btn2 text-start">
          <img src="/img/imsi.png">
          <span>유한양행</span>
          <img src="/img/graph.png">
        </div>
        <div class="btn1 text-start">
          <img src="/img/imsi.png">
          <span>SK 하이닉스</span>
          <img src="/img/graph.png">
        </div>
        <div class="btn2 text-start">
          <img src="/img/imsi.png">
          <span>LG 전자</span>
          <img src="/img/graph.png">
        </div>
      </div>
    </div>
    <br><br><br><br>
  </section>
</template>

<style scoped>
.f4_trendMain {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 1200px;
  margin: 0 auto;
  padding: 0 50px;
}

.m-1 {
  z-index: 99;
  padding: 20px;
  display: flex;
  flex-direction: column;
}

.f4_trendMain h1 {
  font-size: 50px;
  font-weight: 700;
  letter-spacing: -1px;
  z-index: 4;
}

.trendBtn {
  width: 220px;
  height: 70px;
  font-size: 50px;
  font-weight: 700;
  border: none;
  border-radius: 30px;
  background-color: rgba(241, 250, 247, 1);
  color: rgba(68, 140, 116, 1);
  letter-spacing: -1px;
  z-index: 2;
}

.trendImage {
  margin-top: -40%;
  margin-left: 70%;
  z-index: -1;
}

.trendGraph {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  width: 400px; /* 적절한 폭 설정 */
  margin-left: 60px; /* trendMenu와 간격 */
}

.trendGraph span {
  font-size: 20px;
  font-weight: 700;
}

.trendGraph div {
  width: 370px;
  height: 80px;
  background-color: rgba(217, 217, 217, 0.3);
  border-radius: 30px;
  margin: 10px 0;
  display: flex;
  align-items: center;
  padding: 0 20px;
}

.trendGraph div img {
  width: 50px;
  height: 50px;
  margin: 0 27px;
  border-radius: 50%;


}

.trendGraph div:nth-child(odd) {
  margin-left: -60px;
}

.trendGraph div:nth-child(even) {
  margin-left: 60px;
}

</style>

<script setup lang="ts">
</script>
