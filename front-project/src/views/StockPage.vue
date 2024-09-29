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
                        <h3>{{ stock.name }}</h3>
                        <p>{{ stock.price }}</p>
                        <p>{{ stock.change }} ({{ stock.changeRate }}%)</p>
                    </div>
                </div>
            </section>

            <p class="middle-title">현재 상위권 TOP3 🏆</p>
            <section class="top3-stocks">
                <div class="top3-cards">
                    <div v-for="(stock, index) in top3Stocks" :key="index" class="top3-card">
                        <h3>{{ stock.name }}</h3>
                        <p>{{ stock.price }}</p>
                        <p>{{ stock.change }} ({{ stock.changeRate }}%)</p>
                        <router-link :to="'/stock/' + stock.code">자세히 보기</router-link>
                    </div>
                </div>
            </section>

            <section class="stock-list">
                <p class="title">주식 목록</p>
                <div class="search-bar">
                    <input v-model="searchKeyword" placeholder="키워드를 입력해주세요" @keyup.enter="searchStocks">
                    <button @click="searchStocks">검색</button>
                </div>
                <div v-for="(stock, index) in stocks" :key="index" class="stock-item">
                    <p>{{ stock.name }} - {{ stock.price }} - 거래량: {{ stock.tradeVolume }}</p>
                </div>
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

export default {
    data() {
        return {
            currentStocks: [],
            top3Stocks: [],
            stocks: [],
            error: null,
            searchKeyword: '',
            currentPage: 1,
            totalPages: 1,
            itemsPerPage: 20
        };
    },
    mounted() {
        this.fetchAllData();
    },
    methods: {
        async fetchAllData() {
            try {
                await Promise.all([
                    this.fetchCurrentStocks(),
                    this.fetchTop3Stocks(),
                    this.fetchStocks()
                ]);
            } catch (error) {
                this.error = '데이터를 불러오는 데 실패했습니다. 잠시 후 다시 시도해 주세요.';
                console.error('Error fetching data:', error);
            }
        },
        async fetchCurrentStocks() {
            try {
                const response = await axios.get('/api/stocks/current');
                this.currentStocks = response.data;
            } catch (error) {
                console.error('Error fetching current stocks:', error);
                throw error;
            }
        },
        async fetchTop3Stocks() {
            try {
                const response = await axios.get('/api/stocks/top3');
                this.top3Stocks = response.data;
            } catch (error) {
                console.error('Error fetching top3 stocks:', error);
                throw error;
            }
        },
        async fetchStocks() {
            try {
                const response = await axios.get('/api/stocks', {
                    params: {
                        page: this.currentPage,
                        size: this.itemsPerPage,
                        keyword: this.searchKeyword
                    }
                });
                this.stocks = response.data.content;
                this.totalPages = response.data.totalPages;
            } catch (error) {
                console.error('Error fetching stocks:', error);
                throw error;
            }
        },
        searchStocks() {
            this.currentPage = 1;
            this.fetchStocks();
        },
        changePage(direction) {
            this.currentPage += direction;
            this.fetchStocks();
        }
    }
};
</script>

<style scoped>
/* 기존 스타일 그대로 유지 */
</style>


<style scoped>
/* 기본 레이아웃 스타일 */
.stock-dashboard {
    padding: 20px;
    background-color: white
        /* 전체 배경색 */
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
    background-color: #F5F8F4;
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
    color: #448C74;
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
    background-color: #448C74;
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
    background-color: #448C74;
    color: white;
    border-radius: 5px;
    cursor: pointer;
}

.pagination span {
    font-size: 14px;
    color: #333;
}
</style>