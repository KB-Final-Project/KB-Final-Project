<template>
    <div class="news-view">
        <h1><b>뉴스 보기</b></h1>

        <!-- 메인 뉴스 -->
        <div v-if="mainNews" class="main-news">
            <div class="main-news-image-container">
                <img :src="require('@/assets/img/news/news-main.png')" alt="Main News Image" class="main-news-image"
                    @click="goToNews(mainNews.link)" @error="onImageError" />
                <div class="main-news-title-overlay">
                    <b class="main-news-title" v-html="mainNews.title"></b>
                </div>
            </div>
        </div>

        <br><br><br><br>

        <h1><b>실시간 주요뉴스</b></h1>
        <!-- 카테고리 선택 -->
        <div class="category-selector">
            <button v-for="category in categories" :key="category" @click="selectCategory(category)"
                :class="{ active: selectedCategory === category }">
                {{ category }}
            </button>
        </div>

        <!-- 뉴스 그리드 -->
        <div v-if="selectedCategoryNews.length" class="news-grid">
            <div v-for="news in selectedCategoryNews" :key="news.link" class="news-card" @click="goToNews(news.link)">
                <img :src="news.image" alt="News Image" class="news-image" @error="onImageError" />
                <h3 class="news-title" v-html="news.title"></h3>
                <p class="news-date">{{ news.date }}</p>
            </div>
        </div>
    </div>
</template>


<script>
import axios from 'axios';

export default {
    data() {
        return {
            categories: ['금융', '예금', '적금', '주식', '펀드', 'ISA', '금', 'ELS', 'ETF'],
            selectedCategory: '금융',
            mainNews: null,
            categoryNews: {},
            fallbackImage: 'https://via.placeholder.com/150'
        };
    },

    computed: {
        selectedCategoryNews() {
            return this.categoryNews[this.selectedCategory] || [];
        }
    },

    methods: {
        // 카테고리 별로 세부 검색어를 설정
        getSearchQuery(category) {
            const queries = {
                금융: '금융 정책 변화, 금융 시장 동향, 금융 리스크 관리, 한국 금융시장 전망, 미국증시',
                예금: '예금 금리 인상, 고금리 예금 상품, 예금 보험, 예금 세금 혜택',
                적금: '장기 적금 상품, 적금 금리 비교, 적금 세금 혜택, 적금 활용 전략',
                주식: '2023 주식 시장 전망, 주식 투자 팁, 고배당 주식, 인기 성장주',
                펀드: '펀드 수익률 비교, 주식형 펀드 추천, 채권형 펀드 동향, 펀드 투자 전략',
                ISA: 'ISA 세제 혜택, ISA 계좌 추천, ISA 투자 전략, ISA 상품 비교',
                금: '금 투자, 금 시장 전망',
                ELS: 'ELS 시장 동향',
                ETF: 'ETF 투자 전략, ETF 시장 트렌드, 주식형 ETF 분석'
            };
            return queries[category] || category; // 카테고리 없으면 그대로 사용
        },

        async fetchNews(category, count = 9) {
            try {
                const query = this.getSearchQuery(category);
                const response = await axios.get('/api/news', {
                    params: { query: query, display: count }
                });

                const newsItems = response.data.items.map(this.processNewsItem);

                // 관련도에 따라 뉴스 정렬
                return this.sortByRelevance(newsItems);
            } catch (error) {
                console.error(`${category} 뉴스 데이터를 가져오는 데 실패했습니다:`, error);
                return [];
            }
        },

        processNewsItem(newsData) {
            return {
                title: this.stripHtml(newsData.title),
                description: this.stripHtml(newsData.description),
                date: new Date(newsData.pubDate).toLocaleDateString(),
                image: this.extractImageUrl(newsData.description) || this.fallbackImage,
                link: newsData.link
            };
        },

        // HTML 태그 제거
        stripHtml(text) {
            return text.replace(/<[^>]+>/g, '');
        },

        // 이미지 URL 추출
        extractImageUrl(description) {
            const imgRegex = /<img[^>]+src="?([^"\s]+)"?\s*\/?>/g;
            const match = imgRegex.exec(description);
            return match ? match[1] : this.fallbackImage;
        },

        // 관련도 기준으로 뉴스 정렬
        sortByRelevance(newsItems) {
            const relevantKeywords = ['금융', '은행', '금리', '증권', '자산', '채권'];

            return newsItems.sort((a, b) => {
                const aScore = this.calculateRelevanceScore(a, relevantKeywords);
                const bScore = this.calculateRelevanceScore(b, relevantKeywords);
                return bScore - aScore; // 높은 점수 순으로 정렬
            });
        },

        calculateRelevanceScore(newsItem, keywords) {
            let score = 0;
            keywords.forEach(keyword => {
                if (newsItem.title.includes(keyword)) score += 2; // 제목에 포함될 경우 높은 점수
                if (newsItem.description.includes(keyword)) score += 1; // 설명에 포함될 경우 낮은 점수
            });
            return score;
        },

        async selectCategory(category) {
            this.selectedCategory = category;
            if (!this.categoryNews[category]) {
                this.categoryNews[category] = await this.fetchNews(category);
            }
        },

        goToNews(link) {
            window.open(link, '_blank');
        },

        onImageError(event) {
            event.target.src = this.fallbackImage;
        }
    },

    async mounted() {
        // 메인 뉴스 가져오기
        const mainNewsData = await this.fetchNews('투자', 1);
        this.mainNews = mainNewsData[0];

        // 초기 카테고리 뉴스 가져오기 (기본 카테고리)
        await this.selectCategory(this.selectedCategory);
    }
};
</script>

<style scoped>
.news-view {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
    background-color: #F9FAFC;
}

h1 {
    text-align: center;
    margin-bottom: 20px;
}

.main-news {
    display: flex;
    margin-bottom: 30px;
    background-color: white;
    border-radius: 30px;
    overflow: hidden;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.main-news-image-container {
    position: relative;
    width: 100%;
    height: 450px;
    cursor: pointer;
}

.main-news-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.main-news-title-overlay {
    position: absolute;
    top: 80%;
    /* 수직 중앙에 위치 */
    left: 50%;
    /* 수평 중앙에 위치 */
    transform: translate(-50%, -50%);
    /* 정확한 중앙으로 이동 */
    color: white;
    font-size: 24px;
    text-align: center;
    /* 텍스트 가운데 정렬 */
    padding: 10px;
    border-radius: 5px;
}


.main-news-title {
    font-size: 24px;
    margin: 0;
}

.main-news-content {
    width: 50%;
    padding: 20px;
}

.main-news-description {
    font-size: 16px;
    margin-bottom: 10px;
}

.main-news-date {
    font-size: 14px;
    color: #666;
}

.category-selector {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    margin-bottom: 20px;
}

.category-selector button {
    margin: 5px;
    padding: 20px 35px;
    border: none;
    background-color: #90DAAA;
    cursor: pointer;
    border-radius: 20%;
    font-size: 15px;
    font-weight: bold;
    transition: background-color 0.3s;
}


.category-selector button.active {
    background-color: #438B73;
    color: white;
}

.news-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
}

.news-card {
    background-color: white;
    border-radius: 30px;
    overflow: hidden;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease;
    cursor: pointer;
}

.news-card:hover {
    transform: translateY(-5px);
}

.news-image {
    width: 100%;
    height: 200px;
    object-fit: cover;
}

.news-title {
    padding: 10px;
    font-size: 16px;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
}

.news-date {
    padding: 0 10px 10px;
    font-size: 12px;
    color: #666;
}

@media (max-width: 900px) {
    .main-news {
        flex-direction: column;
    }

    .main-news-image,
    .main-news-content {
        width: 100%;
    }

    .news-grid {
        grid-template-columns: repeat(2, 1fr);
    }
}

@media (max-width: 600px) {
    .news-grid {
        grid-template-columns: 1fr;
    }
}
</style>