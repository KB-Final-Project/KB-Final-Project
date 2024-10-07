<template>
    <div class="stock-category">
      <h1>전체 카테고리</h1>
      <div class="category-list">
        <div v-for="category in categories" :key="category.name" class="category-item">
          <div class="category-header">
            <img :src="getCategoryIcon(category.name)" :alt="category.name">
            <h2>{{ category.name }}</h2>
            <p :class="{ 'positive': category.change > 0, 'negative': category.change < 0 }">
              {{ category.change.toFixed(2) }}%
            </p>
          </div>
          <table class="stock-table">
            <thead>
              <tr>
                <th>종목명</th>
                <th>현재가</th>
                <th>전일대비</th>
                <th>등락률</th>
                <th>누적거래대금(백만)</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="stock in category.stocks" :key="stock.stockCode">
                <td>{{ stock.stockName }}</td>
                <td>{{ formatNumber(stock.currentPrice) }}</td>
                <td :class="{ 'positive': stock.change > 0, 'negative': stock.change < 0 }">
                  {{ formatNumber(stock.change) }}
                </td>
                <td :class="{ 'positive': stock.changePercent > 0, 'negative': stock.changePercent < 0 }">
                  {{ stock.changePercent.toFixed(2) }}%
                </td>
                <td>{{ formatNumber(stock.accumulatedTradingValue) }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        categories: []
      };
    },
    async mounted() {
      await this.fetchCategoryData();
    },
    methods: {
      async fetchCategoryData() {
        try {
          const response = await axios.get('http://localhost:8080/api/categories');
          this.categories = response.data;
        } catch (error) {
          console.error('카테고리 데이터를 가져오는 중 오류 발생:', error);
        }
      },
      getCategoryIcon(categoryName) {
        // 카테고리 아이콘 매핑
        const iconMap = {
          '전기·전자': '/icons/electronics.png',
          '보험': '/icons/insurance.png',
          '의약품': '/icons/medicine.png',
          '제조업': '/icons/manufacturing.png',
          '철강·금속': '/icons/metal.png',
          '화학': '/icons/chemistry.png',
          '비금속광물': '/icons/mineral.png',
          '음식료품': '/icons/food.png',
          '운수·창고': '/icons/transport.png',
          '종합': '/icons/general.png',
          '운수·장비': '/icons/equipment.png',
          '의료정밀': '/icons/medical.png',
          '기계': '/icons/machine.png',
          // 나머지 카테고리에 대한 아이콘 추가
        };
        return iconMap[categoryName] || '/icons/default.png';
      },
      formatNumber(value) {
        return new Intl.NumberFormat('ko-KR').format(value);
      }
    }
  };
  </script>
  
  <style scoped>
  .stock-category {
    padding: 20px;
    background-color: #f5f8f4;
  }
  
  h1 {
    text-align: center;
    margin-bottom: 30px;
    color: #333;
  }
  
  .category-list {
    display: flex;
    flex-direction: column;
    gap: 30px;
  }
  
  .category-item {
    background-color: white;
    border-radius: 10px;
    padding: 20px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  .category-header {
    display: flex;
    align-items: center;
    margin-bottom: 20px;
  }
  
  .category-header img {
    width: 40px;
    height: 40px;
    margin-right: 15px;
  }
  
  .category-header h2 {
    margin: 0;
    flex-grow: 1;
    font-size: 24px;
    color: #333;
  }
  
  .stock-table {
    width: 100%;
    border-collapse: collapse;
  }
  
  .stock-table th, .stock-table td {
    padding: 10px;
    text-align: right;
    border-bottom: 1px solid #eee;
  }
  
  .stock-table th:first-child, .stock-table td:first-child {
    text-align: left;
  }
  
  .stock-table th {
    background-color: #f0f0f0;
    font-weight: bold;
    color: #333;
  }
  
  .positive {
    color: #e31b23;
  }
  
  .negative {
    color: #1261c4;
  }
  </style>