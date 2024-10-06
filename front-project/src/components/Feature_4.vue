<template>
  <section>
    <br><br><br><br>
    <div class="f4_trendMain">
      <!-- trendMenu -->
      <div class="trendMenu">
          <button class="trendBtn  p-1">국내주식</button>
          <h1 class="d-inline">트렌드<br></h1>
          <h1 class="d-inline m-l">한눈에 보기</h1><br>
          <br><h3>전체 운용상품 보러가기 ></h3>
        <img src="/img/trend.png" class="trendImage">
      </div>

      <!-- trendGraph -->
      <div class="trendGraph">
        <div class="btn1 ">
          <img src="/img/imsi.png">
          <span>삼성전자</span>
          <img src="/img/graph.png">
        </div>
        <div class="btn2 ">
          <img src="/img/imsi.png">
          <span>유한양행</span>
          <img src="/img/graph.png">
        </div>
        <div class="btn1 ">
          <img src="/img/imsi.png">
          <span>SK 하이닉스</span>
          <img src="/img/graph.png">
        </div>
        <div class="btn2 ">
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
  font-size: 50px;
  font-weight: 700;
  border: none;
  border-radius: 30px;
  background-color: rgba(241, 250, 247, 1);
  color: rgba(68, 140, 116, 1);
  z-index: 2;
}

.trendImage {
  margin-top: -50%;
  margin-left: 80%;
  z-index: -1;
}

.trendGraph {
  display: flex;
  font-family: J3;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  width: 400px;
  margin-left: 60px;
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

.m-l{
  margin-left: 25px;
}

h3 {
  margin-left: 25px;
  margin-top: 20px;
}

.trendMenu {
  font-family: J5;
}

</style>

<script setup lang="ts">
</script>
