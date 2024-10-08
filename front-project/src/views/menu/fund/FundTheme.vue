<template>
  <div class="container text-center">
    <br><br>
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

  </div><br><br><br>
</template>



<style scoped>
.container {
  padding-top: 80px;
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



.slider .sliders:hover{
  transform: scale(0.9);
  z-index: 11111;
}
.slider .sliders{
  transition: transform 0.9s;
}

.container{
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