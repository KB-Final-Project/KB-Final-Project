<template>
  <div class="stock-chart">
    <!-- 상단 정보 -->
    <div class="stock-header">
      <div class="stock-title">
        <h1>{{ stockName }}</h1>
        <p class="stock-code">{{ stockCode }}</p>
      </div>
      <div class="stock-price">
        <h2>{{ currentPrice }}원</h2>
        <p :class="{ 'positive': priceChange > 0, 'negative': priceChange < 0 }">
          {{ priceChange > 0 ? '+' : '' }}{{ priceChange }}원 ({{ priceChangePct }}%)
        </p>
      </div>
    </div>

    <!-- 세부 정보 -->
    <div class="stock-details">
      <div class="detail-item"><span>거래량</span><span>{{ volume }}</span></div>
      <div class="detail-item"><span>시가총액</span><span>{{ htsAvls }}</span></div>
      <div class="detail-item"><span>52주 최고</span><span>{{ w52Hgpr }}</span></div>
      <div class="detail-item"><span>52주 최저</span><span>{{ w52Lwpr }}</span></div>
    </div>

    <!-- 탭 메뉴 -->
    <div class="tabs">
      <button v-for="tab in tabs" :key="tab" @click="currentTab = tab" :class="{ active: currentTab === tab }">
        {{ tab }}
      </button>
    </div>

    <!-- 탭 콘텐츠 -->
    <div class="tab-content">
      <!-- 차트 탭 -->
      <div v-if="currentTab === '차트'" class="chart-container">
        <apexchart type="candlestick" height="350" :options="chartOptions" :series="chartSeries"></apexchart>
        <div class="time-range">
          <button v-for="range in timeRanges" :key="range" :class="{ active: selectedRange === range }" @click="changeTimeRange(range)">
            {{ range }}
          </button>
        </div>
      </div>

      <!-- 뉴스 탭 -->
      <div v-else-if="currentTab === '뉴스'" class="news-container">
        <div v-for="news in newsItems" :key="news.link" class="news-item" @click="openNewsModal(news)">
          <h3>{{ news.title }}</h3>
          <p>{{ news.description }}</p>
        </div>
        <div class="pagination">
          <button @click="loadMoreNews" :disabled="isLoadingNews">더 보기</button>
        </div>
      </div>
    </div>

    <!-- 뉴스 모달 -->
    <div v-if="showNewsModal" class="modal-overlay" @click="closeNewsModal">
      <div class="modal-content" @click.stop>
        <button class="close-button" @click="closeNewsModal">×</button>
        <h2>{{ selectedNews.title }}</h2>
        <p class="news-date">{{ selectedNews.pubDate }}</p>
        <img v-if="selectedNews.image" :src="selectedNews.image" :alt="selectedNews.title" class="news-image">
        <div class="news-content" v-html="selectedNews.content"></div>
        <a :href="selectedNews.link" target="_blank" class="read-more">원문 보기</a>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import VueApexCharts from 'vue3-apexcharts';

export default {
  components: {
    apexchart: VueApexCharts,
  },
  props: [
    'stockCode', 'stockName', 'currentPrice', 'priceChange', 'priceChangePct', 'volume', 'htsAvls', 'w52Hgpr', 'w52Lwpr'
  ],
  data() {
    return {
      tabs: ['차트', '뉴스'],
      currentTab: '차트',
      timeRanges: ['1일', '1주', '1개월', '3개월', '1년', '3년', '5년'],
      selectedRange: '1개월',
      newsItems: [],
      newsPage: 1,
      isLoadingNews: false,
      showNewsModal: false,
      selectedNews: null,
      chartOptions: {
        chart: {
          type: 'candlestick',
        },
        xaxis: {
          type: 'datetime',
        },
        yaxis: {
          tooltip: {
            enabled: true,
          },
        },
      },
      chartSeries: [],
    };
  },
  methods: {
    changeTimeRange(range) {
  const rangeMap = {
    '1일': '1day',
    '1주': '1week',
    '1개월': '1month',
    '3개월': '3months',
    '1년': '1year',
    '3년': '3years',
    '5년': '5years'
  };
  this.selectedRange = rangeMap[range] || '1day'; // 기본값을 '1day'로 설정
  this.fetchChartData();
},
async fetchChartData() {
  if (!this.selectedRange) {
    console.error('selectedRange is undefined');
    return;
  }

  try {
    const response = await axios.get(`http://localhost:8080/api/stockcandle/${this.stockCode}/${this.selectedRange}`);
    
    if (response.data) {
      const chartData = response.data.map(item => ({
        x: new Date(item.stockCandleDay),
        y: [item.stockCandleOpen, item.stockCandleHigh, item.stockCandleLow, item.stockCandleClose]
      }));
      this.chartSeries = [{ name: '주가', data: chartData }];
    }
  } catch (error) {
    console.error('차트 데이터를 가져오는 중 오류 발생:', error);
  }
},
    async fetchNews() {
      if (this.isLoadingNews) return;
      
      this.isLoadingNews = true;
      try {
        const response = await axios.get('/api/news', {
          params: {
            query: this.stockName,
            display: 10,
            start: (this.newsPage - 1) * 10 + 1,
            sort: 'sim'
          }
        });
  
        if (response.data && response.data.items) {
          const newItems = response.data.items.map(this.processNewsItem);
          this.newsItems = [...this.newsItems, ...newItems];
          this.newsPage++;
        }
      } catch (error) {
        console.error('뉴스 데이터를 가져오는 데 실패했습니다:', error);
      } finally {
        this.isLoadingNews = false;
      }
    },
    processNewsItem(item) {
      return {
        title: item.title.replace(/&quot;/g, '"').replace(/</g, "&lt;").replace(/>/g, "&gt;"),
        description: item.description.replace(/&quot;/g, '"').replace(/</g, "&lt;").replace(/>/g, "&gt;"),
        link: item.link,
        pubDate: new Date(item.pubDate).toLocaleString(),
        content: item.content,
        image: item.image // 이미지 URL이 API 응답에 포함되어 있다고 가정
      };
    },
    loadMoreNews() {
      this.fetchNews();
    },
    openNewsModal(news) {
      this.selectedNews = news;
      this.showNewsModal = true;
    },
    closeNewsModal() {
      this.showNewsModal = false;
      this.selectedNews = null;
    }
  },
  watch: {
    currentTab(newTab) {
      if (newTab === '뉴스' && this.newsItems.length === 0) {
        this.fetchNews();
      }
    }
  },
  mounted() {
    this.fetchChartData(); // 페이지 로드 시 기본 차트 데이터 로드
    this.fetchChartData(); // 컴포넌트가 마운트될 때 차트 데이터 가져오기
  }
};
</script>

  
<style scoped>
.stock-chart {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    background-color: #1e1e1e;
    color: #ffffff;
    border-radius: 8px;
}

.stock-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    margin-bottom: 20px;
}

.stock-title h1 {
    margin: 0;
    font-size: 24px;
}

.stock-code {
    color: #888;
    margin: 5px 0;
}

.stock-price {
    text-align: right;
}

.stock-price h2 {
    margin: 0;
    font-size: 28px;
}

.tabs {
    display: flex;
    margin-bottom: 20px;
}

.tabs button {
    background: none;
    border: none;
    color: #888;
    cursor: pointer;
    padding: 10px 15px;
    font-size: 16px;
}

.tabs button.active {
    color: #ffffff;
    border-bottom: 2px solid #ffffff;
}

.chart-container,
.order-book,
.news-container,
.disclosure {
    min-height: 300px;
    background-color: #2a2a2a;
    border-radius: 8px;
    padding: 20px;
    margin-bottom: 20px;
}

.time-range {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}

.time-range button {
    background: none;
    border: none;
    color: #888;
    cursor: pointer;
    padding: 5px 10px;
    border-radius: 15px;
}

.time-range button.active {
    background-color: #3a3a3a;
    color: #ffffff;
}

.stock-details {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 10px;
}

.detail-item {
    display: flex;
    justify-content: space-between;
    padding: 5px 0;
    border-bottom: 1px solid #333;
}

.positive {
    color: #e15241;
}

.negative {
    color: #2988de;
}

.news-item {
    margin-bottom: 20px;
    padding-bottom: 20px;
    border-bottom: 1px solid #333;
    cursor: pointer;
    transition: background-color 0.3s;
}

.news-item:hover {
    background-color: #3a3a3a;
}

.pagination {
    text-align: center;
    margin-top: 20px;
}

.pagination button {
    background-color: #3a3a3a;
    color: #ffffff;
    border: none;
    padding: 10px 20px;
    cursor: pointer;
    border-radius: 5px;
}

.pagination button:disabled {
    background-color: #555;
    cursor: not-allowed;
}

.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.7);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
}

.modal-content {
    background-color: #2a2a2a;
    padding: 20px;
    border-radius: 8px;
    max-width: 600px;
    max-height: 80vh;
    overflow-y: auto;
    position: relative;
}

.close-button {
    position: absolute;
    top: 10px;
    right: 10px;
    background: none;
    border: none;
    font-size: 24px;
    color: #fff;
    cursor: pointer;
}

.news-date {
    color: #888;
    font-size: 0.9em;
    margin-bottom: 10px;
}

.news-image {
    max-width: 100%;
    height: auto;
    margin-bottom: 15px;
}

.read-more {
    display: inline-block;
    margin-top: 15px;
    color: #2988de;
    text-decoration: none;
}

.read-more:hover {
    text-decoration: underline;
}
</style>