<script setup>
import {Swiper, SwiperSlide} from 'swiper/vue';
import 'swiper/swiper-bundle.css';
import {ref, onMounted, computed, watch} from 'vue';
import axios from 'axios';
import {useRoute, useRouter} from 'vue-router';

const savings = ref([]);
const topSavings = ref([]);
const loading = ref(true);
const searchTerm = ref('');
const currentPage = ref(1);

const route = useRoute();
const router = useRouter();

const selectedBanks = ref([]);
const selectedDurations = ref([]);
const selectedInterestTypes = ref([]);

const goToDetail = (savingId) => {
  router.push({name: 'depositDetail', params: {savingId}});
};

const removeFilter = (arrayRef, value) => {
  const index = arrayRef.indexOf(value);
  if (index !== -1) {
    arrayRef.splice(index, 1);
  }
};

const firstTierBanks = ref([
  '국민은행', '신한은행', '하나은행', '우리은행', '농협은행', '기업은행', '수협은행',
  'SC제일은행', '토스뱅크', '경남은행', '광주은행', 'KDB산업은행', '케이뱅크', 'IM뱅크',
  '카카오뱅크', '제주은행', '부산은행', '씨티은행', '전북은행'
]);

const toggleSelection = (arrayRef, value) => {
  const index = arrayRef.value.indexOf(value);
  if (index === -1) {
    arrayRef.value.push(value);
  } else {
    arrayRef.value.splice(index, 1);
  }
};

const selectAll = (arrayRef, items) => {
  const isAllSelected = items.every(item => arrayRef.value.includes(item));
  if (isAllSelected) {
    arrayRef.value = arrayRef.value.filter(item => !items.includes(item));
  } else {
    arrayRef.value.push(...items.filter(item => !arrayRef.value.includes(item)));
  }
};

const selectBank = (bank) => {
  toggleSelection(selectedBanks, bank);
};

const selectDuration = (duration) => {
  toggleSelection(selectedDurations, duration);
};

const selectInterestType = (type) => {
  toggleSelection(selectedInterestTypes, type);
};

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

const fetchSavings = async () => {
  loading.value = true;
  try {
    const params = {
      searchValue: searchTerm.value,
      bankId: selectedBanks.value.join(','),
      saveTerm: selectedDurations.value.join(','),
      page: currentPage.value,
      interestRateType: selectedInterestTypes.value.join(','),
    };

    const response = await axios.get('/api/deposit', { params });
    console.log('API 응답:', response.data);

    if (response.data.savings && Array.isArray(response.data.savings)) {
      savings.value = response.data.savings;
      topSavings.value = savings.value
          .sort((a, b) => a.interestRateList.interestRate - b.interestRateList.interestRate);
    } else {
      console.warn('데이터가 없습니다.'); // 데이터가 없을 경우 경고 메시지
    }

  } catch (error) {
    console.error('예금 상품 목록을 가져오는 중 오류 발생:', error);
  } finally {
    loading.value = false;
  }
};


onMounted(() => {
  fetchSavings();
});
</script>

<template>
  <div class="container text-center">
    <br><br> <br><br> <br><br>
    <h1 class="d-inline">예금 </h1>
    <p class="d-inline">모은 꿈을 더 크게</p>
    <br><br>
    <div class="savingBest">
      <div class="text-start">
        <h2>ㅇㅇㅇ성향 고객님들이 선택한 BEST 인기상품</h2>
        <h4>가장 많이 사랑 받은 예금 상품</h4><br><br>
      </div>
      <div v-if="loading">로딩 중...</div>
      <div v-else class="itemBoxDiv">
        <ul>
          <li v-for="(topSaving, index) in topSavings.splice(0,3)" :key="topSaving.savingId"
              class="d-inline-block itemBox text-start">
            <div class="ranking">
          <span>
            {{ index === 0 ? '🥇' : index === 1 ? '🥈' : '🥉' }}
          </span>
            </div>
            <div class="goToDetail" @click="goToDetail(topSaving.savingId)">
              <div class="savingDepositMethod">
                <div class="savingMethod text-center">{{ topSaving.joinWay }}</div>
                <div class="bankLogo">
                  <a :href="topSaving.bank.bankUrl">
                    <img style="height: 25px;" :src="topSaving.bank.bankLogoUrl"/>
                  </a>
                  <br/>
                  <h3>{{ topSaving.bank.bankName }}</h3>
                </div>
              </div>
              <br/>
              <div style="width: 300px;"><h3 class="savingName">{{ topSaving.savingName }}</h3><br/></div>
              <div>
                <h3 style="font-weight: 600">{{ topSaving.interestRateList.interestRateType }}</h3>
                <h3 class="d-inline" style="color: rgba(68, 140, 116, 1);">
                  {{ topSaving.interestRateList.savingTerm }}</h3>
                <h3 class="d-inline">개월</h3>
              </div>
              <div style="display: flex; justify-content: space-between; color: grey">
                <div>
                  <h3>기본금리</h3>
                  <h3>{{ topSaving.interestRateList.interestRate }}%</h3>
                </div>
                <div>
                  <h3>최고금리</h3>
                  <h3>{{ topSaving.interestRateList.interestMaxRate }}%</h3>
                </div>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <br><br>
    <div class="text-start">
      <h4 class="search">상품 검색</h4>
      <div class="searchContainer">
        <input class="searchBar" type="text" placeholder="검색어를 입력해주세요" v-model="searchTerm" @focus="highlightInput"
               @blur="resetInput"/>
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
              <input type="checkbox" :id="'bank' + index" @change="selectBank(bank)"
                     :checked="selectedBanks.includes(bank)"/>
              <label :for="'bank' + index" :class="{ 'selected': selectedBanks.includes(bank) }">{{ bank }}</label>
            </div>
          </li>
          <br>

          <!-- 저축 기간 필터 -->
          <li>
            <h4 style="font-weight: 700;">저축 기간</h4>
            <button class="filterBtn" @click="selectAllDurations">전체</button>
            <div class="filter d-inline" v-for="duration in ['1개월', '3개월', '6개월', '12개월', '24개월', '36개월']"
                 :key="duration">
              <input type="checkbox" :id="'duration' + duration" @change="selectDuration(duration)"
                     :checked="selectedDurations.includes(duration)"/>
              <label :for="'duration' + duration"
                     :class="{ 'selected': selectedDurations.includes(duration) }">{{ duration }}</label>
            </div>
          </li>
          <br>

          <!-- 이자 유형 필터 -->
          <li>
            <h4 style="font-weight: 700;">이자 유형</h4>
            <button class="filterBtn" @click="selectAllInterestTypes">전체</button>
            <div class="filter d-inline">
              <input type="checkbox" id="interest1" @change="selectInterestType('단리')"
                     :checked="selectedInterestTypes.includes('단리')"/>
              <label for="interest1" :class="{ 'selected': selectedInterestTypes.includes('단리') }">단리</label>
            </div>
            <div class="filter d-inline">
              <input type="checkbox" id="interest2" @change="selectInterestType('복리')"
                     :checked="selectedInterestTypes.includes('복리')"/>
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
                :space-between="10"
                :loop="false"
                :slides-per-view="7.2"
                :centered-slides="false"
                :edge-swipe-detection="true"
                :pagination="{ clickable: true }"
            >
              <!-- 선택된 은행 -->
              <SwiperSlide v-for="(bank, index) in selectedBanks" :key="'bank' + index">
                <div class="checkedFilter">
                  {{ bank }}
                  <button @click="removeFilter(selectedBanks, bank)">X</button>
                </div>
              </SwiperSlide>

              <!-- 선택된 저축 기간 -->
              <SwiperSlide v-for="(duration, index) in selectedDurations" :key="'duration' + index">
                <div class="checkedFilter">
                  {{ duration }}
                  <button @click="removeFilter(selectedDurations, duration)">X</button>
                </div>
              </SwiperSlide>

              <!-- 선택된 이자 유형 -->
              <SwiperSlide v-for="(type, index) in selectedInterestTypes" :key="'type' + index">
                <div class="checkedFilter">
                  {{ type }}
                  <button @click="removeFilter(selectedInterestTypes, type)">X</button>
                </div>
              </SwiperSlide>
            </Swiper>
          </div>
        </div>
      </div>
      <div class="toggle-wrapper">
        <br>
        <span class="more-text" v-if="!expanded" @click="toggleText">상세 검색 열기<i class="ai-chevron-down"></i></span>
        <span class="less-text" v-if="expanded" @click="toggleText">상세 검색 닫기<i class="ai-chevron-up"></i></span>
      </div>
    </div>
    <br><br>
    <div class="savingsContent">
      <div class="text-start">
        <h2 class="d-inline"><b>검색 결과</b> 테마상품</h2>
        <br><br><br>
      </div>
      <div v-if="loading">로딩 중...</div>
      <div v-else class="itemBoxDiv">
        <ul>
          <li v-for="saving in savings" :key="saving.savingId"
              class="d-inline-block itemBox text-start">
            <div class="goToDetail" @click="goToDetail(saving.savingId)">
              <div class="savingDepositMethod">
                <div class="depositMethod text-center">{{ saving.joinWay }}</div>
                <div class="bankLogo">
                  <a :href="saving.bank.bankUrl">
                    <img style="height: 25px;" :src="saving.bank.bankLogoUrl"/>
                  </a>
                  <h3>{{ saving.bank.bankName }}</h3>
                </div>
              </div>
              <br/>
              <div style="width: 300px;"><h3 class="savingName">{{ saving.savingName }}</h3><br/></div>
              <div>
                <h3 style="font-weight: 600">{{ saving.interestRateList.interestRateType }}</h3>
                <h3 class="d-inline" style="color: rgba(68, 140, 116, 1);">
                  {{ saving.interestRateList.savingTerm }}</h3>
                <h3 class="d-inline">개월</h3>
              </div>
              <div style="display: flex; justify-content: space-between; color: grey">
                <div>
                  <h3>기본금리</h3>
                  <h3>{{ saving.interestRateList.interestRate }}%</h3>
                </div>
                <div>
                  <h3>최고금리</h3>
                  <h3>{{ saving.interestRateList.interestMaxRate }}%</h3>
                </div>
              </div>
            </div>
          </li>
        </ul>
      </div>
      <div class="pagination">
        <button v-if="currentPage > 1" @click="changePage(currentPage - 1)">이전</button>
        <span>{{ currentPage }} / {{ totalPages }}</span>
        <button v-if="currentPage < totalPages" @click="changePage(currentPage + 1)">다음</button>
      </div>
    </div>
    <br><br>
  </div>
</template>


<style scoped>
.ranking {
  font-size: 30px;
  font-weight: bold;
  color: rgba(68, 140, 116, 1);
  margin-top: -25px;
}

.goToDetail {
  cursor: pointer;
}

.savingName {
  color: rgba(68, 140, 116, 1);
  font-weight: 700;
  text-align: start;
}

.savingDepositMethod {
  position: relative;
}

.savingMethod {
  position: absolute;
  top: -20px;
  right: 5px;
  border-radius: 20px;
  border: 1px solid lightgrey;
  width: 100px;
}
.depositMethod{
  position: absolute;
  top: -10px;
  right: 5px;
  border-radius: 20px;
  border: 1px solid lightgrey;
  width: 100px;
}

.savingRank > tbody tr {
  padding: 20px;
}

.checkedFilterBox {
  margin-top: 10px;
}

.selected-filters {
  text-align: start;
  width: 850px;
  margin-left: 200px;
}

.itemBoxDiv {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

.itemBox {
  padding: 20px;
  margin: 20px;
  max-width: 400px;
  box-sizing: border-box;
}

.checkedFilter {
  background-color: rgba(68, 140, 116, 1);
  color: white;
  font-size: 12px;
  border: 1px solid #bebebe;
  border-radius: 20px;
  padding: 5px;
  width: 120px;
  height: 40px;
  text-align: start;
  margin-left: 20px;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
}

.checkedFilter button {
  background-color: transparent;
  border: none;
  cursor: pointer;
  color: white;
}

.filter label {
  width: 130px;
  height: 40px;
  border: 1px solid lightgrey;
  border-radius: 30px;
  background-color: white;
  font-size: 15px;
  text-align: center;
  margin: 5px;
  padding: 10px;
  cursor: pointer;
}

.filter label:hover {
  border: 1px solid rgba(68, 140, 116, 1);
  color: rgba(68, 140, 116, 1);
}

.filter .selected:hover{
  color: white;
}
input[type="checkbox"] {
  display: none;
}

.toggle-wrapper {
  cursor: pointer;
  text-align: center;
  font-size: 20px;
  color: black;
}

.filterBar {
  margin-left: 13%;
  list-style: none;
  width: 70%;
  margin-bottom: 5%;
}

.filterBtn {
  width: 130px;
  height: 40px;
  border: 1px solid lightgrey;
  border-radius: 30px;
  background-color: white;
  font-size: 15px;
  margin: 5px;
}

.filterBtn:hover {
  color: rgba(68, 140, 116, 1);
  border: 1px solid rgba(68, 140, 116, 1);
  font-size: 16px;
}

.savingBest {
  background-color: rgba(67, 140, 116, 0.06);
  border-radius: 30px;
  padding: 50px;
}

.itemBox {
  border: 1px solid rgba(231, 236, 243, 1);
  border-radius: 30px;
  background-color: white;
  list-style: none;
}

.savingsContent {
  background-color: rgba(247, 249, 252, 1);
  border-radius: 30px;
  padding: 50px;
}

.themeItem {
  font-weight: 700;
}

.searchBar {
  width: 70%;
  height: 50px;
  border: 1px solid rgba(215, 221, 227, 1);
  border-radius: 30px;
  padding: 30px;
}

.searchBtn {
  width: 80px;
  height: 50px;
  color: white;
  border: none;
  border-radius: 30px;
  background-color: rgba(68, 140, 116, 1);
  margin: 1%;
}

.searchBtn:active {
  background-color: lightgrey;
  color: black;
}

.search {
  margin-left: 15%;
  font-weight: 700;
}

.page-link {
  color: rgba(68, 140, 116, 1);
}

.active > .page-link {
  background-color: rgba(68, 140, 116, 1);
  border: none;
}

.page-link:hover {
  color: white;
}

.pagination {
  --bs-pagination-color: rgba(68, 140, 116, 1);
  --bs-pagination-hover-color: rgba(68, 140, 116, 1);
}

.searchContainer {
  margin-left: 15%;
}

.container {
  width: 80%;
}

.selected {
  background-color: rgba(68, 140, 116, 1) !important;
  color: white;
  border: 1px solid rgba(68, 140, 116, 1);
  transition: background-color 0.3s ease, color 0.3s ease;
}

.selected:hover {
  background-color: white;
  border: 1px solid rgba(48, 120, 96, 1);
}

</style>
