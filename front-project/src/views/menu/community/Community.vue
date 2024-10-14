<template>
  <div class="content fs-6 d-flex flex-column flex-column-fluid" id="kt_content">
    <div class="post container fs-6 d-flex flex-column-fluid" id="kt_post">
      <div class="container-xxl d-flex">
        <!-- 왼쪽에 Community Panel -->
        <div class="col-xl-3">
          <CommunityPanel />
        </div>

        <!-- 중앙에 CommunityWriter와 포스트 목록을 포함하는 스크롤 컨테이너 -->
        <div class="col-xl-6 d-flex flex-column">
          <!-- 스크롤 가능한 컨테이너 -->
          <div class="scrollable-container flex-grow-1 overflow-auto">
            <!-- CommunityWriter 페이지에 접근 권한 전달 -->
            <CommunityWriter :isAuthorized="isAuthorized" />

            <!-- 포스트 목록을 렌더링하는 router-view -->
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
    <!-- 푸터가 여기에 위치 -->
    <Footer />
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import CommunityPanel from "@/views/menu/community/CommunityPanel.vue";
import CommunityWriter from "@/views/menu/community/CommunityWriter.vue";
import axios from "axios";

// Router 인스턴스
const router = useRouter();
const route = useRoute();

// 사용자의 investType을 저장할 ref
const userInvestType = ref(null);

// 현재 접근하려는 postType을 route 파라미터나 경로에서 가져옴
const currentPostType = ref(route.path.split('/').pop());

// 접근 권한 여부를 저장할 ref
const isAuthorized = ref(false);

// INVEST_TYPE에 따른 사용자 권한 확인 함수
async function checkUserAuthorization() {
  const authValue = localStorage.getItem('auth');
  if (authValue) {
    try {
      const authData = JSON.parse(authValue);
      if (authData && authData.id && authData.token) {
        const response = await axios.get(`/api/member/${authData.id}`, {
          headers: {
            'Authorization': `Bearer ${authData.token}`,
          },
        });

        const investType = response.data.investType;
        userInvestType.value = investType;

        // postType과 investType 매핑
        const investTypeMap = {
          'stability': 1,
          'neutral': 2,
          'activeInvestment': 3,
          'aggressiveInvestment': 4,
        };

        if (investTypeMap[currentPostType.value] === investType) {
          isAuthorized.value = true;
        } else {
          isAuthorized.value = false;
        }
      } else {
        console.error('유효하지 않은 authData:', authData);
        isAuthorized.value = false;
      }
    } catch (error) {
      console.error('사용자 정보를 가져오는 중 오류 발생:', error);
      isAuthorized.value = false;
    }
  } else {
    console.log('Auth value not found');
    isAuthorized.value = false;
  }
}

// 컴포넌트 마운트 시 권한 확인 함수 호출
onMounted(() => {
  checkUserAuthorization();
});
</script>

<style scoped>
.container-xxl {
  display: flex;
  align-content: center;
  justify-content: space-between;
  gap: 30px;
  width: 90%;
}

.content{
  background-color: rgba(249, 249, 249, 1);
  align-content: center;
}

/* 패널 크기 */
.col-xl-3 {
  width: 280px; /* CommunityPanel의 고정 너비 */
}

/* 메인 콘텐츠 크기 */
.col-xl-6 {
  flex-grow: 1; /* 나머지 공간을 차지하게 설정 */
  height: 100vh;
}
.scrollable-container {
  /* 기존 스타일 유지 */
  flex-grow: 1;
  overflow-y: auto; /* 세로 스크롤 가능 */
  padding: 10px; /* 필요시 패딩 추가 */
  display: flex;
  flex-direction: column;

  /* 스크롤바 숨기기 */
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none; /* Internet Explorer 10+ */
}

.scrollable-container::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera */
}
</style>
