<template>
    <div class="news-view text-center animate-on-load">
        <h1>뉴스 보기</h1>
        <div class="container">
            
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
                <div v-for="news in selectedCategoryNews" :key="news.link" class="news-card"
                    @click="goToNews(news.link)">
                    <img :src="require('@/assets/img/news/news2.jpg')" alt="News Image" class="news-image" @error="onImageError" />
                    <h3 class="news-title" v-html="news.title"></h3>
                    <p class="news-date">{{ news.date }}</p>
                </div>
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
        getSearchQuery(category) {
            const queries = {
                금융: '금융시장, 한국금융시장전망, 미국증시',
                예금: '예금금리인상, 예금',
                적금: '장기적금상품, 적금금리비교, 적금세금혜택, 적금',
                주식: '주식시장전망, 고배당주식, 인기성장주',
                펀드: '펀드수익률, 주식형펀드 , 펀드동향, 펀드투자',
                ISA: 'ISA세제, ISA계좌, ISA투자전략, ISA상품비교',
                금: '금투자, 금시장',
                ELS: 'ELS시장동향',
                ETF: 'ETF투자전략, ETF시장트렌드, 주식형ETF분석'
            };
            return queries[category] || category;
        },

        async fetchNews(category, count = 9, sort = 'sim') {
            try {
                const query = this.getSearchQuery(category);
                const response = await axios.get('/api/news', {
                    params: { query: query, display: count, sort: sort }
                });

                if (response.data && response.data.items) {
                    const newsItems = response.data.items.map(this.processNewsItem);
                    return sort === 'sim' ? newsItems : this.sortByRelevance(newsItems);
                } else {
                    console.error('Unexpected API response:', response.data);
                    return [];
                }
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

        stripHtml(text) {
            return text.replace(/<[^>]+>/g, '');
        },

        extractImageUrl(description) {
            const imgRegex = /<img[^>]+src="?([^"\s]+)"?\s*\/?>/g;
            const match = imgRegex.exec(description);
            return match ? match[1] : this.fallbackImage;
        },

        sortByRelevance(newsItems) {
            const relevantKeywords = ['금융', '은행', '금리', '증권', '자산', '채권'];

            return newsItems.sort((a, b) => {
                const aScore = this.calculateRelevanceScore(a, relevantKeywords);
                const bScore = this.calculateRelevanceScore(b, relevantKeywords);
                return bScore - aScore;
            });
        },

        calculateRelevanceScore(newsItem, keywords) {
            let score = 0;
            keywords.forEach(keyword => {
                if (newsItem.title.includes(keyword)) score += 2;
                if (newsItem.description.includes(keyword)) score += 1;
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
        const mainNewsData = await this.fetchNews('금융시장', 1, 'sim');
        this.mainNews = mainNewsData[0];
        
        // 카테고리 뉴스는 기존 방식(관련도순)으로 가져옵니다.
        await this.selectCategory(this.selectedCategory);
    }
};
</script>

<style scoped>
.news-view {
    margin: 0 auto;
    background-color: #F9FAFC;
    font-family: J5;
}

.container {
    max-width: 1200px;
}

h1 {
    text-align: center;
    margin-bottom: 20px;
}

.main-news {
    font-family: J5;
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
    font-size: 40px;
    text-align: center;
    /* 텍스트 가운데 정렬 */
    padding: 10px;
    border-radius: 5px;
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
    font-family: J5;
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    margin-bottom: 20px;
}

.category-selector button {
    margin: 5px;
    padding: 15px 35px;
    border: none;
    background-color: #90DAAA;
    color: white;
    cursor: pointer;
    border-radius: 20px;
    font-size: 15px;
    transition: background-color 0.3s;
}

.category-selector :hover {
    background-color: #438B73;
    color: white;
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
    font-family: J3;
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
  
</style>