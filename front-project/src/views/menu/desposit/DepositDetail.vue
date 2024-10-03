<script setup>
import { ref } from 'vue';

const expanded = ref(false);

const toggleText = () => {
  expanded.value = !expanded.value;
};

</script>

<template>
  <div class="container text-center">
    <br><br>
    <div class="savingDetailBox">
      <ul class="text-start">
        <li><img src=""></li>
        <li><h1>상품명</h1></li>
        <li><h1>부제목</h1></li>
        <li><br><br></li>
      </ul>
      <ul class="text-start">
        <li class="d-inline subject2">최고 금리</li>
        <li class="d-inline subject2">기본 금리(36개월 기준)</li>
        <li><br></li>
        <li class="d-inline bestSubject">4.09%</li>
        <li class="d-inline normalSubject"> 3.09%</li>
      </ul>
      <ul class="text-start mt-5">
        <li><router-link to="">oo뱅크에서 보기 <i class="ai-chevron-right"></i></router-link></li>
      </ul>
      <hr class="mt-5 hr"/>
      <div class="text-start m-4">
        <h3>이자 계산기</h3>
        <br><br>
        <div class="calBox">
          <p></p>
        </div>
        <div class="calRate text-center"><br>
          <p class="d-inline">금리 %</p>
        </div>
        <div class="text-center">
          <p class="d-inline">기본 % + </p>
          <div class="calSpecialRate d-inline">
            <p class="d-inline">우대 %</p>
          </div>
        </div><br><br><br>
        <div class="calNumber">
          <table>
            <tbody>
            <tr>
              <td class="cal" rowspan="2">🧮</td>
              <td style="font-size: 15px;">월 만원 적금하면 </td>
            </tr>
            <tr>
              <td style="font-size: 15px; font-weight: 600;">총 세후 이자 원</td>
              <td style="font-size: 30px;"><i class="ai-chevron-right"></i></td>
            </tr>
            </tbody>
          </table>
          <br>
        </div>
        <p style="font-size: 15px; color: #777777;">우대금리가 반영된 금리예요</p>
      </div>
      <table class="calTable text-start">
        <tbody>
        <tr>
          <th>
            <label class="form-check-label" for="customSwitch">달성 시</label>
          </th>
          <td>
            <label class="calPercent" for="customSwitch">22%</label>
          </td>
          <td>
            <div class="form-check form-switch">
              <input type="checkbox" class="form-check-input" id="customSwitch" checked>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
      <hr class="mt-5 hr"/>
      <div class="text-start m-4">
        <img class="d-inline rankMedal" src="/img/emoji/goldmedal.png">
        <table class="d-inline calNumber">
          <tbody>
          <tr>
            <td><h4 class="d-inline rank">예금 최고금리 순위</h4></td>
            <td></td>
          </tr>
          <tr>
            <td><h4 class="d-inline rank">3위</h4></td>
            <td style="font-size: 30px;"><i class="rightArrow ai-chevron-right"></i></td>
          </tr>
          </tbody>
        </table>
      </div>
      <hr class="mt-5 hr"/>
      <div class="moreInfo text-start m-4">
        <h3>상품 정보</h3><br>
        <div class="text-wrapper default-info">
          <ul class="info-list">
            <li>
              <h4>가입 금액</h4>
              <h5></h5>
            </li>
            <li>
              <h4>가입 대상</h4>
              <h5></h5>
            </li>
            <li>
              <h4>가입 방법</h4>
              <h5></h5>
            </li>
          </ul>
          <div v-if="expanded" class="additional-info">
            <ul class="info-list">
              <li>
                <h4>만기 후 이자율</h4>
                <h5></h5>
              </li>
              <li>
                <h4>세제혜택</h4>
                <h5></h5>
              </li>
              <li>
                <h4>예금자보호</h4>
                <h5></h5>
              </li>
            </ul>
          </div>
          <div class="toggle-wrapper">
            <span class="more-text" v-if="!expanded" @click="toggleText">더보기<i class="ai-chevron-down"></i></span>
            <span class="less-text" v-if="expanded" @click="toggleText">줄이기<i class="ai-chevron-up"></i></span>
          </div>
        </div>
      </div>
      <hr class="mt-5 hr"/>
      <br>
      <div class="text-start m-4">
        <h3>기간별 금리</h3><br>
        <table class="dayInfo text-start">
          <tbody>
          <tr>
            <td><h4>기간</h4></td>
            <td><h4>최고금리(기본금리)</h4></td>
          </tr>
          <tr>
            <td><h4>2개월</h4></td>
            <td><h4>23%</h4></td>
          </tr>
          </tbody>
        </table>
      </div>
      <button class="detailMove"><a href="#">상품 페이지로 이동</a></button>
    </div>
  </div>
</template>

<style scoped>
.rightArrow{
  margin-left: 650px;
}

.rank{
  vertical-align: central;
}

.rankMedal{
  width: 60px;
  margin: 10px;
}
.toggle-wrapper {
  display: inline-block;
  text-align: center;
  margin-top: 10px;
}

.info-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.more-text .less-text {
  font-size: 16px;
  color: black;
  cursor: pointer;
}

.moreInfo>span{
  text-align: start;
}

.text-wrapper {
  width: 200px;
}

.text {
  overflow: hidden;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  text-align: justify;
}

.text.expanded {
  display: inline-block;
}

.more-text {
  font-size: 16px;
  color: black;
  cursor: pointer;
}

.less-text {
  font-size: 16px;
  color: black;
  cursor: pointer;
}

.additional-info {
  margin-top: 10px;
  font-size: 14px;
}

.default-info ul {
  padding-left: 0px;
}

#customSwitch{
  width: 40px;
}

.calTable td{
  text-align: right;
  width: 10%;
  padding: 20px;
}

.calTable{
  margin: 20px;
  table-layout: fixed;
  width: 95%;
  border-bottom: 1px solid rgba(180, 178, 178, 0.47);
}

.dayInfo{
  table-layout: fixed;
  width: 100%;
  text-align: left;
}


.calPercent{
  font-weight: 500;
  font-size: 20px;
  color: rgba(67, 140, 116, 1);
}

.form-check-label{
  font-weight: 500;
  font-size: 20px;
  color: black;
}

.cal{
  font-size: 40px;
  width: 20px;
}

.calBox{
  width:120px;
  height: 100px;
  background-color: #4d4f5e;
  border-radius: 30px;
  margin: 0 auto;
  padding: 20px;
}

.calRate{
  font-size: 30px;
  color: rgba(67, 140, 116, 1);
  font-weight : 700;
}

.calSpecialRate{
  color: rgba(67, 140, 116, 1);
}

.container{
  width: 60%;
  padding-top: 80px;
}


.hr{
  width: 96%;
  border: 5px solid rgba(180, 178, 178, 0.47);
}

.subject2{
  font-size: 25px;
  font-weight: bold;
  color: #777777;
  margin-right: 50px;
}

.bestSubject{
  font-size: 35px;
  font-weight: bold;
  color: #8ad3c5;
  margin-right: 50px;
}

.normalSubject{
  font-size: 35px;
  font-weight: bold;
  color: #777777;
  margin-right: 50px;
}

table{
  margin-right:auto;
  width: 70%;
}

hr{
  width: 90%;
  margin: 0px auto;
}

.savingDetailBox{
  padding: 50px;
  border-radius: 30px;
  border: 1px solid rgba(153, 153, 153, 0.6);
}

ul{
  list-style:none;
}

.detailMove{
  border: none;
  background-color: rgba(67, 140, 116, 1);
  width: 200px;
  height: 50px;
  color: white;
  font-size: 20px;
}

button{
  border-radius: 20px;
  margin: 20px;
}

a {
  color: inherit;
  text-decoration: none;
}
.calNumber{
  width: 165%;
}
</style>