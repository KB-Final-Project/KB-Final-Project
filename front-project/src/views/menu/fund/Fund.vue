<template>
  <div class="container text-center">
    <h1 class="d-inline">펀드 </h1><p class="d-inline"></p>
    <br><br>
    <div class="savingBest">
      <div class="text-start">
        <h2>ㅇㅇㅇ성향 고객님들이 선택한 BEST 인기상품</h2>
        <h4>가장 많이 사랑 받은 적금 상품</h4><br><br>
      </div>
      <div class="itemBoxDiv row g-3 gap-3">
        <div class="itemBox col"><div class="p-3">content</div></div>
        <div class="itemBox col"><div class="p-3">content</div></div>
        <div class="itemBox col"><div class="p-3">content</div></div>
      </div>
    </div><br><br>
    <div class="text-start m-4">
      <h2 class="d-inline">트렌드를 반영한</h2><h2 class="d-inline" style="font-weight: 600;"> 테마별 펀드 상품</h2>
    </div><br>
    <div class="slider-container position-relative">

      <Swiper
          class="slider slider1"
          :modules="modules"
          :slidesPerView="3.1"
          :centeredSlides="true"
          :spaceBetween="20"
          loop
          pagination
          style="padding-left: 450px;"
      >
      <SwiperSlide class="sliders" style="background-color: rgba(67, 140, 116, 1);" @click="FundDetail({type : '1', name: '지수형 펀드', img: '/img/emoji/fund4.PNG'})">
        <h3>지수형 펀드</h3>
        <img class="position-absolute bottom-25 end-25" style="width:50px; height:50px;" src="/img/emoji/fund4.PNG" alt="kor">
      </SwiperSlide>
      <SwiperSlide style="background-color: rgba(113, 113, 113, 1);"></SwiperSlide>
      <SwiperSlide style="background-color: rgba(191, 191, 191, 1);"></SwiperSlide>
      <SwiperSlide class="sliders" style="background-color: rgba(67, 140, 116, 1);" @click="FundDetail({type:'2', name:'글로벌 중소형주펀드', img: '/img/emoji/earth.png'})">
        <h3>글로벌</h3><h3>중소형주펀드</h3>
        <img class="position-absolute bottom-25 end-25" style="width:50px; height:50px;" src="/img/emoji/earth.png" alt="kor">
      </SwiperSlide>
      <SwiperSlide class="sliders" style="background-color: rgba(111, 186, 161, 1);" @click="FundDetail({type: '3', name: '한국형 TDF', img: '/img/emoji/kor.PNG'})">
        <h3>한국형</h3><h3>TDF</h3>
        <img class="position-absolute bottom-25 end-25" style="width:50px; height:50px;" src="/img/emoji/kor.PNG" alt="kor">
      </SwiperSlide>
      </Swiper>
      <br>
      <Swiper
          class="slider slider2"
          :modules="modules"
          :slidesPerView="3.1"
          :centeredSlides="true"
          :spaceBetween="20"
          loop
          pagination
          style="padding-left: 400px;"
      >
        <SwiperSlide class="sliders" style="background-color: rgba(67, 140, 116, 1);" @click="FundDetail({type: '4', name: '유명산업펀드 ', img: '/img/emoji/fund2.PNG'})">
          <h3>유망산업펀드</h3>
          <img class="position-absolute bottom-25 end-25" style="width:50px; height:50px;" src="/img/emoji/fund2.PNG" alt="kor">
        </SwiperSlide>
      <SwiperSlide style="background-color: rgba(191, 191, 191, 1);"></SwiperSlide>
      <SwiperSlide style="background-color: rgba(191, 191, 191, 1);"></SwiperSlide>
      <SwiperSlide style="background-color: rgba(113, 113, 113, 1);"></SwiperSlide>
      <SwiperSlide class="sliders" style="background-color: rgba(111, 186, 161, 1);" @click="FundDetail({type: '5', name: '개인연금펀드 ', img:'/img/emoji/fund1.PNG'})">
        <h3>개인연금펀드</h3>
        <img class="position-absolute bottom-25 end-25" style="width:50px; height:50px;" src="/img/emoji/fund1.PNG" alt="kor">
      </SwiperSlide>
      </Swiper>
      <br>
      <Swiper
          class="slider slider3"
          :modules="modules"
          :slidesPerView="3.1"
          :centeredSlides="true"
          :spaceBetween="20"
          loop
          pagination
          style="padding-left: 380px;"
      >
        <SwiperSlide class="sliders" style="background-color: rgba(111, 186, 161, 1);" @click="FundDetail({type: '6', name: '퇴직연금 펀드 ', img: '/img/emoji/fund5.PNG'})">
          <h3>퇴직연금 펀드</h3>
          <img class="position-absolute bottom-25 end-25" style="width:50px; height:50px;" src="/img/emoji/fund5.PNG" alt="kor">
        </SwiperSlide>
        <SwiperSlide style="background-color: rgba(113, 113, 113, 1);"></SwiperSlide>
        <SwiperSlide style="background-color: rgba(113, 113, 113, 1);"></SwiperSlide>
        <SwiperSlide style="background-color: rgba(191, 191, 191, 1);"></SwiperSlide>
        <SwiperSlide class="sliders" style="background-color: rgba(67, 140, 116, 1);" @click="FundDetail({type: '7', name: '자산배분펀드 ', img: '/img/emoji/fund3.PNG'})">
          <h3>자산배분펀드</h3>
          <img class="position-absolute bottom-25 end-25" style="width:50px; height:50px;" src="/img/emoji/fund3.PNG" alt="kor">
        </SwiperSlide>
      </Swiper>
      <br>
      <Swiper
          class="slider slider3"
          :modules="modules"
          :slidesPerView="3.1"
          :centeredSlides="true"
          :spaceBetween="20"
          loop
          pagination
          style="padding-left: 450px;"
      >
        <SwiperSlide class="sliders" style="background-color: rgba(111, 186, 161, 1);" @click="FundDetail({type: '8', name: 'EMP 펀드 ', img: '/img/emoji/bank.png'})">
          <h3>EMP 펀드</h3>
          <img class="position-absolute bottom-25 end-25" style="width:50px; height:50px;" src="/img/emoji/bank.png" alt="kor">
        </SwiperSlide>
        <SwiperSlide style="background-color: rgba(113, 113, 113, 1);"></SwiperSlide>
        <SwiperSlide style="background-color: rgba(113, 113, 113, 1);"></SwiperSlide>
        <SwiperSlide style="background-color: rgba(191, 191, 191, 1);"></SwiperSlide>
        <SwiperSlide class="sliders" style="background-color: rgba(67, 140, 116, 1);" @click="FundDetail({ type: '9', name: '평생소득 TIF ', img: '/img/emoji/dollor.png'})">
          <h3>평생소득</h3><h3>TIF</h3>
          <img class="position-absolute bottom-25 end-25" style="width:50px; height:50px;" src="/img/emoji/dollor.png" alt="kor">
        </SwiperSlide>
      </Swiper>
    </div><br><br><br>
    <div class="fundSearch text-start">
      <h2>펀드 찾기</h2>
      <div class="text-center">
        <h2 class="d-inline search">상품 검색</h2>
        <input class="searchBar" type="text" placeholder="검색어를 입력해주세요"/>
        <button class="searchBtn" type="submit">검색</button>
      </div>


      <div class="fundSearchResult text-start">
        <h5>ㅇㅇ 검색 결과</h5>
        <div>
          <table class="fundSearchResultTable">
            <tbody>
              <tr>
                <td style="width: 50%;"><h4>한국투자NBSP 삼성그룹적립식증권투자신탁2호(주식)-A</h4></td>
                <td style="width: 5%;"><h4>수익률</h4></td>
                <td style="width: 5%;"><h2>-3.08%</h2></td>
                <td style="width: 10%;"><h4>(3개월 누적)</h4></td>
              </tr>
            </tbody>
          </table>
        </div>
      </div><br><br><br>
      <!-- Pagination basic example -->
      <nav aria-label="Page navigation example">
        <ul class="pagination">
          <li class="page-item">
            <a href="#" class="page-link">이전</a>
          </li>
          <li class="page-item d-sm-none">
            <span class="page-link pe-none">2 / 5</span>
          </li>
          <li class="page-item d-none d-sm-block">
            <a href="#" class="page-link">1</a>
          </li>
          <li class="page-item active d-none d-sm-block" aria-current="page">
      <span class="page-link">
        2
        <span class="visually-hidden">(current)</span>
      </span>
          </li>
          <li class="page-item d-none d-sm-block">
            <a href="#" class="page-link">3</a>
          </li>
          <li class="page-item d-none d-sm-block">
            <a href="#" class="page-link">4</a>
          </li>
          <li class="page-item d-none d-sm-block">
            <a href="#" class="page-link">5</a>
          </li>
          <li class="page-item">
            <a href="#" class="page-link">다음</a>
          </li>
        </ul>
      </nav>
    </div><br><br><br>
    <div class="text-center d-inline ">
      <h1>AROUND 만의 또 다른 추천</h1>
      <br>
        <div class="suggest">
          <p>커뮤니티</p><br>
          <p class="text-end">👨‍👩‍👧‍</p>
        </div>
        <div class="suggest">
          <p>용어사전</p><br>
          <p class="text-end">📚</p>
        </div>
        <div class="suggest">
          <p>투자성향TEST</p><br>
          <p class="text-end">💖</p>
        </div>
        <div class="suggest">
          <p>챗붓 상담</p><br>
          <p class="text-end">💖</p>
        </div>
    </div>
  </div>
</template>
<style scoped>
.container {
  padding-top: 80px;
}

.suggest{
  display: inline-block;
  border: 1px solid rgba(153, 153, 153, 0.6);
  border-radius: 20px;
  width: 250px;
  height: 200px;
  margin: 10px;
  padding: 25px;
  font-size: 30px;
  text-align: start;
}

.fundSearchResult{
  margin-left: 10%;
  padding:10px;
}

.fundSearchResultTable{
  table-layout: fixed;
  text-align: start;
  border-bottom: 1px solid rgb(190, 190, 190);
}

.fundBeanBox{
  border: 1px solid rgba(153, 153, 153, 1);
}

.savingBest{
  background-color: rgba(67, 140, 116, 0.06);
  border-radius: 30px;
  padding: 50px;
}

.itemBox{
  border: 1px solid rgba(231, 236, 243, 1);
  border-radius: 30px;
  display: inline-block;
  width: 350px;
  height: 350px;
  background-color: white;
}

.swiper {
  width: 100%;
  height: 100%;
}

SwiperSlide {
  text-align: center;
  font-size: 18px;
  width: 400px;
  height: 400px;

}
.swiper-slide{
  text-align: left;
  border-radius: 20px;
  padding: 40px;

}
.swiper-slide img {
  display: block;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.slider-container {
  position: relative;
  height: 1000px;
  display: flex;
  flex-direction: column;
}

h3{
  font-weight: 700;
  color: white;
  font-size: 30px;
}

.fundSearch{
  padding: 50px;
  background-color: rgba(248, 244, 244, 1);
  border-radius: 30px;
}

.searchBar{
  width: 60%;
  height: 50px;
  border: 1px solid rgba(215, 221, 227, 1);
  border-radius: 30px;
  padding: 20px;
}

.searchBtn{
  width: 80px;
  height: 50px;
  color: white;
  border: none;
  border-radius: 30px;
  background-color: rgba(68, 140, 116, 1);
  margin: 1%;
}
.searchBtn:active{
  background-color: lightgrey;
  color: black;
}

.search{
  margin: 30px;
}

.page-link{
  color: rgba(68, 140, 116, 1);
}

.active>.page-link{
  background-color: rgba(68, 140, 116, 1);
  border: none;

}
.page-link:hover{
  color: white;
}

.pagination {
  --bs-pagination-color: rgba(68, 140, 116, 1);
  --bs-pagination-hover-color:rgba(68, 140, 116, 1);

}

.slider .sliders:hover{
  transform: scale(0.9);
  z-index: 11111;
}
.slider .sliders{
  transition: tranform 0.9s;
}

.container{
  padding-top: 80px;
  width: 80%;
}


</style>
<script setup>
import { Swiper, SwiperSlide } from 'swiper/vue';
import { Pagination } from 'swiper/modules';
import 'swiper/css';
import { useFundStore } from '@/stores/fundStore';

import { useRouter } from "vue-router";
const modules = [Pagination];
const router = useRouter();
const fundStore = useFundStore();

function FundDetail(fundInfo) {
  fundStore.setFundDetails(fundInfo);

  router.push({ name: 'fundDetail' });
}

</script>