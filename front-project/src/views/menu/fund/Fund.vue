<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

// 반응형 데이터
const searchQuery = ref('');
const funds = ref([]);
const currentPage = ref(1);
const totalPages = ref(1);
const pageSize = ref(10); // 페이지당 항목 수
const pageLimit = ref(10); // 페이지당 페이지 수 (옵션, 백엔드에 따라 다름)

// 로딩 및 에러 상태
const isLoading = ref(false);
const error = ref(null);

// 날짜 형식 변환 함수
const formatDate = (dateString) => {
  if (!dateString) return '';
  const date = new Date(dateString);
  const year = date.getFullYear() % 100; // 2024 -> 24
  const month = date.getMonth() + 1; // 월 (0부터 시작하므로 1을 더함)
  const day = date.getDate(); // 일
  return `${year}.${month}.${day}`;
};

// 펀드 데이터 가져오기 함수
const fetchFunds = async (endpoint) => {
  isLoading.value = true;
  error.value = null;
  try {
    const response = await axios.get(endpoint, {
      params: {
        keyword: searchQuery.value,
        page: currentPage.value - 1, // Spring의 Pageable은 0부터 시작
        size: pageSize.value,
        pageLimit: pageLimit.value,
      },
    });
    funds.value = response.data;
    const pageInfo = response.data.pageInfo;
    if (pageInfo && pageInfo.totalSize && pageInfo.listLimit) {
      totalPages.value = Math.ceil(pageInfo.totalSize / pageInfo.listLimit);
    } else {
      totalPages.value = 1;
    }
  } catch (err) {
    error.value = '펀드 데이터를 불러오는 데 실패했습니다.';
    console.error(err);
  } finally {
    isLoading.value = false;
  }
};

// 검색 함수
const searchFundsFunc = () => {
  currentPage.value = 1;
  fetchFunds('/api/funds/search');
};

// 모든 펀드 조회 함수
const fetchAllFunds = () => {
  currentPage.value = 1;
  fetchFunds('/api/funds/all');
};

// 특정 페이지로 이동하는 함수
const goToPage = (page) => {
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page;
    if (searchQuery.value) {
      fetchFunds('/api/funds/search');
    } else {
      fetchFunds('/api/funds/all');
    }
  }
};

// 이전 페이지로 이동하는 함수
const goToPreviousPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--;
    if (searchQuery.value) {
      fetchFunds('/api/funds/search');
    } else {
      fetchFunds('/api/funds/all');
    }
  }
};

// 다음 페이지로 이동하는 함수
const goToNextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
    if (searchQuery.value) {
      fetchFunds('/api/funds/search');
    } else {
      fetchFunds('/api/funds/all');
    }
  }
};

// 컴포넌트 마운트 시 펀드 데이터 가져오기
onMounted(() => {
  fetchFunds('/api/funds/all'); // 기본적으로 모든 펀드를 가져옵니다. 검색 시 /search 엔드포인트 사용.
});
</script>

<template>
  <div class="bc">
    <div class="container">
      <h1 class="text-center">펀드 전체 보기</h1>

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
      <div v-if="isLoading" class="text-center mt-4">
        <p>데이터를 불러오는 중입니다...</p>
      </div>

      <!-- 에러 메시지 -->
      <div v-if="error" class="text-center text-danger mt-4">
        <p>{{ error }}</p>
      </div>

      <!-- 펀드 데이터 표시 -->
      <div v-if="!isLoading && !error" class="fund text-center">
        <div class="fundSearchResult text-center">
          <table class="fundSearchResultTable text-center">
            <thead>
            <tr>
              <th style="width: 45%;" rowspan="2">상품명</th>
              <th style="width: 10%;">기준가</th>
              <th style="width: 35%;" colspan="3">수익률(%)</th>
              <th style="width: 6%;" class="rate" rowspan="2">총 보수(연)</th>
              <th style="width: 5%;" rowspan="2">기준일</th>
            </tr>
            <tr>
              <th>순자산(억원)</th>
              <th style="width: 3%;">1개월<i class="ai-chevron-down"></i></th>
              <th style="width: 3%;">3개월<i class="ai-chevron-down"></i></th>
              <th style="width: 3%;" class="rate">1년<i class="ai-chevron-down"></i></th>
            </tr>
            </thead>
          </table>
        </div>
        <hr class="hr" />

        <div class="fundSearchResult text-center">
          <table class="fundSearchResultTable text-center">
            <tbody v-for="fund in funds" :key="fund.id">
            <tr>
              <td style="width: 45%;" rowspan="2"><h4>{{ fund.investGrade }}등급 {{ fund.fundFnm }}</h4></td>
              <td style="width: 10%;"><h4>{{ fund.gijunGa }}</h4></td>
              <td style="width: 35%;" colspan="3">
                <div class="bm">
                  <h5>벤치</h5>
                </div>
                <h4 class="bmFund">{{ fund.bmSuikJisu }}</h4>
              </td>
              <td style="width: 6%;" class="rate" rowspan="2"><h4>{{ fund.feeTot }}</h4></td>
              <td style="width: 5%;" rowspan="2"><h4>{{ formatDate(fund.gijunYmd) }}</h4></td>
            </tr>
            <tr>
              <td><h4>{{ fund.seoljAek }}</h4></td>
              <td style="width: 3%;">
                <div class="bm">
                  <h5>펀드</h5>
                </div>
                <h4 class="bmFund">{{ fund.suikRt1 }}</h4>
              </td>
              <td style="width: 3%;"><h4>{{ fund.suikRt3 }}</h4></td>
              <td style="width: 3%;" class="rate"><h4>{{ fund.suikRt12 }}</h4></td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- Pagination -->
      <nav aria-label="Page navigation" v-if="!isLoading && !error && totalPages > 1">
        <ul class="pagination justify-content-center mt-4">
          <li class="page-item" :class="{ disabled: currentPage === 1 }">
            <button class="page-link" @click="goToPreviousPage" :disabled="currentPage === 1">이전</button>
          </li>
          <li
              class="page-item d-none d-sm-block"
              v-for="page in totalPages"
              :key="page"
              :class="{ active: page === currentPage }"
          >
            <button class="page-link" @click="goToPage(page)">{{ page }}</button>
          </li>
          <li class="page-item d-sm-none">
            <span class="page-link pe-none">{{ currentPage }} / {{ totalPages }}</span>
          </li>
          <li class="page-item" :class="{ disabled: currentPage === totalPages }">
            <button class="page-link" @click="goToNextPage" :disabled="currentPage === totalPages">다음</button>
          </li>
        </ul>
      </nav>
    </div>
  </div>
</template>

<style scoped>
.bmFund {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%; /* bm와 같은 높이 설정 */
  margin: 0; /* 추가 여백 제거 */
}

.bm {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center; /* 수직 가운데 정렬 */
  height: 30px; /* bmFund와 동일한 높이로 설정 */
  border-radius: 30px;
  border: 1px solid lightgrey;
  width: 40px;
  font-size: 10px;
  margin-bottom: -31px;
}


.rate2 {
  border-right: none;
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

.suggest {
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

.fundBeanBox {
  border: 1px solid rgba(153, 153, 153, 1);
}

.bc {
  padding: 50px;
  background-color: rgba(248, 244, 244, 1);
  border-radius: 30px;
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
  background-color: white;
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
  --bs-pagination-hover-color: rgba(68, 140, 116, 1);
}

.text-danger {
  color: red;
}
</style>
