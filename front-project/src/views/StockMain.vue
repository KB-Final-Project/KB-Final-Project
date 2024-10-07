<template>
  <div class="stock-dashboard">
    <h1><b>국내주식</b></h1>
    <div class="container">
      <div v-if="error" class="error-message">{{ error }}</div>

      <!-- 이 시각 증시 (KOSPI, KOSDAQ, KOSPI200) -->
      <section class="current-stocks">
        <div class="section-header">
          <p class="title">이 시각 증시</p>
      
        </div>
        <div class="stock-cards">
          <div v-for="(stock, index) in currentStocks" :key="index" class="stock-card">
            <h3>{{ stock.name }}</h3>
            <p>{{ stock.index }}</p>
          </div>
        </div>
      </section>

      <p class="middle-title">현재 상위권 TOP3 🏆</p>
      <section class="top3-stocks">
        <div class="top3-cards">
          <div v-for="(stock, index) in top3Stocks" :key="index" class="top3-card">
            <h3>{{ stock.stockName }}</h3>
            <p>{{ stock.currentPrice }}</p>
            <p :class="{'positive': stock.priceChangePct > 0, 'negative': stock.priceChangePct < 0}">
              {{ stock.priceChange }} ({{ stock.priceChangePct }}%)
            </p>
            <router-link :to="'/stock/' + stock.stockCode">자세히 보기</router-link>
          </div>
        </div>
      </section>

      <!-- 주식 목록 섹션 -->
      <section class="stock-list">
        <p class="title">실시간 주식 목록</p>
        <router-link to="/stockdetail" class="more-link">더보기</router-link>
        <!-- 주식 목록 데이터 -->
        <table class="stock-table">
          <thead>
            <tr>
              <th @click="sortBy('stockName')" :class="{ active: sortKey === 'stockName' }">
                종목명 <span v-if="sortKey === 'stockName'" :class="{ 'sort-arrow': true, 'sort-reverse': sortOrder === -1 }"></span>
              </th>
              <th @click="sortBy('currentPrice')" :class="{ active: sortKey === 'currentPrice' }">
                현재가 <span v-if="sortKey === 'currentPrice'" :class="{ 'sort-arrow': true, 'sort-reverse': sortOrder === -1 }"></span>
              </th>
              <th @click="sortBy('priceChange')" :class="{ active: sortKey === 'priceChange' }">
                대비 <span v-if="sortKey === 'priceChange'" :class="{ 'sort-arrow': true, 'sort-reverse': sortOrder === -1 }"></span>
              </th>
              <th @click="sortBy('priceChangePct')" :class="{ active: sortKey === 'priceChangePct' }">
                등락률 <span v-if="sortKey === 'priceChangePct'" :class="{ 'sort-arrow': true, 'sort-reverse': sortOrder === -1 }"></span>
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(stock, index) in sortedStocks" :key="index">
              <td>{{ stock.stockName }}</td>
              <td>{{ stock.currentPrice }}</td>
              <td :class="{'positive': stock.priceChange > 0, 'negative': stock.priceChange < 0}">
                {{ stock.priceChange }}
              </td>
              <td :class="{'positive': stock.priceChangePct > 0, 'negative': stock.priceChangePct < 0}">
                {{ stock.priceChangePct }}%
              </td>
            </tr>
          </tbody>
        </table>
      </section>

      <!-- 카테고리 섹션 -->
      <section class="categories">
        <div class="section-header">
          <p class="title">카테고리</p>
          <router-link to="/stockcategory" class="more-link">더보기</router-link>
        </div>
        <div class="category-cards">
          <div v-for="(category, index) in categories" :key="index" class="category-card">
            <img :src="category.icon" :alt="category.name">
            <h3>{{ category.name }}</h3>
            <p>{{ category.change }}%</p>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      stocks: [], // 웹소켓에서 받아온 10개 주식 데이터
      currentStocks: [], // KOSPI, KOSDAQ, KOSPI200 데이터
      top3Stocks: [], // 등락률 상위 3개 주식
      error: null, // 에러 메시지
      sortKey: 'stockName', // 정렬 기준 키
      sortOrder: 1, // 정렬 순서 (1: 오름차순, -1: 내림차순)
      categories: [
        { name: '운수·창고', change: -3.12, icon: '/path/to/icon1.png' },
        { name: '의약품', change: 2.06, icon: '/path/to/icon2.png' },
        { name: '통신업', change: 1.96, icon: '/path/to/icon3.png' },
        { name: '보험', change: 1.81, icon: '/path/to/icon4.png' },
      ]
    };
  },

  computed: {
    // 정렬된 주식 목록
    sortedStocks() {
      return [...this.stocks].sort((a, b) => {
        let aValue = a[this.sortKey];
        let bValue = b[this.sortKey];
        
        // 숫자 정렬을 위한 변환
        if (typeof aValue === 'string' && !isNaN(aValue)) {
          aValue = parseFloat(aValue);
          bValue = parseFloat(bValue);
        }
        
        if (aValue < bValue) return -1 * this.sortOrder;
        if (aValue > bValue) return 1 * this.sortOrder;
        return 0;
      });
    }
  },

  async mounted() {
    try {
      await this.fetchStockData();
      this.startWebSocket();
    } catch (error) {
      console.error('초기 데이터 로드 중 오류 발생:', error);
      this.error = '주식 데이터를 불러오는 중 오류가 발생했습니다.';
    }
  },

  methods: {
    // 초기 주식 데이터를 가져오는 메서드
    async fetchStockData() {
      try {
        const response = await axios.get('http://localhost:8080/api/websocket/prices');
        this.stocks = Object.values(response.data);
        this.updateTop3Stocks();
        // API 요청: 주식 데이터와 지수 데이터 가져오기

        const kospiResponse = await axios.get('http://localhost:8080/api/index/kospi');
        const kosdaqResponse = await axios.get('http://localhost:8080/api/index/kosdaq');
        const kospi200Response = await axios.get('http://localhost:8080/api/index/kospi200');

        // 데이터 저장
    
        this.currentStocks = [
          { name: 'KOSPI', index: kospiResponse.data },
          { name: 'KOSDAQ', index: kosdaqResponse.data },
          { name: 'KOSPI200', index: kospi200Response.data }
        ];
      } catch (error) {
        console.error('주식 데이터를 가져오는 중 오류 발생:', error);
       
      }
    },
    

    // WebSocket 연결 시작
    startWebSocket() {
      const ws = new WebSocket('ws://localhost:8080/ws'); // WebSocket 서버 주소로 변경해야 함

      ws.onmessage = (event) => {
        const data = JSON.parse(event.data);
        this.updateStockData(data);
      };

      ws.onerror = (error) => {
        console.error('WebSocket 오류:', error);
       
      };
    },

    // 웹소켓에서 받은 데이터로 주식 정보 업데이트
    updateStockData(data) {
      const index = this.stocks.findIndex(stock => stock.stockCode === data.stockCode);
      if (index !== -1) {
        this.stocks[index] = { ...this.stocks[index], ...data };
      } else if (this.stocks.length < 10) {
        this.stocks.push(data);
      }
      this.updateTop3Stocks();
    },

    // Top3 주식 업데이트 메서드
    updateTop3Stocks() {
      this.top3Stocks = [...this.stocks]
        .sort((a, b) => b.priceChangePct - a.priceChangePct)
        .slice(0, 3);
    },

    // 정렬 메서드
    sortBy(key) {
      if (this.sortKey === key) {
        // 같은 키로 다시 정렬하면 순서를 반대로
        this.sortOrder *= -1;
      } else {
        // 다른 키로 정렬하면 오름차순으로 시작
        this.sortKey = key;
        this.sortOrder = 1;
      }
    }
  }
};
</script>

<style scoped>
.stock-dashboard {
  background-color: white;
  padding: 20px;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.title {
  font-weight: bold;
  font-size: 24px;
  margin: 0;
}

.more-link {
  color: #448c74;
  text-decoration: none;
  font-weight: bold;
}

.current-stocks,
.top3-stocks,
.stock-list,
.categories {
  margin-bottom: 40px;
  background-color: #f5f8f4;
  border-radius: 10px;
  padding: 20px;
}

.stock-cards,
.top3-cards,
.category-cards {
  display: flex;
  justify-content: space-between;
  gap: 20px;
}

.stock-card,
.top3-card,
.category-card {
  background-color: white;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  flex: 1;
  text-align: center;
}

.category-card img {
  width: 50px;
  height: 50px;
  margin-bottom: 10px;
}

.stock-table {
  width: 100%;
  border-collapse: collapse;
}

.stock-table th,
.stock-table td {
  padding: 10px;
  text-align: center;
  border-bottom: 1px solid #ddd;
}

.stock-table th {
  cursor: pointer;
  user-select: none;
  position: relative;
}

.stock-table th.active {
  background-color: #f0f0f0;
}

.sort-arrow {
  display: inline-block;
  width: 0;
  height: 0;
  margin-left: 5px;
  vertical-align: middle;
  border-left: 4px solid transparent;
  border-right: 4px solid transparent;
  border-bottom: 4px solid #000;
}

.sort-arrow.sort-reverse {
  border-bottom: none;
  border-top: 4px solid #000;
}

.positive {
  color: red;
}

.negative {
  color: blue;
}

h1 {
  text-align: center;
  margin-bottom: 30px;
}

.middle-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 15px;
}
</style>
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
  
  .stock-table th {
    cursor: pointer;
    user-select: none;
    position: relative;
  }
  
  .stock-table th.active {
    background-color: #f0f0f0;
  }
  
  .sort-arrow {
    display: inline-block;
    width: 0;
    height: 0;
    margin-left: 5px;
    vertical-align: middle;
    border-left: 4px solid transparent;
    border-right: 4px solid transparent;
    border-bottom: 4px solid #000;
  }
  
  .sort-arrow.sort-reverse {
    border-bottom: none;
    border-top: 4px solid #000;
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
  </style> 