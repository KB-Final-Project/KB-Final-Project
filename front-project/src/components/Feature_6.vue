<template>
  <section>
    <br><br><br><br><br><br>
    <div class="f6_trendMain">
      <div class="trendGraph">
        <div class="btn1">
          <ul>
            <li>
              <button class="fundBtn">FUND</button>
            </li>
            <li>
              <h3>삼성전자삼성전자</h3>
            </li>
            <li>
              기준가
            </li>
            <li>
              <i style="color: blue;">▼</i>0.03(-0.00%)
            </li>
            <li>
              <h4>1,322.07원</h4>
            </li>
            <li>
              수익률 <h4>12%</h4>
            </li>
          </ul>
        </div>
        <div class="btn1">
          <ul>
            <li>
              <button class="fundBtn">FUND</button>
            </li>
            <li>
              <h3>삼성전자삼성전자</h3>
            </li>
            <li>
              기준가
            </li>
            <li>
              <i style="color: blue;">▼</i>0.03(-0.00%)
            </li>
            <li>
              <h4>1,322.07원</h4>
            </li>
            <li>
              수익률 <h4>12%</h4>
            </li>
          </ul>
        </div>
        <div class="btn1">
          <ul>
            <li>
              <button class="fundBtn">FUND</button>
            </li>
            <li>
              <h3>삼성전자삼성전자</h3>
            </li>
            <li>
              기준가
            </li>
            <li>
              <i style="color: blue;">▼</i>0.03(-0.00%)
            </li>
            <li>
              <h4>1,322.07원</h4>
            </li>
            <li>
              수익률 <h4>12%</h4>
            </li>
          </ul>
        </div>
      </div>

      <!-- trendMenu -->
      <div class="trendMenu">
        <button class="trendBtn">국내펀드</button>
        <h1 class="d-inline">트렌드<br></h1>
        <h1 class="d-inline m-l">한눈에 보기</h1><br>
        <br><h3>전체 운용상품 보러가기 ></h3>
        <img src="/img/emoji/fund4.PNG" class="trendImage">
      </div>
    </div>
    <br><br><br><br>
    <br><br><br><br><br>
  </section>
</template>

<style scoped>

.fundBtn{
  width: 60px;
  height:20px;
  margin: 5px;
  padding: 0;
  font-size: 15px;
  border: 1px solid black;
  border-radius: 20px;
  color: #0f9d58;
}
.btn1 ul{
  list-style: none;
  margin: 0;
  padding: 10px;
}

.f6_trendMain {
  font-family: J3;
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

.f6_trendMain h1 {
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
  letter-spacing: -1px;
  z-index: 2;
}

.trendImage {
  width: 200px;
  margin-top: -40%;
  margin-left: 75%;
  z-index: -1;
}

.trendGraph {
  position: relative;
  display: flex;
  flex-direction: column;
  width: 300px;
  margin-left: 100px;
}

.trendGraph .btn1 {
  position: absolute;
  width: 250px;
  height:250px;
}

.trendGraph .btn1:nth-child(1) {
  bottom: 0;
  left: 50%;
  transform: translateX(0%);
}

.trendGraph .btn1:nth-child(2) {
  top: 25px;
  left: 50%;;
  transform: translateX(0%);
}

.trendGraph .btn1:nth-child(3) {
  top: 50%;
  right: 60%;
  transform: translateY(-50%);
}

.trendGraph span {
  font-size: 20px;
  font-weight: 700;
}

.trendGraph div {
  background-color: rgba(217, 217, 217, 0.3);
  border-radius: 30px;
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

.m-l{
  margin-left: 25px;
}

h3 {
  margin-left: 5px;
  margin-top: 20px;
}

.trendMenu {
  font-family: J5;
}
</style>

<script setup lang="ts">
</script>
