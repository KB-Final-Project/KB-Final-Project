<template>
  <div class="stock-dashboard">
    <h1><b>국내주식</b></h1>
    <div class="container">
      <div v-if="error" class="error-message">
        {{ error }}
      </div>

      <section class="current-stocks">
        <p class="title">이 시각 증시</p>
        <div class="stock-cards">
          <div v-for="(stock, index) in currentStocks" :key="index" class="stock-card">
            <h3>{{ stock.rprs_mrkt_kor_name }}</h3>
            <p>{{ stock.stck_prpr }}</p>
            <p>{{ stock.prdy_vrss }} ({{ stock.prdy_ctrt }}%)</p>
          </div>
        </div>
      </section>

      <div class="crypto-chart-container mx-n4">
        <div id="chart-crypto-price" ref="chartElement" :data-height="420"></div>
      </div>

      <p class="middle-title">현재 상위권 TOP3 🏆</p>
      <section class="top3-stocks">
        <div class="top3-cards">
          <div v-for="(stock, index) in top3Stocks" :key="index" class="top3-card">
            <h3>{{ stock.rprs_mrkt_kor_name }}</h3>
            <p>{{ stock.stck_prpr }}</p>
            <p>{{ stock.prdy_vrss }} ({{ stock.prdy_ctrt }}%)</p>
            <router-link :to="'/stock/' + stock.stockCode">자세히 보기</router-link>
          </div>
        </div>
      </section>


      <!-- 주식 목록 섹션 -->
      <section class="stock-list">
        <p class="title">주식 목록</p>
        <div class="search-bar">
          <input v-model="searchKeyword" placeholder="키워드를 입력해주세요" @keyup.enter="searchStocks">
          <button @click="searchStocks">검색</button>
        </div>

        <!-- 주식 목록 데이터 -->
        <table class="stock-table">
          <thead>
            <tr>
              <th>Stock Code</th>
              <th>Stock Name</th>
              <th>Current Price</th>
              <th>Change</th>
              <th>Change Rate</th>
              <th>Volume</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(stock, index) in paginatedStocks" :key="index">
              <td>{{ stock.stck_shrn_iscd }}</td>
              <td>{{ stock.rprs_mrkt_kor_name }}</td>
              <td>{{ stock.stck_prpr }}</td>
              <td :class="{'positive': stock.prdy_vrss > 0, 'negative': stock.prdy_vrss < 0}">
                {{ stock.prdy_vrss }}
              </td>
              <td :class="{'positive': stock.prdy_ctrt > 0, 'negative': stock.prdy_ctrt < 0}">
                {{ stock.prdy_ctrt }}%
              </td>
              <td>{{ stock.acml_vol }}</td>
            </tr>
          </tbody>
        </table>

        <!-- 페이지네이션 -->
        <div class="pagination">
          <button @click="changePage(-1)" :disabled="currentPage === 1">이전</button>
          <span>{{ currentPage }} / {{ totalPages }}</span>
          <button @click="changePage(1)" :disabled="currentPage === totalPages">다음</button>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
// import { Line, Bar } from 'vue-chartjs';
// import { Chart as ChartJS, Title, Tooltip, Legend, LineElement, BarElement, CategoryScale, LinearScale, PointElement } from 'chart.js';
import Choices from '../js/choices.min.js';
import graph from '../js/graph.js';
// ChartJS.register(Title, Tooltip, Legend, LineElement, BarElement, CategoryScale, LinearScale, PointElement);


export default {
  data() {
    return {
      stocks: [], // 전체 주식 목록
      error: null, // 에러 메시지
      searchKeyword: '', // 검색 키워드
      currentPage: 1, // 현재 페이지
      totalPages: 1, // 전체 페이지 수
      itemsPerPage: 10 // 한 페이지에 표시할 항목 수
    };
  },
  
  computed: {
    // 페이지네이션 된 주식 목록을 계산
    paginatedStocks() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.stocks.slice(start, end);
    }
  },
  async mounted() {
    try {
      await this.fetchStocks(); // 주식 목록을 초기 로드
    } catch (error) {
      console.error('초기 데이터 로드 중 오류 발생:', error);
    }
        // Choices.js 사용: select 요소에 대한 UI 커스터마이징
        const selectElement = this.$refs.selectElement;
    if (selectElement) {
      new Choices(selectElement, {
        searchEnabled: false,
      });
    }

    // graph.js 사용: 차트 생성
    const chartElement = this.$refs.chartElement;
    if (chartElement) {
      graph.renderChart(chartElement, { height: chartElement.dataset.height });
    }
  },
  methods: {
    // 주식 데이터를 가져오는 메서드
    async fetchStocks() {
      try {
        const response = await axios.get('/api/stocks', {
          params: { stockCodes: '005930,000660' }, // 임의의 종목 코드를 사용
          headers: {
            tr_id: 'FHKST01010100' // 헤더에 tr_id 포함
          }
        });
        this.stocks = response.data.map(item => item.output);
        this.totalPages = Math.ceil(this.stocks.length / this.itemsPerPage); // 총 페이지 계산
      } catch (error) {
        console.error('주식 목록을 가져오는 중 오류 발생:', error);
        this.error = '주식 데이터를 불러오는 중 오류가 발생했습니다.';
      }
    },
    searchStocks() {
      // 검색어가 있는 경우 해당 주식을 필터링
      const filteredStocks = this.stocks.filter(stock =>
        stock.rprs_mrkt_kor_name.includes(this.searchKeyword)
      );
      this.stocks = filteredStocks;
      this.currentPage = 1; // 검색 후 첫 페이지로 이동
      this.totalPages = Math.ceil(this.stocks.length / this.itemsPerPage); // 총 페이지 계산
    },
    changePage(direction) {
      this.currentPage += direction;
    }
  }
};
</script>


<style scoped>
.stock-dashboard {
  background-color: white;
}

.title {
  padding-top: 20px;
  font-weight: bold;
  margin-bottom: 20px;
  text-align: center;
  font-size: 30px;
}

.container {
  padding-top: 30px;
}

.stock-table {
  width: 100%;
  border-collapse: collapse;
}

.stock-table th, .stock-table td {
  padding: 10px;
  text-align: center;
  border-bottom: 1px solid #ddd;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
  margin-top: 20px;
}

.pagination button {
  padding: 10px 15px;
  border: none;
  background-color: #448c74;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}

.pagination span {
  font-size: 14px;
  color: #333;
}

/* 가격 변동 색상 */
.positive {
  color: red;
}

.negative {
  color: blue;
}
.stock-dashboard {
    background-color: white;
}

.middle-title {
    padding-top: 20px;
    font-weight: bold;
    margin-bottom: 20px;
    font-size: 30px;
}

.container {
    padding-top: 30px;
}

.title {
    padding-top: 20px;
    font-weight: bold;
    margin-bottom: 20px;
    text-align: center;
    font-size: 30px;
}

h1 {
    font-weight: bold;
    margin-bottom: 20px;
    text-align: center;
    font-size: 40px;
}

.current-stocks,
.top3-stocks,
.stock-list {
    margin-bottom: 40px;
    border-radius: 30px;
    background-color: #f5f8f4;
}

.current-stocks h2,
.top3-stocks h2,
.stock-list h2 {
    font-size: 18px;
    font-weight: bold;
    color: #333;
    margin-bottom: 15px;
    text-align: center;
}

.stock-cards {
    display: flex;
    justify-content: space-between;
    gap: 20px;
    padding: 20px;
    border-radius: 10px;
    background-color: #ffffff;
}

.stock-card,
.top3-card {
    background-color: #ffffff;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    text-align: center;
    flex: 1;
}

.stock-card h3,
.top3-card h3 {
    font-size: 16px;
    margin-bottom: 10px;
    color: #333;
}

.stock-card p,
.top3-card p {
    font-size: 14px;
    color: #777;
}

.top3-cards {
    display: flex;
    justify-content: space-between;
    gap: 20px;
    padding: 20px;
    border-radius: 10px;
    background-color: #ffffff;
}

.top3-card router-link {
    display: block;
    margin-top: 10px;
    font-size: 12px;
    color: #448c74;
    text-decoration: none;
    font-weight: bold;
}

.stock-list .search-bar {
    display: flex;
    justify-content: center;
    margin-bottom: 20px;
}

.stock-list .search-bar input {
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ccc;
    width: 300px;
}

.stock-list .search-bar button {
    margin-left: 10px;
    padding: 10px 20px;
    background-color: #448c74;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.stock-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px 20px;
    background-color: #ffffff;
    border-radius: 10px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    margin-bottom: 10px;
}

.stock-item p {
    margin: 0;
    font-size: 14px;
    color: #333;
}

.pagination {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 10px;
    margin-top: 20px;
}

.pagination button {
    padding: 10px 15px;
    border: none;
    background-color: #448c74;
    color: white;
    border-radius: 5px;
    cursor: pointer;
}

.pagination span {
    font-size: 14px;
    color: #333;
}
</style>

<style lang="scss">
:root,
[data-bs-theme="light"] {
  --x-gray-200: #e2e8f0;
  --x-light-border-subtle: #e2e8f0;
  --x-border-color: #e2e8f0;
  --x-purple: #8957ff;
  --x-primary: #8957ff;
  --x-link-color: #8957ff;
}

.btn-primary {
  --x-btn-bg: #8957ff;
  --x-btn-border-color: #8957ff;
  --x-btn-hover-bg: #6e46cc;
  --x-btn-hover-border-color: #6e46cc;
  --x-btn-active-bg: #6e46cc;
  --x-btn-active-border-color: #6741bf;
  --x-btn-disabled-bg: #8957ff;
  --x-btn-disabled-border-color: #8957ff;
}

</style> 