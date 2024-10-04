<template>
  <div class="result-view">
    <br><br>
    <h4 class="animated-text" style="color: #45a049;">투자 성향 테스트 결과</h4>
    <h1 class="animated-text">
      <p>나의 투자 성향은?</p>
    </h1>

    <!-- 투자 성향 카드 -->
    <div class="result-cards">
      <div v-if="userType === 'IPWC'" class="result-card animated-text">
        <h3>{{ investmentResults[0].title }}</h3>
        <p>{{ investmentResults[0].description }}</p>
        <Vue3Lottie :animationData="investmentResults[0].lottiePath" />
      </div>
      <div v-if="userType === 'IPMC'" class="result-card animated-text">
        <h3>{{ investmentResults[1].title }}</h3>
        <p>{{ investmentResults[1].description }}</p>
        <Vue3Lottie :animationData="investmentResults[1].lottiePath" />
      </div>
      <div v-if="userType === 'IBWC'" class="result-card animated-text">
        <h3>{{ investmentResults[2].title }}</h3>
        <p>{{ investmentResults[2].description }}</p>
        <Vue3Lottie :animationData="investmentResults[2].lottiePath" />
      </div>
      <div v-if="userType === 'IBMC'" class="result-card animated-text">
        <h3>{{ investmentResults[3].title }}</h3>
        <p>{{ investmentResults[3].description }}</p>
        <Vue3Lottie :animationData="investmentResults[3].lottiePath" />
      </div>
      <div v-if="userType === 'IPML'" class="result-card animated-text">
        <h3>{{ investmentResults[4].title }}</h3>
        <p>{{ investmentResults[4].description }}</p>
        <Vue3Lottie :animationData="investmentResults[4].lottiePath" />
      </div>
      <div v-if="userType === 'IPWL'" class="result-card animated-text">
        <h3>{{ investmentResults[5].title }}</h3>
        <p>{{ investmentResults[5].description }}</p>
        <Vue3Lottie :animationData="investmentResults[5].lottiePath" />
      </div>
      <div v-if="userType === 'IBML'" class="result-card animated-text">
        <h3>{{ investmentResults[6].title }}</h3>
        <p>{{ investmentResults[6].description }}</p>
        <Vue3Lottie :animationData="investmentResults[6].lottiePath" />
      </div>
      <div v-if="userType === 'IBWL'" class="result-card animated-text">
        <h3>{{ investmentResults[7].title }}</h3>
        <p>{{ investmentResults[7].description }}</p>
        <Vue3Lottie :animationData="investmentResults[7].lottiePath" />
      </div>
      <div v-if="userType === 'APWL'" class="result-card animated-text">
        <h3>{{ investmentResults[8].title }}</h3>
        <p>{{ investmentResults[8].description }}</p>
        <Vue3Lottie :animationData="investmentResults[8].lottiePath" />
      </div>
      <div v-if="userType === 'APML'" class="result-card animated-text">
        <h3>{{ investmentResults[9].title }}</h3>
        <p>{{ investmentResults[9].description }}</p>
        <Vue3Lottie :animationData="investmentResults[9].lottiePath" />
      </div>
      <div v-if="userType === 'ABWC'" class="result-card animated-text">
        <h3>{{ investmentResults[10].title }}</h3>
        <p>{{ investmentResults[10].description }}</p>
        <Vue3Lottie :animationData="investmentResults[10].lottiePath" />
      </div>
      <div v-if="userType === 'APMC'" class="result-card animated-text">
        <h3>{{ investmentResults[11].title }}</h3>
        <p>{{ investmentResults[11].description }}</p>
        <Vue3Lottie :animationData="investmentResults[11].lottiePath" />
      </div>
      <div v-if="userType === 'ABWL'" class="result-card animated-text">
        <h3>{{ investmentResults[12].title }}</h3>
        <p>{{ investmentResults[12].description }}</p>
        <Vue3Lottie :animationData="investmentResults[12].lottiePath" />
      </div>
      <div v-if="userType === 'APWC'" class="result-card animated-text">
        <h3>{{ investmentResults[13].title }}</h3>
        <p>{{ investmentResults[13].description }}</p>
        <Vue3Lottie :animationData="investmentResults[13].lottiePath" />
      </div>
      <div v-if="userType === 'ABMC'" class="result-card animated-text">
        <h3>{{ investmentResults[14].title }}</h3>
        <p>{{ investmentResults[14].description }}</p>
        <Vue3Lottie :animationData="investmentResults[14].lottiePath" />
      </div>
      <div v-if="userType === 'ABML'" class="result-card animated-text">
        <h3>{{ investmentResults[15].title }}</h3>
        <p>{{ investmentResults[15].description }}</p>
        <Vue3Lottie :animationData="investmentResults[15].lottiePath" />
      </div>
    </div>

    <!-- 추천 콘텐츠 -->
    <div class="recommended-content" v-if="recommendedContent.length">
      <b class="animated-text">
        <p class="type_txt">{{ userType }}</p> 타입을 가진 당신을 위해 아래 콘텐츠를 준비했어요!
      </b>
      <div class="content-cards">
        <div v-for="(content, index) in recommendedContent" :key="index" class="content-card animated-text">
          <p>{{ content.title }}</p>
          <h4>{{ content.description }}</h4>
          <img :src="content.icon" alt="Content Icon" />
          <router-link :to="content.link" class="content-link">➔</router-link>
        </div>
      </div>
    </div>


    <!-- 하단 버튼 -->
    <div class="action-buttons">
      <button @click="restartTest" class="restart-button animated-text">테스트 다시하기</button>
      <button @click="shareResults" class="share-button animated-text">공유</button>
    </div>
  </div>
</template>

<script>
import { Vue3Lottie } from 'vue3-lottie'; // vue3-lottie 컴포넌트 가져오기

import futureOrientedCollaborator from '@/assets/img/analyResult/1.json';
import forwardThinker from '@/assets/img/analyResult/2.json';
import knowledgeableMaster from '@/assets/img/analyResult/3.json';
import braveExplorer from '@/assets/img/analyResult/4.json';
import trendsetter from '@/assets/img/analyResult/5.json';
import insightfulArtist from '@/assets/img/analyResult/6.json';
import naturalLeader from '@/assets/img/analyResult/7.json';
import diversifiedExpert from '@/assets/img/analyResult/8.json';
import confidentInvestor from '@/assets/img/analyResult/9.json';
import digitalInnovator from '@/assets/img/analyResult/10.json';
import exploratorySeeker from '@/assets/img/analyResult/11.json';
import strategicResearcher from '@/assets/img/analyResult/12.json';
import strategicPlanner from '@/assets/img/analyResult/13.json';
import buddingInvestor from '@/assets/img/analyResult/14.json';
import experiencedIcon from '@/assets/img/analyResult/15.json';
import cautiousObserver from '@/assets/img/analyResult/16.json';

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
          title: "IPWC",
          description: "안정성을 최우선으로 고려하며, 주로 단기적이고 소규모로 신중하게 투자합니다.",
          lottiePath: cautiousObserver
        },
        {
          title: "IPMC",
          description: "리스크를 철저히 관리하며 안정적인 자산 운용을 추구합니다.",
          lottiePath: experiencedIcon
        },
        {
          title: "IBWC",
          description: "안정적인 상품에 주로 투자하면서도, 미래 성장 가능성을 고려한 투자를 선호합니다.",
          lottiePath: buddingInvestor
        },
        {
          title: "IBMC",
          description: "안정성을 중시하면서도, 적절한 위험을 감수하여 장기적인 성장을 목표로 합니다.",
          lottiePath: strategicPlanner
        },
        {
          title: "IPWL",
          description: "투자 전략을 철저히 분석하며, 안정적이면서도 성장 가능성이 있는 상품에 투자합니다.",
          lottiePath: strategicResearcher
        },
        {
          title: "IPWL",
          description: "금융 지식이 풍부하며, 다각화된 자산에 신중하게 투자합니다.",
          lottiePath: exploratorySeeker
        },
        {
          title: "IBML",
          description: "디지털 자산과 새로운 금융 상품에 관심이 많으며, 혁신적인 투자에 도전합니다.",
          lottiePath: digitalInnovator
        },
        {
          title: "IBWL",
          description: "위험을 감수하더라도 성장 가능성이 높은 자산에 투자합니다. 고위험 상품에도 과감히 투자합니다.",
          lottiePath: confidentInvestor
        },
        {
          title: "APWL",
          description: "자산을 다양한 곳에 분산하여 리스크를 줄이면서도 높은 수익을 노립니다.",
          lottiePath: diversifiedExpert
        },
        {
          title: "APML",
          description: "리더십과 판단력이 뛰어나며, 공격적인 투자 방식으로 높은 수익을 추구합니다.",
          lottiePath: naturalLeader
        },
        {
          title: "ABWC",
          description: "직관적이고 창의적인 방법으로 시장의 변화를 빠르게 감지하여 투자합니다.",
          lottiePath: insightfulArtist
        },
        {
          title: "APMC",
          description: "혁신적이며 리스크를 감수하더라도 트렌드를 선도하는 자산에 투자합니다.",
          lottiePath: trendsetter
        },
        {
          title: "ABWL",
          description: "도전적이며 불확실한 시장에서도 과감한 결정을 내리고, 고위험 자산에 투자합니다.",
          lottiePath: braveExplorer
        },
        {
          title: "APWC",
          description: "풍부한 금융 지식을 바탕으로 복잡한 금융 상품에도 적극적으로 투자하며, 큰 수익을 기대합니다.",
          lottiePath: knowledgeableMaster
        },
        {
          title: "ABMC",
          description: "남다른 통찰력과 미래 지향적인 투자 성향으로, 시장을 선도하며 고위험 자산에도 투자합니다.",
          lottiePath: forwardThinker
        },
        {
          title: "ABML",
          description: "현실적인 계획을 바탕으로 극단적인 위험을 감수하며, 장기적으로도 큰 수익을 목표로 합니다.",
          lottiePath: futureOrientedCollaborator
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
      if (this.totalScore >= 44) {
        this.userType = "ABML";
      } else if (this.totalScore >= 43) {
        this.userType = "ABMC";
      } else if (this.totalScore >= 42) {
        this.userType = "APWC";
      } else if (this.totalScore >= 41) {
        this.userType = "ABWL";
      } else if (this.totalScore >= 40) {
        this.userType = "APMC";
      } else if (this.totalScore >= 39) {
        this.userType = "ABWC";
      } else if (this.totalScore >= 38) {
        this.userType = "APML";
      } else if (this.totalScore >= 37) {
        this.userType = "APWL";
      } else if (this.totalScore >= 35) {
        this.userType = "IBWL";
      } else if (this.totalScore >= 34) {
        this.userType = "IBML";
      } else if (this.totalScore >= 33) {
        this.userType = "IPWL";
      } else if (this.totalScore >= 32) {
        this.userType = "IPML";
      } else if (this.totalScore >= 30) {
        this.userType = "IBMC";
      } else if (this.totalScore >= 28) {
        this.userType = "IBWC";
      } else if (this.totalScore >= 26) {
        this.userType = "IPMC";
      } else {
        this.userType = "IPWC";
      }
    },
    setRecommendedContent() {
      // 사용자의 성향에 맞는 추천 콘텐츠를 설정
      if (this.userType === "IBWL") { // 매우 보수적인 투자 성향
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
      } else if (this.userType === "IPWL") { // 보수적 투자 성향
        this.recommendedContent = [
          {
            title: "안정적 자산 운용 상품",
            description: "리스크를 줄이고 안정적인 성장을 위한 투자 상품을 확인하세요.",
            icon: require('@/assets/img/analysis/7.png'),
            link: "/stable-assets"
          },
          {
            title: "절세 투자 전략",
            description: "세금 혜택을 받을 수 있는 안정적인 투자 전략을 알아보세요.",
            icon: require('@/assets/img/analysis/8.png'),
            link: "/tax-savings"
          }
        ];
      } else if (this.userType === "IBWC") { // 보수적인 성장형 투자 성향
        this.recommendedContent = [
          {
            title: "저위험 성장형 상품",
            description: "안정성과 미래 성장을 동시에 잡을 수 있는 상품을 알아보세요.",
            icon: require('@/assets/img/analysis/9.png'),
            link: "/lowrisk-growth"
          },
          {
            title: "기업 성장주 투자",
            description: "미래 성장 가능성이 높은 기업에 투자하여 안정적으로 자산을 불리세요.",
            icon: require('@/assets/img/analysis/10.png'),
            link: "/growth-stocks"
          }
        ];
      } else if (this.userType === "IPMC") { // 안정적인 성장형 투자 성향
        this.recommendedContent = [
          {
            title: "장기 성장 투자 전략",
            description: "장기적으로 성장 가능성이 높은 자산에 투자하여 안정적인 성장을 추구하세요.",
            icon: require('@/assets/img/analysis/11.png'),
            link: "/long-term-growth"
          },
          {
            title: "리스크 관리 방법",
            description: "적절한 리스크 관리로 장기적인 수익을 얻는 방법을 배워보세요.",
            icon: require('@/assets/img/analysis/12.png'),
            link: "/risk-management"
          }
        ];
      } else if (this.userType === "IPML") { // 전략적인 투자 연구자
        this.recommendedContent = [
          {
            title: "투자 분석 도구 활용",
            description: "투자 전략을 철저히 분석하고 최적의 투자 방법을 찾아보세요.",
            icon: require('@/assets/img/analysis/13.png'),
            link: "/investment-tools"
          },
          {
            title: "리스크와 수익의 균형",
            description: "리스크와 수익을 균형 있게 고려하는 투자 전략을 알아보세요.",
            icon: require('@/assets/img/analysis/14.png'),
            link: "/risk-reward-balance"
          }
        ];
      } else if (this.userType === "IBMC") { // 중립적인 투자 성향
        this.recommendedContent = [
          {
            title: "분산 투자 전략",
            description: "다양한 자산에 분산 투자하여 리스크를 최소화하세요.",
            icon: require('@/assets/img/analysis/4.png'),
            link: "/diversification"
          },
          {
            title: "ISA 상품 활용",
            description: "ISA를 통한 세금 혜택을 받고, 안정적인 투자를 유지하세요.",
            icon: require('@/assets/img/analysis/3.png'),
            link: "/isa"
          }
        ];
      } else if (this.userType === "IPWC") { // 디지털 혁신 투자 지휘관
        this.recommendedContent = [
          {
            title: "디지털 자산 투자",
            description: "디지털 자산과 새로운 금융 상품에 투자하여 혁신적인 기회를 잡아보세요.",
            icon: require('@/assets/img/analysis/15.png'),
            link: "/digital-assets"
          },
          {
            title: "NFT와 블록체인 투자",
            description: "블록체인 기술을 활용한 새로운 금융 상품을 알아보세요.",
            icon: require('@/assets/img/analysis/16.png'),
            link: "/blockchain-nft"
          }
        ];
      } else if (this.userType === "APWC") { // 적극적인 투자 성향
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
      } else if (this.userType === "APWL") { // 분산투자 능력자
        this.recommendedContent = [
          {
            title: "글로벌 분산 투자",
            description: "자산을 다양한 국가와 산업에 분산 투자하여 리스크를 줄이세요.",
            icon: require('@/assets/img/analysis/17.png'),
            link: "/global-diversification"
          },
          {
            title: "대체 투자 전략",
            description: "전통적인 투자 외에도 대체 자산에 투자하여 새로운 기회를 잡아보세요.",
            icon: require('@/assets/img/analysis/18.png'),
            link: "/alternative-investments"
          }
        ];
      } else if (this.userType === "APML") { // 리더형 투자자
        this.recommendedContent = [
          {
            title: "리더십을 발휘하는 투자",
            description: "리더십을 발휘하여 시장을 선도하는 전략을 확인하세요.",
            icon: require('@/assets/img/analysis/19.png'),
            link: "/leadership-investment"
          },
          {
            title: "혁신 기술에 투자",
            description: "첨단 기술과 혁신적인 상품에 투자하여 미래를 준비하세요.",
            icon: require('@/assets/img/analysis/20.png'),
            link: "/tech-investments"
          }
        ];
      } else if (this.userType === "ABWL") { // 공격적인 투자 성향
        this.recommendedContent = [
          {
            title: "고위험 혁신 투자",
            description: "창의적이고 혁신적인 방법으로 높은 수익을 노릴 수 있는 상품을 확인하세요.",
            icon: require('@/assets/img/analysis/20.png'),
            link: "/innovative-investments"
          },
          {
            title: "시장 트렌드 활용",
            description: "빠르게 변화하는 시장 트렌드를 활용하여 기회를 잡으세요.",
            icon: require('@/assets/img/analysis/20.png'),
            link: "/market-trends"
          }
        ];
      } else if (this.userType === "ABML") { // 트렌드세터 투자자
        this.recommendedContent = [
          {
            title: "최신 투자 트렌드",
            description: "트렌드를 선도하는 최신 투자 기회를 알아보세요.",
            icon: require('@/assets/img/analysis/20.png'),
            link: "/latest-trends"
          },
          {
            title: "혁신적인 신기술 투자",
            description: "리스크를 감수하며 신기술에 투자하여 미래 수익을 기대하세요.",
            icon: require('@/assets/img/analysis/20.png'),
            link: "/innovative-tech"
          }
        ];
      } else if (this.userType === "ABWC") { // 도전형 투자자
        this.recommendedContent = [
          {
            title: "도전적 고위험 투자",
            description: "고위험 자산에 과감하게 투자하여 높은 수익을 추구하세요.",
            icon: require('@/assets/img/analysis/20.png'),
            link: "/high-risk-challenge"
          },
          {
            title: "불확실한 시장에서 기회 찾기",
            description: "불확실한 시장에서도 기회를 찾는 투자 전략을 알아보세요.",
            icon: require('@/assets/img/analysis/20.png'),
            link: "/uncertain-market"
          }
        ];
      } else if (this.userType === "APMC") { // 투자 달인
        this.recommendedContent = [
          {
            title: "복잡한 금융 상품 분석",
            description: "복잡한 금융 상품을 분석하고 최적의 투자 방법을 찾아보세요.",
            icon: require('@/assets/img/analysis/20.png'),
            link: "/complex-finance"
          },
          {
            title: "고수익 금융 상품",
            description: "금융 전문가를 위한 고수익 상품에 대해 알아보세요.",
            icon: require('@/assets/img/analysis/20.png'),
            link: "/high-profit-finance"
          }
        ];
      } else if (this.userType === "ABMC") { // 시대를 앞서가는 투자 명장
        this.recommendedContent = [
          {
            title: "미래를 선도하는 투자",
            description: "시대를 앞서가는 통찰력을 통해 미래를 준비하는 투자 전략을 확인하세요.",
            icon: require('@/assets/img/analysis/20.png'),
            link: "/future-leadership"
          },
          {
            title: "첨단 기술 투자",
            description: "첨단 기술에 투자하여 시장을 선도하는 방법을 알아보세요.",
            icon: require('@/assets/img/analysis/20.png'),
            link: "/advanced-tech"
          }
        ];
      } else { // 미래지향적 투자 협력가
        this.recommendedContent = [
          {
            title: "장기적 큰 수익 목표",
            description: "장기적으로 큰 수익을 목표로 하는 전략을 확인하세요.",
            icon: require('@/assets/img/analysis/20.png'),
            link: "/long-term-profit"
          },
          {
            title: "고위험 장기 투자",
            description: "극단적인 리스크를 감수하고 장기적인 성장을 추구하는 투자 방법을 알아보세요.",
            icon: require('@/assets/img/analysis/20.png'),
            link: "/high-risk-longterm"
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
@keyframes slideIn {
  from {
    transform: translateY(20px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

.result-view {
  text-align: center;
  background-color: #f9f9f9;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
}

.animated-text {
  animation: slideIn 0.5s ease-out forwards;
}

h1, h2, .result-card h3, .content-card p, .content-card h4 {
  animation: slideIn 0.5s ease-out forwards;
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
