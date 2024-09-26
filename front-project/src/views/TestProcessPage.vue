<template>
  <div class="survey-view">
    <!-- 진행 상황 표시 -->
    <div class="progress-bar">
      <div class="progress" :style="{ width: progressWidth + '%' }"></div>
    </div>

    <!-- 현재 질문 표시 -->
    <div class="question-card">
      <p class="question-number">Q. {{ currentQuestionIndex + 1 }}/{{ questions.length }}</p>
      <h2>{{ currentQuestion.question }}</h2>

      <!-- 아이콘 추가 -->
      <img v-if="currentQuestion.icon" :src="currentQuestion.icon" alt="Icon" class="question-icon"/>

      <!-- 선택지 -->
      <div class="options">
        <button
          v-for="(option, index) in currentQuestion.options"
          :key="index"
          :class="{ selected: selectedOption === index }"
          @click="selectOption(index)"
        >
          {{ option.text }}
        </button>
      </div>
    </div>
    <!-- 이전/다음 버튼 -->
    <div class="navigation-buttons">
      <button v-if="currentQuestionIndex > 0" @click="prevQuestion" class="prev-button">이전으로</button>
      <button v-if="selectedOption !== null" @click="nextQuestion" class="next-button">다음으로</button>
    </div>
  </div>
</template>


<script>
export default {
  data() {
    return {
      currentQuestionIndex: 0, // 현재 질문의 인덱스
      selectedOption: null, // 사용자가 선택한 옵션
      questions: [
        {
          id: 1,
          question: "당신은 20대 또는 30대인가요?",
          options: [
            { text: "네", score: 1 },
            { text: "아니오", score: 0 }
          ],
          icon: require('@/assets/img/analysis/1.png')
        },
        {
          id: 2,
          question: "당신의 연간 소득이 5천만원 이하인가요?",
          options: [
            { text: "네", score: 0 },
            { text: "아니오", score: 1 }
          ],
          icon: require('@/assets/img/analysis/2.png')
        },
        {
          id: 3,
          question: "현재 수입원이 안정적이고 꾸준한가요?",
          options: [
            { text: "네", score: 0 },
            { text: "아니오", score: 1 }
          ],
          icon: require('@/assets/img/analysis/3.png')
        },
        {
          id: 4,
          question: "총 자산 중 금융 자산의 비중이 30% 이하인가요?",
          options: [
            { text: "네", score: 0 },
            { text: "아니오", score: 1 }
          ], 
          icon: require('@/assets/img/analysis/4.png')
        },
        {
          id: 5,
          question: "현재 투자 자산의 비중이 30% 이하인가요?",
          options: [
            { text: "네", score: 0 },
            { text: "아니오", score: 1 }
          ], 
          icon: require('@/assets/img/analysis/5.png')
        },
        {
          id: 6,
          question: "투자 자금을 확보하기 어려운 상황에서 저축을 활용할 수 있나요?",
          options: [
            { text: "네", score: 0 },
            { text: "아니오", score: 1 }
          ], 
          icon: require('@/assets/img/analysis/6.png')
        },
        {
          id: 7,
          question: "투자 성과가 기대와 크게 차이나면 손실을 감수하고 장기적으로 기다릴 수 있나요?",
          options: [
            { text: "네", score: 1 },
            { text: "아니오", score: 0 }
          ], 
          icon: require('@/assets/img/analysis/7.png')
        },
        {
          id: 8,
          question: "주식 시장의 급격한 변동 시 계획대로 투자할 수 있나요?",
          options: [
            { text: "네", score: 1 },
            { text: "아니오", score: 0 }
          ], 
          icon: require('@/assets/img/analysis/8.png')
        },
        {
          id: 9,
          question: "투자 결정을 내릴 때 전문가의 조언을 참고하나요?",
          options: [
            { text: "네", score: 0 },
            { text: "아니오", score: 1 }
          ], 
          icon: require('@/assets/img/analysis/9.png')
        },
        {
          id: 10,
          question: "새로운 금융 상품에 대한 정보를 주로 인터넷 검색을 통해 수집하나요?",
          options: [
            { text: "네", score: 1 },
            { text: "아니오", score: 0 }
          ], 
          icon: require('@/assets/img/analysis/10.png')
        },
        {
          id: 11,
          question: "재산 증식을 위해 적절한 리스크를 감수할 목표가 있나요?",
          options: [
            { text: "네", score: 1 },
            { text: "아니오", score: 0 }
          ], 
          icon: require('@/assets/img/analysis/11.png')
        },
        {
          id: 12,
          question: "투자 시 장기 성장 가능성을 고려하나요?",
          options: [
            { text: "네", score: 1 },
            { text: "아니오", score: 0 }
          ], 
          icon: require('@/assets/img/analysis/12.png')
        },
        {
          id: 13,
          question: "10% 이내의 손실은 감내할 수 있나요?",
          options: [
            { text: "네", score: 1 },
            { text: "아니오", score: 0 }
          ], 
          icon: require('@/assets/img/analysis/13.png')
        },
        {
          id: 14,
          question: "리스크를 줄이기 위해 분산 투자를 선호하나요?",
          options: [
            { text: "네", score: 0 },
            { text: "아니오", score: 1 }
          ], 
          icon: require('@/assets/img/analysis/14.png')
        },
        {
          id: 15,
          question: "정기적인 투자 계획을 세우고 실행하고 있나요?",
          options: [
            { text: "네", score: 1 },
            { text: "아니오", score: 0 }
          ], 
          icon: require('@/assets/img/analysis/15.png')
        },
        {
          id: 16,
          question: "장기 투자 시 자산 배분 전략을 사용할 의향이 있나요?",
          options: [
            { text: "네", score: 1 },
            { text: "아니오", score: 0 }
          ], 
          icon: require('@/assets/img/analysis/16.png')
        },
        {
          id: 17,
          question: "투자 성과를 목표 수익률과 비교하여 평가하나요?",
          options: [
            { text: "네", score: 1 },
            { text: "아니오", score: 0 }
          ], 
          icon: require('@/assets/img/analysis/17.png')
        },
        {
          id: 18,
          question: "당신의 투자 스타일이 중립적이라고 생각하나요?",
          options: [
            { text: "네", score: 0 },
            { text: "아니오", score: 1 }
          ], 
          icon: require('@/assets/img/analysis/18.png')
        },
        {
          id: 19,
          question: "투자에 대한 기본적인 이해도가 있다고 생각하나요?",
          options: [
            { text: "네", score: 1 },
            { text: "아니오", score: 0 }
          ], 
          icon: require('@/assets/img/analysis/19.png')
        },
        {
          id: 20,
          question: "불확실한 경제 상황에서 신중하게 접근할 의향이 있나요?",
          options: [
            { text: "네", score: 0 },
            { text: "아니오", score: 1 }
          ], 
          icon: require('@/assets/img/analysis/20.png')
        },
        // 추가 질문을 여기에 추가합니다.
      ],
      answers: [], // 사용자의 응답을 저장
      totalScore: 0 // 총점 계산
    };
  },
  computed: {
    // 현재 질문 가져오기
    currentQuestion() {
      return this.questions[this.currentQuestionIndex];
    },
    // 진행 상황 퍼센티지 계산
    progressWidth() {
      return ((this.currentQuestionIndex + 1) / this.questions.length) * 100;
    }
  },
  methods: {
    // 옵션 선택
    selectOption(index) {
      this.selectedOption = index;
    },
    // 다음 질문으로 이동
    nextQuestion() {
      const selectedScore = this.currentQuestion.options[this.selectedOption].score;
      this.answers.push({ questionId: this.currentQuestion.id, selectedOption: this.selectedOption, score: selectedScore });
      this.totalScore += selectedScore; // 선택된 옵션의 점수 추가
      this.selectedOption = null;

      if (this.currentQuestionIndex < this.questions.length - 1) {
        this.currentQuestionIndex++;
      } else {
        // 설문 완료 처리
        this.finishSurvey();
      }
    },
    // 이전 질문으로 이동
    prevQuestion() {
      if (this.currentQuestionIndex > 0) {
        const lastAnswer = this.answers.pop();
        this.totalScore -= lastAnswer.score; // 이전에 선택한 점수를 다시 차감
        this.currentQuestionIndex--;
      }
    },
    // 설문 완료 처리
    finishSurvey() {
      console.log("설문이 완료되었습니다.", this.answers);
      console.log("총 점수: ", this.totalScore);
      // 설문 완료 후 추가 작업 (예: 결과 페이지로 이동)
      this.$router.push('/test-result');
    }
  }
};
</script>

<style scoped>
/* 전체 화면 스타일 */
.survey-view {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f9f9f9;
  padding: 20px;
}

/* 진행 바 스타일 */
.progress-bar {
  width: 60%;
  background-color: #e0e0e0;
  height: 6px;
  border-radius: 5px;
  margin-bottom: 20px;
}

.progress {
  height: 6px;
  background-color: #458D75;
  border-radius: 5px;
  width: 0;
}

/* 질문 카드 스타일 */
.question-card {
  background-color: #458D75;
  padding: 40px 30px;
  border-radius: 15px;
  box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 600px;
  text-align: center;
  color: white;
  position: relative;
}

/* 질문 번호와 질문 텍스트 스타일 */
.question-number {
  font-size: 16px;
  font-weight: bold;
  color: #eee;
  text-align: left;
  margin-bottom: 10px;
}

h2 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 30px;
  color: white;
}

/* 질문 아이콘 스타일 */
.question-icon {
  width: 60px;
  height: 60px;
  margin-bottom: 20px;
}

/* 옵션 버튼 스타일 */
.options {
  display: flex;
  justify-content: space-around;
  margin-bottom: 20px;
}

.options button {
  padding: 15px 25px;
  border: none;
  background-color: white;
  border-radius: 10px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s ease;
}

.options button:hover {
  background-color: #6FBAA1;
  color: white;
}

.options button.selected {
  background-color: #6FBAA1;
  color: white;
}

/* 이전/다음 버튼 스타일 */
.navigation-buttons {
  display: flex;
  padding-top: 50px;
  justify-content: space-between;
}

.prev-button,
.next-button {
  padding: 10px 30px;
  border: none;
  background-color: white;
  color: #4CAF50;
  border-radius: 10px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.prev-button:hover,
.next-button:hover {
  background-color: #eee;
}

.prev-button:disabled,
.next-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>