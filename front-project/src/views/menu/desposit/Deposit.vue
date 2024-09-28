<script setup>
import { Swiper, SwiperSlide } from 'swiper/vue';
import 'swiper/swiper-bundle.css';
import { ref } from 'vue';

// 선택된 은행, 저축 기간, 이자 유형을 추적
const selectedBanks = ref([]);
const selectedDurations = ref([]);
const selectedInterestTypes = ref([]);

const removeFilter = (arrayRef, value) => {
  // 배열이 정의되어 있는지 확인
  if (!arrayRef || !Array.isArray(arrayRef.value)) {
    console.error('arrayRef is not defined or not an array:', arrayRef);
    return; // 함수 종료
  }
  const index = arrayRef.value.indexOf(value);
  if (index !== -1) {
    arrayRef.value.splice(index, 1);
  }
};

// 제1금융권 목록을 정의
const firstTierBanks = ref([
  '국민은행', '신한은행', '하나은행', '우리은행', '농협은행', '기업은행', '수협은행',
  'SC 제일은행', '토스뱅크', '경남은행', '광주은행', 'KDB 산업은행', '케이뱅크', 'IM 뱅크',
  '카카오뱅크', '제주은행', '부산은행', '씨티은행', '전북은행', '씨티은행'
]);

// 체크박스 선택 처리 함수 (선택 또는 해제)
const toggleSelection = (arrayRef, value) => {
  const index = arrayRef.value.indexOf(value);
  if (index === -1) {
    arrayRef.value.push(value);
  } else {
    arrayRef.value.splice(index, 1);
  }
};

// 전체 선택 및 해제 함수
const selectAll = (arrayRef, items) => {
  const isAllSelected = items.every(item => arrayRef.value.includes(item));
  if (isAllSelected) {
    arrayRef.value = arrayRef.value.filter(item => !items.includes(item));
  } else {
    arrayRef.value.push(...items.filter(item => !arrayRef.value.includes(item)));
  }
};

// 은행 선택 처리 함수
const selectBank = (bank) => {
  toggleSelection(selectedBanks, bank);
};

// 저축 기간 선택 처리 함수
const selectDuration = (duration) => {
  toggleSelection(selectedDurations, duration);
};

// 이자 유형 선택 처리 함수
const selectInterestType = (type) => {
  toggleSelection(selectedInterestTypes, type);
};

// 전체 선택 처리 함수들
const selectAllBanks = () => selectAll(selectedBanks, firstTierBanks.value);
const selectAllDurations = () => selectAll(selectedDurations, ['1개월', '3개월', '6개월', '12개월', '24개월', '36개월']);
const selectAllInterestTypes = () => selectAll(selectedInterestTypes, ['단리', '복리']);

const expanded = ref(false);

const toggleText = () => {
  expanded.value = !expanded.value;
};

const highlightInput = (event) => {
  event.target.classList.add('highlight');
};

const resetInput = (event) => {
  event.target.classList.remove('highlight');
};

const clearFilter = () => {
  selectedBanks.value = [];
  selectedDurations.value = [];
  selectedInterestTypes.value = [];
};


</script>

<template>
  <div class="container text-center">
    <h1 class="d-inline">예금 </h1><p class="d-inline">열심히 모은 꿈을 더 크게</p>
    <br><br>
    <div class="savingBest">
      <div class="text-start">
        <h2>ㅇㅇㅇ성향 고객님들이 선택한 BEST 인기상품</h2>
        <h4>가장 많이 사랑 받은 예금 상품</h4><br><br>
      </div>
      <div class="itemBoxDiv row g-3 gap-3">
        <div class="itemBox col"><div class="p-3">content</div></div>
        <div class="itemBox col"><div class="p-3">content</div></div>
        <div class="itemBox col"><div class="p-3">content</div></div>
      </div>
    </div><br><br>
    <div class="text-start">
      <h4 class="search">상품 검색</h4>
      <div class="searchContainer">
        <input class="searchBar" type="text" placeholder="검색어를 입력해주세요" @focus="highlightInput" @blur="resetInput"/>
        <button class="searchBtn" type="submit">검색</button>
      </div>
      <br>
      <div v-if="expanded" class="additional-info">
        <ul class="filterBar">
          <!-- 은행 필터 -->
          <li>
            <h4 style="font-weight: 700;">은행</h4>
            <button class="filterBtn" @click="selectAllBanks">전체</button>
            <div class="filter d-inline" v-for="(bank, index) in firstTierBanks" :key="index">
              <input type="checkbox" :id="'bank' + index" @change="selectBank(bank)" :checked="selectedBanks.includes(bank)" />
              <label :for="'bank' + index" :class="{ 'selected': selectedBanks.includes(bank) }">{{ bank }}</label>
            </div>
          </li>
          <li><br></li>

          <!-- 저축 기간 필터 -->
          <li>
            <h4 style="font-weight: 700;">저축 기간</h4>
            <button class="filterBtn" @click="selectAllDurations">전체</button>
            <div class="filter d-inline"  v-for="duration in ['1개월', '3개월', '6개월', '12개월', '24개월', '36개월']" :key="duration">
              <input type="checkbox" :id="'duration' + duration" @change="selectDuration(duration)" :checked="selectedDurations.includes(duration)" />
              <label :for="'duration' + duration" :class="{ 'selected': selectedDurations.includes(duration) }">{{ duration }}</label>
            </div>
          </li>
          <li><br></li>

          <!-- 이자 유형 필터 -->
          <li>
            <h4 style="font-weight: 700;">이자 유형</h4>
            <button class="filterBtn" @click="selectAllInterestTypes">전체</button>
            <div  class="filter d-inline" >
              <input type="checkbox" id="interest1" @change="selectInterestType('단리')" :checked="selectedInterestTypes.includes('단리')" />
              <label for="interest1" :class="{ 'selected': selectedInterestTypes.includes('단리') }">단리</label>
            </div>
            <div  class="filter d-inline" >
              <input type="checkbox" id="interest2" @change="selectInterestType('복리')" :checked="selectedInterestTypes.includes('복리')" />
              <label for="interest2" :class="{ 'selected': selectedInterestTypes.includes('복리') }">복리</label>
            </div>
          </li>
        </ul>
        <div class="checkedFilterBox">
          <div
              class="selected-filters"
              v-if="selectedBanks.length || selectedDurations.length || selectedInterestTypes.length"
          >
            <Swiper
                :slides-per-view="8"
                :space-between="5"
                :pagination="{ clickable: true }"
            >
              <SwiperSlide v-for="(bank, index) in selectedBanks" :key="'bank' + index">
                <div class="checkedFilter">{{ bank }} <button @click="removeFilter(selectedBanks, bank)">X</button></div>
              </SwiperSlide>
              <SwiperSlide v-for="(duration, index) in selectedDurations" :key="'duration' + index">
                <div class="checkedFilter">{{ duration }} <button @click="removeFilter(selectedDurations, duration)">X</button></div>
              </SwiperSlide>
              <SwiperSlide v-for="(type, index) in selectedInterestTypes" :key="'type' + index">
                <div class="checkedFilter">{{ type }} <button @click="removeFilter(selectedInterestTypes, type)">X</button></div>
              </SwiperSlide>
            </Swiper>
          </div>
        </div>
        <!-- 필터 초기화 버튼 추가 -->
        <button class="clear-filter" @click="clearFilter">필터 초기화</button>
      </div>
      <!-- 선택된 필터를 나열 -->
      <div class="toggle-wrapper">
        <br>
        <span class="more-text" v-if="!expanded" @click="toggleText">상세 검색 열기<i class="ai-chevron-down"></i></span>
        <span class="less-text" v-if="expanded" @click="toggleText">상세 검색 닫기<i class="ai-chevron-up"></i></span>
      </div>
    </div>
    <br><br>
    <div class="savingsContent">
      <div class="text-start">
        <h2 class="d-inline">ooo가 고객님에게 추천한 </h2><h2 class="d-inline themeItem">테마상품</h2>
        <br><br><br>
      </div>
      <div class="itemBoxDiv row g-lg-3 gap-3">
        <div class="itemBox col"><div class="p-3">content</div></div>
        <div class="itemBox col"><div class="p-3">content</div></div>
        <div class="itemBox col"><div class="p-3">content</div></div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.highlight {
  background-color: lightgreen;
}

.checkedFilterBox {
  margin-top: 10px;
}

.selected-filters {
  display: flex;
  overflow-x: auto;
}

.checkedFilter {
  background-color: #e0f7fa;
  border: 1px solid #00796b;
  border-radius: 5px;
  padding: 5px 10px;
  margin-right: 5px;
  display: inline-flex;
  align-items: center;
}

.checkedFilter button {
  background-color: transparent;
  border: none;
  margin-left: 5px;
  cursor: pointer;
  color: #f44336;
}

.clear-filter {
  margin-top: 10px;
  padding: 5px 10px;
  background-color: #ffeb3b;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.filter label{
  width: 130px;
  height: 40px;
  border: 1px solid lightgrey;
  border-radius: 30px;
  background-color: white;
  font-size:15px;
  text-align: center;
  margin: 5px;
  padding: 10px;
  cursor: pointer;
}
.filter label:hover{
  border: 1px solid rgba(68, 140, 116, 1);
  color: rgba(68, 140, 116, 1);
}
input[type="checkbox"] {
  display: none;
}

.toggle-wrapper{
  cursor: pointer;
  text-align: center;
  font-size: 20px;
  color: black;
}

.filterBar{
  margin-left: 13%;
  list-style: none;
  width: 70%;
  margin-bottom: 5%;
}

.filterBtn{
  width: 130px;
  height: 40px;
  border: 1px solid lightgrey;
  border-radius: 30px;
  background-color: white;
  font-size:15px;
  margin: 5px;
}

.filterBtn:hover{
  color: rgba(68, 140, 116, 1);
  border: 1px solid rgba(68, 140, 116, 1);
  font-size: 16px;
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

.savingsContent{
  background-color: rgba(247, 249, 252, 1);
  border-radius: 30px;
}

.themeItem{
  font-weight: 700;
}

.searchBar{
  width: 70%;
  height: 50px;
  border: 1px solid rgba(215, 221, 227, 1);
  border-radius: 30px;
  padding: 30px;
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
  margin-left:15%;
  font-weight: 700;
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

.searchContainer{
  margin-left: 15%;
}

.container{
  width: 80%;
  padding-top: 80px;
}

</style>