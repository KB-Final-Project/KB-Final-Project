<template>
  <section>
    <br><br>
    <div class="todayBox text-center">
      <br><br>
      <h2 class="review-section__title mb-4">TODAY</h2>
      <div class="d-flex mt-4 justify-content-center text-start gap-4">
        <div class="todayStock">
          <h3>현재 증시</h3>
          <div class="todayCo">
            코스피 코스닥
          </div>
        </div>
        <div class="todayExchange">
          <h3 class="d-inline-block">환율 정보</h3>
          <a class="d-inline-block mx-xl-20" href="#">더보기<i class="ai-chevron-right"></i></a>
          <div class="exchangeBox">
            <div class="content">
              <div class="line-c">
                <Line :data="chartData" :options="chartOptions" v-if="chartData" />
              </div>
              <div class="new-section" v-if="exchangeData">
                <h4 class="d-inline-block mx-xl-20">{{ exchangeData.currencyName }}({{ exchangeData.currencyCode }})</h4>
                <h4 class="d-inline-block">{{ exchangeData.basePrice.toLocaleString() }}원 ({{ exchangeData.baseRateDifference.toLocaleString() }})</h4>
                {{ exchangeData.dailyChangeRate.toFixed(2) }}%
                환율 날짜: {{ exchangeData.exchangeRateDate }}
              </div>
            </div>
            <div class="currency-buttons">
              <button @click="fetchExchangeRates(23)" class="exchangeCountry1">미국</button>
              <button @click="fetchExchangeRates(13)" class="exchangeCountry2">일본</button>
              <button @click="fetchExchangeRates(9)" class="exchangeCountry1">EU</button>
              <button @click="fetchExchangeRates(2)" class="exchangeCountry2">호주</button>
              <button @click="fetchExchangeRates(10)" class="exchangeCountry1">영국</button>
            </div>
          </div>
        </div>
      </div>
      <br><br>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { Line } from 'vue-chartjs';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';

// Chart.js 구성 요소 등록
ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

const loading = ref(false);
const exchangeData = ref(null); 

const nationalMoney = ref([
  { currencyId: 2, code: 'AUD', name: '호주 달러', countryCode: 'AU', flagUrl: 'https://flagcdn.com/24x18/au.png' },
  { currencyId: 9, code: 'EUR', name: 'EU 유로', countryCode: 'EU', flagUrl: 'https://flagcdn.com/24x18/eu.png' },
  { currencyId: 10, code: 'GBP', name: '영국 파운드', countryCode: 'GB', flagUrl: 'https://flagcdn.com/24x18/gb.png' },
  { currencyId: 13, code: 'JPY', name: '일본 옌', countryCode: 'JP', flagUrl: 'https://flagcdn.com/24x18/jp.png' },
  { currencyId: 23, code: 'USD', name: '미국 달러', countryCode: 'US', flagUrl: 'https://flagcdn.com/24x18/us.png' }
]);

const chartData = ref(null);
const chartOptions = ref({
  responsive: true,
  maintainAspectRatio: false,
  scales: {
    x: {
      display: true,
      grid: {
        display: false,
      },
      ticks: {
        callback: function (value, index, values) {
          if (index === values.length - 1) {
            return values[index].label;
          }
          return null;
        },
      },
    },
    y: {
      display: true,
      grid: {
        display: true,
        drawBorder: true,
        drawOnChartArea: false,
      },
      ticks: {
        callback: function (value) {
          return value.toLocaleString();
        },
        maxTicksLimit: 3,
      },
    },
  },
  plugins: {
    legend: {
      display: false,
    },
    tooltip: {
      enabled: true,
      callbacks: {
        title: function (tooltipItems) {
          return tooltipItems[0].label;
        },
        label: function (tooltipItem) {
          return `가격: ${tooltipItem.raw.toLocaleString()}`;
        },
      },
    },
  },
  elements: {
    point: {
      radius: 2,
    },
    line: {
      tension: 0.1,
    },
  },
});

const fetchExchangeRates = async (currencyId) => {
  loading.value = true;
  fetchDailyExchangeData(currencyId);
  try {
    const response = await axios.get(`/api/exchange/detail/${currencyId}`);
    const exchangeList = response.data[0]?.exchangeList || [];
    
    chartData.value = {
      labels: exchangeList.map(item => new Date(item.exchangeRateDate).toLocaleDateString()),
      datasets: [
        {
          label: 'Base Price',
          data: exchangeList.map(item => item.basePrice),
          borderColor: 'rgba(75, 192, 192, 1)',
          backgroundColor: 'rgba(75, 192, 192, 0.2)',
        },
      ],
    };
  } catch (error) {
    console.error('환율 데이터를 가져오는 중 오류 발생:', error);
  } finally {
    loading.value = false;
  }
};

const fetchDailyExchangeData = async (currencyId) => {
  loading.value = true;
  try {
    const response = await axios.get('/api/exchange/daily', {
      params: {
        currencyId: currencyId,
      },
    });
    const data = response.data;
    exchangeData.value = {
      basePrice: data.exchange.basePrice,
      exchangeRateDate: new Date(data.exchange.exchangeRateDate).toLocaleDateString(),
      currencyCode: data.currency.currencyCode,
      currencyName: data.currency.currencyName,
      dailyChangeRate: data.dailyChangeRate,
      baseRateDifference: data.baseRateDifference,
    };
    console.log('저장된 환율 데이터:', exchangeData.value);
  } catch (error) {
    console.error('환율 데이터를 가져오는 중 오류 발생:', error);
  } finally {
    loading.value = false;
  }
};  
onMounted(async () => {
  fetchExchangeRates(23);
});
</script>

<style scoped>
.todayExchange a {
  text-decoration: none;
  color: black;
}
.review-section__title {
  font-size: 40px;
  font-family: J6;
}

.currency-buttons {
  display: flex;
  flex-direction: row;
  justify-content: center;
  gap: 10px;
  margin-top: 10px; 
}

.todayBox {
  width: 100%;
  background-color: rgba(249, 250, 251, 1);
  padding: 20px 0;
}

.todayBox h3 {
  font-size: 30px;
}

.todayCo {
  width: 700px;
  height: 400px;
  border: 1px solid lightgrey;
  border-radius: 30px;
  padding: 30px;
  background-color: white;
}

.exchangeBox {
  width: 600px;
  height: 400px; 
  border: 1px solid lightgrey;
  border-radius: 30px;
  padding: 20px;
  background-color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.content {
  display: flex;
  gap: 15px;
  width: 100%;
}

.line-c {
  width: 380px; /* 원하는 너비 */
  height: 250px; /* 원하는 높이 */
  margin: 0 auto; /* 가운데 정렬 */
}
.new-section {
  flex: 1; 
  min-height: 250px;
  border-radius: 15px;
  background-color: white;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 250px;
}

.exchangeCountry1, .exchangeCountry2 {
  width: 80px;
  height: 40px;
  border-radius: 20px;
  border: none;
  color: white;
  font-size: 18px;
}

.exchangeCountry1 {
  background-color: rgba(67, 139, 115, 1);
}

.exchangeCountry2 {
  background-color: rgba(144, 218, 170, 1);
}

h3 {
  font-family: J4;
  margin: 20px;
}

.exchangeTitle {
  width: 100%;
  text-align: center;
}

button {
  font-family: J3;
}
</style>
