<template>
  <div class="result-view">
    <h4 style="color: #45a049;">투자 성향 테스트 결과</h4>
    <h1>
      <p>나의 투자 성향은?</p>
    </h1>

    <!-- 투자 성향 카드 -->
    <div class="result-cards">
      <div v-if="userType === '공격투자형'" class="result-card">
        <h3>{{ investmentResults[0].title }}</h3>
        <p>{{ investmentResults[0].description }}</p>
        <!-- Lottie 애니메이션을 렌더링 -->
        <Vue3Lottie :animationData="investmentResults[0].lottiePath" />
      </div>
      <div v-if="userType === '적극투자형'" class="result-card">
        <h3>{{ investmentResults[1].title }}</h3>
        <p>{{ investmentResults[1].description }}</p>
        <Vue3Lottie :animationData="investmentResults[1].lottiePath" />
      </div>
      <div v-if="userType === '위험중립형'" class="result-card">
        <h3>{{ investmentResults[2].title }}</h3>
        <p>{{ investmentResults[2].description }}</p>
        <Vue3Lottie :animationData="investmentResults[2].lottiePath" /> 
      </div>
      <div v-if="userType === '안정추구형'" class="result-card">
        <h3>{{ investmentResults[3].title }}</h3>
        <p>{{ investmentResults[3].description }}</p>
        <Vue3Lottie :animationData="investmentResults[3].lottiePath" />
      </div>
      <div v-if="userType === '안정형'" class="result-card">
        <h3>{{ investmentResults[4].title }}</h3>
        <p>{{ investmentResults[4].description }}</p>
        <Vue3Lottie :animationData="investmentResults[4].lottiePath" />
      </div>
    </div>

    <!-- 추천 콘텐츠 -->
    <div class="recommended-content" v-if="recommendedContent.length">
      <b> <p class="type_txt">{{ userType }}</p> 타입을 가진 당신을 위해 아래 콘텐츠를 준비했어요!</b>
      <div class="content-cards">
        <div v-for="(content, index) in recommendedContent" :key="index" class="content-card">
          <p>{{ content.title }}</p>
          <h4>{{ content.description }}</h4>
          <img :src="content.icon" alt="Content Icon" />
          <router-link :to="content.link" class="content-link">➔</router-link>
        </div>
      </div>
    </div>

    <!-- 하단 버튼 -->
    <div class="action-buttons">
      <button @click="restartTest" class="restart-button">테스트 다시하기</button>
      <button @click="shareResults" class="share-button">공유</button>
    </div>
  </div>
</template>

<script>
import { Vue3Lottie } from 'vue3-lottie'; // vue3-lottie 컴포넌트 가져오기

import aggressiveInvestorJson from '@/assets/img/analysis/aggressive_investor.json';
import activeInvestor from '@/assets/img/analysis/active_investor.json';
import balancedInvestor from '@/assets/img/analysis/balanced_investor.json';
import conservativeInvestor from '@/assets/img/analysis/conservative_investor.json';
import riskAverseInvestor from '@/assets/img/analysis/risk-averse_investor.json';

export default {
  components: {
    Vue3Lottie,
  },
  data() {
    return {
      userType: "", // 사용자의 투자 성향 타입을 저장
      totalScore: 0, // 총 점수 저장
      investmentResults: [
        {
          title: "공격투자형",
          description: "높은 수익을 추구하며 높은 리스크 또한 감수합니다.",
          lottiePath: aggressiveInvestorJson
        },
        {
          title: "적극투자형",
          description: "안정성을 고려하면서도 공격적인 투자 전략을 선호합니다.",
          lottiePath: activeInvestor
        },
        {
          title: "위험중립형",
          description: "안정성과 위험을 균형 있게 고려합니다.",
          lottiePath: balancedInvestor
        },
        {
          title: "안정추구형",
          description: "안정적인 수입과 자산 보존을 중요시합니다.",
          lottiePath: conservativeInvestor
        },
        {
          title: "안정형",
          description: "리스크를 피하며 안정적인 수익을 선호합니다.",
          lottiePath: riskAverseInvestor
        }
      ],
      recommendedContent: []
    };
  },
  created() {
    // 라우터 쿼리에서 totalScore 값 가져오기
    this.totalScore = parseInt(this.$route.query.totalScore, 10);
    this.classifyUserType();
    this.setRecommendedContent();
  },
  methods: {
    classifyUserType() {
      // 성향 분류 로직 => totalScore에 따라 성향을 결정
      if (this.totalScore > 15) {
        this.userType = "공격투자형";
      } else if (this.totalScore > 11) {
        this.userType = "적극투자형";
      } else if (this.totalScore > 8) {
        this.userType = "위험중립형";
      } else if (this.totalScore > 4) {
        this.userType = "안정추구형";
      } else {
        this.userType = "안정형";
      }
    },
    setRecommendedContent() {
      // 사용자의 성향에 맞는 추천 콘텐츠를 설정
      if (this.userType === "공격형") {
        this.recommendedContent = [
          {
            title: "고위험 고수익 상품",
            description: "적극적인 투자자들을 위한 고위험 고수익 상품을 확인해보세요!",
            icon: require('@/assets/img/analysis/1.png'),
            link: "/highrisk"
          },
          {
            title: "미래 성장 산업에 투자하기",
            description: "성장 가능성이 높은 산업에 집중 투자하는 방법을 알아보세요.",
            icon: require('@/assets/img/analysis/2.png'),
            link: "/growth"
          }
        ];
      } else if (this.userType === "위험중립형") {
        this.recommendedContent = [
          {
            title: "ISA 상품 활용",
            description: "ISA를 통한 세금 혜택을 받고, 안정적인 투자를 유지하세요.",
            icon: require('@/assets/img/analysis/3.png'),
            link: "/isa"
          },
          {
            title: "분산 투자 전략",
            description: "다양한 자산에 분산 투자하여 리스크를 최소화하세요.",
            icon: require('@/assets/img/analysis/4.png'),
            link: "/diversification"
          }
        ];
      } else if (this.userType === "안정형") {
        this.recommendedContent = [
          {
            title: "안전한 예금 상품",
            description: "안정적인 예금 상품을 통해 꾸준한 수익을 창출하세요.",
            icon: require('@/assets/img/analysis/5.png'),
            link: "/savings"
          },
          {
            title: "국채 투자",
            description: "안전한 국채를 통해 리스크 없이 수익을 올릴 수 있는 방법을 알아보세요.",
            icon: require('@/assets/img/analysis/6.png'),
            link: "/bonds"
          }
        ];
      }
    },
    restartTest() {
      this.$router.push('/test-start');
    },
    shareResults() {
      alert('결과를 공유합니다.');
    }
  }
};
</script>



<style scoped>
.result-view {
  text-align: center;
  background-color: #f9f9f9;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
}

h1 {
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 20px;
}

h2 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 40px;
}

b {
  font-size: 30px;
}

.result-cards {
  display: flex;
  justify-content: center;
  gap: 20px;
}

.result-card {
  background-color: #ECCB50;
  padding: 20px;
  border-radius: 15px;
  color: white;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  width: 300px;
  text-align: center;
}

.result-icon {
  height: 80px;
  margin-top: 20px;
}

.recommended-content {
  margin-top: 80px;
}

.content-cards {
  display: flex;
  color: white;
  margin-top: 35px;
  justify-content: center;
  gap: 20px;
}

.content-card {
  background-color: #EDA150;
  padding: 20px;
  border-radius: 15px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  width: 250px;
  text-align: center;
}

.content-link {
  display: block;
  font-size: 24px;
  color: #438C74;
  margin-top: 10px;
}

.action-buttons {
  margin-top: 40px;
  display: flex;
  gap: 20px;
}

.restart-button,
.share-button {
  background-color: #438C74;
  color: white;
  border: none;
  padding: 15px 30px;
  border-radius: 10px;
  cursor: pointer;
  font-size: 16px;
}

.restart-button:hover,
.share-button:hover {
  background-color: #45a049;
}

.type_txt {
  color: #438C74;
}
</style>
