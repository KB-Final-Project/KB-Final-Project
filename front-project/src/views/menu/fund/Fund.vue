<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';

const searchQuery = ref('');
const allFunds = ref([]); // 전체 펀드 데이터
const displayedFunds = ref([]); // 현재 페이지에 표시할 펀드 데이터
const currentPage = ref(1);
const totalPages = ref(1);
const pageSize = ref(20); // 페이지당 항목 수

const isLoading = ref(false);
const error = ref(null);

const sortKey = ref('');
const sortOrder = ref('desc');

const formatDate = (dateString) => {
  if (!dateString) return '';
  const date = new Date(dateString);
  const year = date.getFullYear() % 100;
  const month = date.getMonth() + 1;
  const day = date.getDate();
  return `${year}.${month}.${day}`;
};

const fetchAllFunds = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    const response = await axios.get('/api/funds/all');
    allFunds.value = response.data; // 전체 데이터 저장
    totalPages.value = Math.ceil(allFunds.value.length / pageSize.value); // 페이지 수 계산
    currentPage.value = 1; // 첫 페이지로 설정
    paginateFunds(); // 첫 페이지 데이터를 표시
  } catch (err) {
    error.value = '펀드 데이터를 불러오는 데 실패했습니다.';
    console.error(err);
  } finally {
    isLoading.value = false;
  }
};

const searchFundsFunc = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    const response = await axios.get('/api/funds/search', {
      params: {
        keyword: searchQuery.value,
      },
    });
    allFunds.value = response.data;
    totalPages.value = Math.ceil(allFunds.value.length / pageSize.value);
    currentPage.value = 1;
    paginateFunds();
  } catch (err) {
    error.value = '검색 결과를 불러오는 데 실패했습니다.';
    console.error(err);
  } finally {
    isLoading.value = false;
  }
};

const paginateFunds = () => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  displayedFunds.value = allFunds.value.slice(start, end);
};

const goToPage = (page) => {
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page;
    paginateFunds();
  }
};

const goToPreviousPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--;
    paginateFunds();
  }
};

const goToNextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
    paginateFunds();
  }
};

const sortFunds = (key) => {
  if (sortKey.value === key) {
    sortOrder.value = sortOrder.value === 'asc' ? 'desc' : 'asc';
  } else {
    sortKey.value = key;
    sortOrder.value = 'desc';
  }

  allFunds.value.sort((a, b) => {
    const aVal = a[key];
    const bVal = b[key];
    if (sortOrder.value === 'desc') {
      return bVal - aVal;
    } else {
      return aVal - bVal;
    }
  });

  paginateFunds();
};

const sortIconClass = (key) => {
  if (sortKey.value === key) {
    return sortOrder.value === 'asc' ? 'ai ai-chevron-up' : 'ai ai-chevron-down';
  }
  return 'ai ai-chevron-down';
};

const getRateClass = (value) => {
  if (value > 0) return 'text-danger';
  if (value < 0) return 'text-primary';
  return 'text-muted'; // 회색 (없음)
};

const visiblePages = computed(() => {
  const pages = [];
  const maxVisible = 5; // 최대 5개 페이지 버튼
  let start = Math.max(currentPage.value - 2, 1);
  let end = Math.min(start + maxVisible - 1, totalPages.value);

  if (end - start < maxVisible - 1) {
    start = Math.max(end - maxVisible + 1, 1);
  }

  for (let i = start; i <= end; i++) {
    pages.push(i);
  }
  return pages;
});

onMounted(() => {
  fetchAllFunds();
});
</script>

<template>
  <div class="bc">
    <br><br><br><br>
    <div class="container">
      <h1 class="text-center">펀드 전체 보기</h1>
      <br><br><br><br>
      <div class="text-center">
        <h2 class="d-inline search">상품 검색</h2>
        <input
            class="searchBar"
            type="text"
            placeholder="검색어를 입력해주세요"
            v-model="searchQuery"
            @keyup.enter="searchFundsFunc"
        />
        <button class="searchBtn" type="button" @click="searchFundsFunc">검색</button>
        <button class="searchBtn" type="button" @click="fetchAllFunds">전체보기</button>
      </div>

      <!-- 로딩 메시지 -->
      <div v-if="isLoading" class="loading-box">
        <div class="spinner-border text-primary" role="status">
          <span class="visually-hidden">데이터를 불러오는 중...</span>
        </div>
      </div>

      <!-- 에러 메시지 -->
      <div v-if="error" class="text-center text-danger mt-4">
        <p>{{ error }}</p>
      </div>

      <div v-if="!isLoading && !error" class="fund text-center">
        <div class="fundSearchResult text-center">
          <table class="fundSearchResultTable text-center">
            <thead>
            <tr>
              <th style="width: 40%;" rowspan="2">상품명</th>
              <th style="width: 10%;">기준가</th>
              <th style="width: 35%;" colspan="4">수익률(%)</th>
              <th style="width: 6%;" class="rate" rowspan="2">총 보수(연)</th>
              <th style="width: 6%;" rowspan="2">기준일</th>
            </tr>
            <tr>
              <th>순자산(억원)</th>
              <th style="width: 3%; cursor: pointer;" @click="sortFunds('suikRt1')">
                1개월 <i style="font-size: 2rem;" :class="sortIconClass('suikRt1')"></i>
              </th>
              <th style="width: 3%; cursor: pointer;" @click="sortFunds('suikRt3')">
                3개월 <i style="font-size: 2rem;" :class="sortIconClass('suikRt3')"></i>
              </th>
              <th style="width: 3%; cursor: pointer;" @click="sortFunds('suikRt6')">
                6개월 <i style="font-size: 2rem;" :class="sortIconClass('suikRt6')"></i>
              </th>
              <th style="width: 3%; cursor: pointer;" @click="sortFunds('suikRt12')">
                1년 <i style="font-size: 2rem;" :class="sortIconClass('suikRt12')"></i>
              </th>
            </tr>
            </thead>
          </table>
        </div>
        <hr class="hr"/>

        <div class="fundSearchResult text-center">
          <table class="fundSearchResultTable text-center">
            <tbody v-for="fund in displayedFunds" :key="fund.id">
            <tr>
              <td class="fundName" style="width: 40%;" rowspan="2"><h4>{{ fund.fundFnm }}</h4></td>
              <td style="width: 10%;"><h4>{{ fund.gijunGa }}</h4></td>
              <td style="width: 35%;" colspan="4">
                <div class="grade"><h5>{{ fund.investGrade }}등급</h5></div>
              </td>
              <td style="width: 6%;" class="rate" rowspan="2"><h4>{{ fund.feeTot }}</h4></td>
              <td style="width: 6%;" rowspan="2"><h4>{{ formatDate(fund.gijunYmd) }}</h4></td>
            </tr>
            <tr>
              <td><h4>{{ fund.navTot }}</h4></td>
              <td style="width: 3%;">
                <h4 :class="getRateClass(fund.suikRt1)">
                  {{ fund.suikRt1 != null ? fund.suikRt1 : '없음' }}
                </h4>
              </td>
              <td style="width: 3%;">
                <h4 :class="getRateClass(fund.suikRt3)">
                  {{ fund.suikRt3 != null ? fund.suikRt3 : '없음' }}
                </h4>
              </td>
              <td style="width: 3%;">
                <h4 :class="getRateClass(fund.suikRt6)">
                  {{ fund.suikRt6 != null ? fund.suikRt6 : '없음' }}
                </h4>
              </td>
              <td style="width: 3%;">
                <h4 :class="getRateClass(fund.suikRt12)">
                  {{ fund.suikRt12 != null ? fund.suikRt12 : '없음' }}
                </h4>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <!-- Pagination -->
    <nav aria-label="Page navigation example" v-if="totalPages > 1">
      <ul class="pagination justify-content-center mt-4">
        <li class="page-item" :class="{ disabled: currentPage === 1 }">
          <a @click.prevent="goToPreviousPage" class="page-link" aria-label="Prev page">
            <i class="ai ai-arrow-left fs-4"></i> <!-- fs-4 클래스 적용 -->
          </a>
        </li>
        <li
            class="page-item"
            v-for="page in visiblePages"
            :key="page"
            :class="{ active: page === currentPage }"
        >
          <a @click.prevent="goToPage(page)" class="page-link">{{ page }}</a>
        </li>
        <li class="page-item" :class="{ disabled: currentPage === totalPages }">
          <a @click.prevent="goToNextPage" class="page-link" aria-label="Next page">
            <i class="ai ai-arrow-right fs-4"></i> <!-- fs-4 클래스 적용 -->
          </a>
        </li>
      </ul>
    </nav>
  </div>
</template>

<style scoped>
.fundName{
  font-family: J3;
  color: rgba(68, 140, 116, 1);
}

.grade {
  border: 1px solid lightgrey;
  border-radius: 30px;
  color: rgb(121, 121, 121);
  width: 50px;
  margin: 0 auto;
}

tbody td {
  padding: 10px;
}

tbody tr {
  border-bottom: 1px solid lightgrey;
}

.fundSearchResultTable th {
  font-size: 20px;
}

.sortIconClass {
  font-size: 25px;
}

.hr {
  display: inline-block;
  width: 100%;
  height: 5px;
  color: #e5e4e4ff;
  background-color: #e5e4e4;
}

thead tr th {
  border-bottom: 1px solid lightgrey;
  border-right: 1px solid lightgrey;
}

thead tr:nth-child(2) th:last-child {
  border-left: none;
  border-bottom: none;
}

thead tr:last-child th {
  border-bottom: none;
}

thead tr th:first-child {
  border-bottom: none;
}

thead tr:first-child th:last-child {
  border-bottom: none;
  border-right: none;
}

.rate {
  border-bottom: none;
}

thead tr:last-child th:last-child {
  border: none;
  border-right: 1px solid lightgrey;
}

.fundSearchResult {
  border: 1px solid lightgrey;
  border-radius: 30px;
  padding: 10px;
  background-color: white;
}

.fundSearchResultTable {
  width: 100%;
  table-layout: fixed;
  text-align: start;
  border-radius: 20px;
  background-color: white;
}

.loading-box {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 50px;
  padding: 30px;
  margin: 20px;
  text-align: center;
}

.bc {
  padding: 50px;
  background-color: rgba(248, 244, 244, 1);
  border-radius: 30px;
  font-family: J3;
}

.searchBar {
  width: 50%;
  max-width: 500px;
  height: 50px;
  border: 1px solid rgba(215, 221, 227, 1);
  border-radius: 30px;
  padding: 0 20px;
  margin: 10px 0;
}

.fund {
  margin-top: 30px;
  border-radius: 30px;
  padding: 20px;
}

.searchBtn {
  width: 80px;
  height: 50px;
  color: white;
  border: none;
  border-radius: 30px;
  background-color: rgba(68, 140, 116, 1);
  margin: 1%;
  cursor: pointer;
  transition: background-color 0.3s;
}

.searchBtn:active,
.searchBtn:hover {
  background-color: lightgrey;
  color: black;
}

.search {
  margin: 30px;
}

.page-link {
  color: rgba(68, 140, 116, 1);
}

.active > .page-link {
  background-color: rgba(68, 140, 116, 1);
  border: none;
  color: white;
}

.page-link:hover {
  color: white;
  background-color: rgba(68, 140, 116, 1);
}

.pagination {
  --bs-pagination-color: rgba(68, 140, 116, 1);
  --bs-pagination-hover-color: rgb(255, 255, 255);
}

.text-danger {
  color: red;
}

.text-primary {
  color: blue;
}

.text-muted {
  color: grey;
}

.icon-large {
  font-size: 2rem;
}

.icon-medium {
  font-size: 1.5rem;
}

.icon-small {
  font-size: 1rem;
}
</style>
