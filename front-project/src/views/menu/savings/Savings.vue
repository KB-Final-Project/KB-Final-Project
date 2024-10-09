<script setup>
import {ref, onMounted, watch, computed} from 'vue';
import axios from 'axios';
import {useRouter} from 'vue-router';
import {Swiper, SwiperSlide} from 'swiper/vue';
import 'swiper/swiper-bundle.css';
import qs from 'qs'; // qs 라이브러리 임포트

const savings = ref([]);
const topSavings = ref([]);
const loading = ref(true);
const searchTerm = ref('');
const currentPage = ref(1);
const totalPages = ref(1);
const totalCount = ref(0);
const expanded = ref(false);
const router = useRouter();

const selectedBanks = ref([]);
const selectedDurations = ref([]);
const selectedInterestTypes = ref([]);

// 모든 은행 목록을 저장하는 반응형 변수
const allBanks = ref([]);

// 은행을 "1금융"과 "기타"로 그룹화하는 계산된 속성
const groupedBanks = computed(() => {
  if (!Array.isArray(allBanks.value)) {
    return {};
  }
  return allBanks.value.reduce((groups, bank) => {
    const type = bank.bankType === 1 ? '1금융' : '기타';
    if (!groups[type]) {
      groups[type] = [];
    }
    groups[type].push(bank);
    return groups;
  }, {});
});

// 페이지네이션 함수
const getPaginationPages = () => {
  const pages = [];
  const maxPagesToShow = 5;
  const startPage = Math.max(1, currentPage.value - Math.floor(maxPagesToShow / 2));
  const endPage = Math.min(totalPages.value, startPage + maxPagesToShow - 1);

  for (let i = startPage; i <= endPage; i++) {
    pages.push(i);
  }
  return pages;
};

// 상세 페이지로 이동하는 함수
const goToDetail = (savingId) => {
  if (savingId) {
    router.push({name: 'savingDetail', params: {savingId}});
  } else {
    console.warn('Invalid savingId provided:', savingId);
  }
};

const removeFilter = (arrayRef, value) => {
  const index = arrayRef.indexOf(value);
  if (index !== -1) {
    arrayRef.splice(index, 1);
  }
  fetchSavings();
};

const selectBank = (bankId) => {
  if (selectedBanks.value.includes(bankId)) {
    selectedBanks.value = [];
  } else {
    selectedBanks.value = [bankId]; // 하나의 은행만 선택
  }
};

const selectDuration = (duration) => {
  if (selectedDurations.value.includes(duration)) {
    selectedDurations.value = [];
  } else {
    selectedDurations.value = [duration]; // 하나의 저축 기간만 선택
  }
};

const selectInterestType = (type) => {
  if (selectedInterestTypes.value.includes(type)) {
    selectedInterestTypes.value = selectedInterestTypes.value.filter(t => t !== type);
  } else {
    selectedInterestTypes.value = [type];
  }
};

const highlightInput = (event) => {
  event.target.classList.add('highlight');
};

const resetInput = (event) => {
  event.target.classList.remove('highlight');
};

const LIST_LIMIT = 9;

const handleBankChange = (bankId) => {
  const id = Number(bankId); // bankId를 정수로 변환
  selectBank(id);
  fetchSavings();
};

const fetchSavings = async () => {
  loading.value = true;
  try {
    const params = {
      searchValue: searchTerm.value || '',
      bankId: selectedBanks.value.length > 0 ? selectedBanks.value.join(',') : undefined,
      saveTerm: selectedDurations.value.length > 0 ? selectedDurations.value.join(',') : undefined,
      page: currentPage.value,
      limit: LIST_LIMIT,
      interestRateType: selectedInterestTypes.value.length > 0 ? selectedInterestTypes.value.join(',') : undefined
    };

    const response = await axios.get('/api/saving', {
      params,
      paramsSerializer: params => qs.stringify(params, {arrayFormat: 'comma', encodeValuesOnly: true})
    });

    if (response.data && response.data.savings) {
      savings.value = response.data.savings;
      totalCount.value = response.data.totalCount;
      totalPages.value = Math.ceil(totalCount.value / LIST_LIMIT);
    } else {
      savings.value = [];
      totalCount.value = 0;
      totalPages.value = 1;
    }
  } catch (error) {
    savings.value = [];
    totalCount.value = 0;
    totalPages.value = 1;
  } finally {
    loading.value = false;
  }
};

const fetchTopSavings = async () => {
  loading.value = true;
  try {
    const response = await axios.get('/api/saving/top');
    if (response.data && response.data.length > 0) {
      topSavings.value = response.data;
    } else {
      topSavings.value = [];
    }
  } catch (error) {
    topSavings.value = [];
  } finally {
    loading.value = false;
  }
};

const fetchAllBanks = async () => {
  try {
    const response = await axios.get('/api/bank/list');
    if (response.data && Array.isArray(response.data)) {
      allBanks.value = response.data.map(bank => ({
        ...bank,
        bankId: Number(bank.bankId)
      }));
    } else {
      allBanks.value = [];
    }
  } catch (error) {
    allBanks.value = [];
  }
};

const getBankNameById = (bankId) => {
  const bank = allBanks.value.find(b => b.bankId === bankId);
  return bank ? bank.bankName : '알 수 없는 은행';
};

onMounted(() => {
  fetchAllBanks();
  fetchSavings();
  fetchTopSavings();
});

watch(searchTerm, () => {
  currentPage.value = 1;
  fetchSavings();
});

watch([selectedBanks, selectedDurations, selectedInterestTypes], () => {
  currentPage.value = 1;
  fetchSavings();
});

const toggleText = () => {
  expanded.value = !expanded.value;
  if (expanded.value) {
    fetchSavings();
  }
};

const changePage = (newPage) => {
  if (newPage >= 1 && newPage <= totalPages.value) {
    currentPage.value = newPage;
    fetchSavings();
  }
};

const truncateText = (text, maxLength) => {
  if (typeof text !== 'string') return '';
  return text.length > maxLength ? text.slice(0, maxLength) + '...' : text;
};
</script>

<template>
  <div class="container text-center animate-on-load">
    <br><br>
    <h1 class="d-inline">적금 </h1>
    <p class="d-inline">꿈을 모아서</p>
    <br><br>
    <div class="savingBest">
      <div class="text-start">
        <h2>고객님들이 선택한 BEST 인기상품</h2>
        <h4>가장 많이 사랑 받은 적금 상품</h4>
        <h5 style="color: rgba(68, 140, 116, 1);">가장 적은 개월 수에 많은 금리</h5><br><br>
      </div>
      <!-- 로딩 메시지 -->
      <div v-if="loading" class="loading-box">
        <div class="spinner-border text-primary" role="status">
          <span class="visually-hidden">데이터를 불러오는 중...</span>
        </div>
      </div>
      <div v-else class="itemBoxDiv">
        <ul>
          <li v-for="(topSaving, index) in topSavings" :key="topSaving.savingId"
              class="d-inline-block itemBox text-start"
              :style="{ animationDelay: (index * 0.2 + 0.1) + 's' }">
            <div class="ranking">
              <span>
                {{ index === 0 ? '🥇' : index === 1 ? '🥈' : '🥉' }}
              </span>
            </div>
            <div class="goToDetail" @click="goToDetail(topSaving.savingId)">
              <div class="savingDepositMethod">
                <div class="savingMethod text-center">{{ topSaving.joinWay }}</div>
                <br>
                <div class="bankLogo d-inline">
                  <a :href="topSaving.bank?.bankUrl">
                    <img style="height: 25px;"
                         :src="topSaving.bank?.bankLogoUrl || '/img/emoji/bank.png'"
                         alt="Bank Logo"/>
                  </a>
                  <h3 class="d-inline">{{ topSaving.bank.bankName }}</h3>
                </div>
              </div>
              <br/>
              <div style="width: 300px;">
                <h4 class="savingName">{{ truncateText(topSaving.savingName, 15) }}</h4>
                <br/>
              </div>
              <div>
                <h3 style="font-weight: 600">{{ topSaving.interestRateList.interestRateType }}</h3>
              </div>
              <div style="display: flex; justify-content: space-between; color: grey">
                <div>
                  <li v-for="(rate, index) in topSaving.interestRateList" :key="index">
                    <h3>{{ rate.interestRateType }}</h3>
                    <h3 class="d-inline" style="color: rgba(68, 140, 116, 1);">
                      {{ rate.savingTerm }}</h3>
                    <h3 class="d-inline">개월</h3>
                  </li>
                </div>
                <div>
                  <h3>최고금리</h3>
                  <ul>
                    <li v-for="(rate, index) in topSaving.interestRateList" :key="index">
                      <h3>{{ rate.interestMaxRate }}%</h3>
                    </li>
                  </ul>
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
      <form @submit.prevent="fetchSavings">
        <div class="searchContainer">
          <input
              class="searchBar"
              type="text"
              placeholder="검색어를 입력해주세요"
              v-model="searchTerm"
              @focus="highlightInput"
              @blur="resetInput"
          />
          <button class="searchBtn" type="submit">검색</button>
        </div>

        <br>
        <div v-if="expanded" class="additional-info">
          <ul class="filterBar">
            <!-- 은행 필터 -->
            <li>
              <h4 style="font-weight: 700;">은행</h4>
              <!-- 전체 선택 / 해제 버튼 제거 -->
              <div v-if="groupedBanks && Object.keys(groupedBanks).length">
                <div v-for="(banks, type) in groupedBanks" :key="type">
                  <h5>{{ type }}</h5>
                  <div class="filter d-inline-flex flex-wrap" v-for="(bank, index) in banks" :key="bank.bankId + index">
                    <input
                        type="checkbox"
                        :id="'bank' + type + index"
                        @change="handleBankChange(bank.bankId)"
                        :checked="selectedBanks.includes(bank.bankId)"
                    />
                    <label :for="'bank' + type + index" :class="{ 'selected': selectedBanks.includes(bank.bankId) }">
                      {{ bank.bankName }}
                    </label>
                  </div>
                </div>
              </div>
            </li>

            <!-- 저축 기간 필터 -->
            <li>
              <h4 style="font-weight: 700;">저축 기간</h4>
              <!-- 전체 선택 / 전체 해제 버튼 제거 -->
              <div class="filter d-inline-flex flex-wrap" v-for="duration in [1, 3, 6, 12, 24, 36]" :key="duration">
                <input
                    type="checkbox"
                    :id="'duration' + duration"
                    @change="() => { selectDuration(duration); fetchSavings(); }"
                    :checked="selectedDurations.includes(duration)"
                />
                <label :for="'duration' + duration"
                       :class="{ 'selected': selectedDurations.includes(duration) }">{{ duration }}개월</label>
              </div>
            </li>
            <li>
              <h4 style="font-weight: 700;">이자 유형</h4>
              <div class="filter d-inline">
                <input
                    type="checkbox"
                    id="interest1"
                    @change="() => {
                    selectInterestType('단리');
                    fetchSavings();
                  }"
                    :checked="selectedInterestTypes.includes('단리')"
                />
                <label for="interest1" :class="{ 'selected': selectedInterestTypes.includes('단리') }">단리</label>
              </div>
              <div class="filter d-inline">
                <input
                    type="checkbox"
                    id="interest2"
                    @change="() => {
                    selectInterestType('복리');
                    fetchSavings();
                  }"
                    :checked="selectedInterestTypes.includes('복리')"
                />
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
                  :slides-per-view="3"
                  :centered-slides="false"
                  :edge-swipe-detection="true"
                  :pagination="{ clickable: true }"
              >
                <!-- 선택된 은행 (하나만 선택 가능) -->
                <SwiperSlide v-for="(bankId, index) in selectedBanks" :key="'bank' + index">
                  <div class="checkedFilter">
                    {{ getBankNameById(bankId) }}
                    <button @click="removeFilter(selectedBanks, bankId)">X</button>
                  </div>
                </SwiperSlide>

                <!-- 선택된 저축 기간 -->
                <SwiperSlide v-for="(duration, index) in selectedDurations" :key="'duration' + index">
                  <div class="checkedFilter">
                    {{ duration }}개월
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
        <!-- 토글 버튼 -->
        <div class="toggle-wrapper">
          <br>
          <span class="more-text" v-if="!expanded" @click="toggleText">
            상세 검색 열기<i class="ai-chevron-down"></i>
          </span>
          <span class="less-text" v-if="expanded" @click="toggleText">
            상세 검색 닫기<i class="ai-chevron-up"></i>
          </span>
        </div>

        <!-- 검색 및 상세 검색 폼 끝 -->
      </form>
    </div>

    <br><br>
    <div class="savingsContent">
      <div class="text-start">
        <h2 class="d-inline"><b>검색 결과</b> 테마상품</h2>
        <br><br><br>
      </div>
      <!-- 로딩 메시지 -->
      <div v-if="loading" class="loading-box">
        <div class="spinner-border text-primary" role="status">
          <span class="visually-hidden">데이터를 불러오는 중...</span>
        </div>
      </div>
      <div v-else>
        <div v-if="savings.length === 0 && !loading">검색 결과가 없습니다.</div>
        <div v-else class="itemBoxDiv">
          <ul>
            <li v-for="saving in savings" :key="saving.savingId"
                class="d-inline-block itemBox text-start"
                :style="{ animationDelay: '0.1s' }">
              <div class="goToDetail" @click="goToDetail(saving.savingId)">
                <div class="savingDepositMethod">
                  <div class="depositMethod text-center">{{ saving.joinWay }}</div>
                  <br>
                  <div class="bankLog d-inline">
                    <a :href="saving.bank?.bankUrl">
                      <img style="height: 25px;"
                           :src="saving.bank?.bankLogoUrl || '/img/emoji/bank.png'"
                           alt="Bank Logo"
                      />
                    </a>
                    <h3 class="d-inline">{{ saving.bank.bankName }}</h3>
                  </div>
                </div>
                <br/>
                <div style="width: 300px;">
                  <!-- **Truncated savingName** -->
                  <h4 class="savingName">{{ truncateText(saving.savingName, 15) }}</h4>
                  <br/>
                </div>
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
      </div>
      <nav aria-label="Page navigation">
        <ul class="pagination">
          <li class="page-item" :class="{ disabled: currentPage === 1 }">
            <button class="page-link" @click="changePage(currentPage - 1)" aria-label="Previous">
              <span aria-hidden="true">&laquo;</span>
            </button>
          </li>
          <template v-for="page in getPaginationPages()" :key="page">
            <li class="page-item" :class="{ active: currentPage === page }">
              <button class="page-link" @click="changePage(page)">{{ page }}</button>
            </li>
          </template>
          <li class="page-item" :class="{ disabled: currentPage === totalPages }">
            <button class="page-link" @click="changePage(currentPage + 1)" aria-label="Next">
              <span aria-hidden="true">&raquo;</span>
            </button>
          </li>
        </ul>
      </nav>
    </div>
    <br><br>
  </div>
</template>

<style scoped>
.loading-box {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 50px;
  padding: 30px;
  margin: 20px;
  text-align: center;
}

.selected-filters {
  overflow: hidden;
}

.checkedFilter {
  background-color: rgba(68, 140, 116, 1);
  color: white;
  font-size: 18px;
  border: 1px solid #bebebe;
  border-radius: 20px;
  padding: 5px;
  width: 220px;
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

li {
  list-style: none;
}

@keyframes riseUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.animate-on-load > * {
  opacity: 0;
  transform: translateY(20px);
  animation: riseUp 1s ease-out forwards;
}

.animate-on-load > *:nth-child(1) {
  animation-delay: 0.1s;
}

.animate-on-load > *:nth-child(2) {
  animation-delay: 0.2s;
}

.animate-on-load > *:nth-child(3) {
  animation-delay: 0.3s;
}

.animate-on-load > *:nth-child(4) {
  animation-delay: 0.4s;
}

.animate-on-load > *:nth-child(5) {
  animation-delay: 0.5s;
}

.animate-on-load > *:nth-child(6) {
  animation-delay: 0.6s;
}

.animate-on-load > *:nth-child(7) {
  animation-delay: 0.7s;
}

.animate-on-load > *:nth-child(8) {
  animation-delay: 0.8s;
}

.animate-on-load > *:nth-child(9) {
  animation-delay: 0.9s;
}

.animate-on-load > *:nth-child(10) {
  animation-delay: 1s;
}

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
  white-space: nowrap; /* Prevent text from wrapping */
  overflow: hidden; /* Hide overflowing text */
  text-overflow: ellipsis; /* Show ellipsis when text overflows */
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

.depositMethod {
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


.checkedFilter button {
  background-color: transparent;
  border: none;
  cursor: pointer;
  color: white;
}

.filter label {
  width: auto; /* 기존 고정 너비 제거 */
  min-width: 100px; /* 최소 너비 설정 */
  max-width: 200px; /* 최대 너비 설정하여 너무 길어지지 않도록 */
  padding: 10px;
  border: 1px solid lightgrey;
  border-radius: 30px;
  background-color: white;
  font-size: 15px;
  text-align: center;
  margin: 5px;
  cursor: pointer;
  flex-grow: 1; /* Flexbox를 사용하여 가용 공간을 활용 */
  white-space: nowrap; /* 텍스트가 길어질 경우 줄바꿈 방지 */
  overflow: hidden; /* 넘치는 텍스트 숨기기 */
  text-overflow: ellipsis; /* 넘치는 텍스트에 ... 표시 */
}

.filter.d-inline-flex {
  display: flex;
  flex-wrap: wrap;
  gap: 10px; /* 필터 간 간격 추가 */
}

.filter .selected {
  background-color: rgba(68, 140, 116, 1) !important;
  color: white;
  border: 1px solid rgba(68, 140, 116, 1);
  transition: background-color 0.3s ease, color 0.3s ease;
}

.filter .selected:hover {
  background-color: white;
  border: 1px solid rgba(48, 120, 96, 1);
}

.filter label:hover {
  border: 1px solid rgba(68, 140, 116, 1);
  color: rgba(68, 140, 116, 1);
}

.filter .selected:hover {
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

.searchContainer {
  margin-left: 15%;
}

.container {
  width: 80%;
  font-family: J3;
}

.selected {
  background-color: rgba(68, 140, 116, 1) !important;
  color: white;
  border: 1px solid rgba(68, 140, 116, 1);
  transition: background-color 0.3s ease, color 0.3s ease
}

.selected:hover {
  background-color: white;
  border: 1px solid rgba(48, 120, 96, 1);
}

.page-link {
  color: #0c0c0c;
}

.active > .page-link {
  background-color: rgba(68, 140, 116, 1);
  color: white;
  border: none;
}

.page-link:hover {
  color: rgba(68, 140, 116, 1);
}

.pagination {
  --bs-pagination-color: rgba(68, 140, 116, 1);
  --bs-pagination-hover-color: rgba(68, 140, 116, 1);
}

.pagination button {
  background-color: #e5e5e5;
}
</style>

