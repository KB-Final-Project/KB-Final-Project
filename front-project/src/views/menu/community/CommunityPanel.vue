<script setup>
import { ref, onMounted, reactive } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import WriterPopup from './WriterPopup.vue';
import axios from 'axios';

const propensityTypes = {
  '1': {
    label: '안정형',
    route: '/community/stability',
  },
  '2': {
    label: '중립형',
    route: '/community/neutral',
  },
  '3': {
    label: '적극투자형',
    route: '/community/activeInvestment',
  },
  '4': {
    label: '공격투자형',
    route: '/community/aggressiveInvestment',
  },
};

// 상태 관리
const activePropensity = ref('');
const showModal = ref(false);
const userPropensity = ref('');
const loading = ref(false);
const error = ref(null);

const router = useRouter();
const route = useRoute();

const myInfo = reactive({
  name: '',
  id: '',
  email: '',
  investType: '', // API 응답의 investType 사용
});

// 사용자 정보 가져오기 (API 호출)
async function getMyInfo() {
  const authValue = localStorage.getItem('auth');
  console.log(authValue);

  if (authValue) {
    try {
      const authData = JSON.parse(authValue);
      if (authData.id) {
        myInfo.id = authData.id;
        myInfo.email = authData.email; // email 값 추출

        // 사용자 투자 유형 설정
        if (authData.investType) {
          myInfo.investType = authData.investType.toString();
          userPropensity.value = authData.investType.toString();
        } else {
          console.warn('investType이 auth 데이터에 없습니다.');
        }

        // 동적으로 API 엔드포인트 구성
        loading.value = true;
        const response = await axios.get(`/api/member/${myInfo.id}`);
        const data = response.data;

        if (data) {
          myInfo.name = data.name;
          // 추가적인 사용자 정보가 필요하면 여기에 설정
          // 예: myInfo.someField = data.someField;
        } else {
          console.error('API 응답이 비어 있습니다.');
        }
      } else {
        console.log('Auth 데이터에 id가 없습니다.');
      }
    } catch (err) {
      error.value = '사용자 정보를 가져오는 중 오류가 발생했습니다.';
      console.error('사용자 정보를 가져오는 중 오류 발생:', err);
    } finally {
      loading.value = false;
    }
  } else {
    console.log('Auth 값이 로컬 스토리지에 없습니다.');
    error.value = '로그인이 필요합니다.';
  }
}

// Propensity 설정 및 라우팅
function setActivePropensity(propensity) {
  if (!propensityTypes[propensity]) {
    console.error('유효하지 않은 유형:', propensity);
    return;
  }

  activePropensity.value = propensity;

  const targetRoute = propensityTypes[propensity].route;
  if (route.path !== targetRoute) {
    router.push(targetRoute);
  }
}

// 팝업 제어
function openPopup() {
  showModal.value = true;
}

function closePopup() {
  showModal.value = false;
}

onMounted(async () => {
  await getMyInfo();
  if (myInfo.investType) {
    setActivePropensity(myInfo.investType);
  }
});
</script>

<template>
  <div class="communityPanel d-inline-block text-start">
    <!-- 로딩 상태 표시 -->
    <div v-if="loading" class="loading">로딩 중...</div>

    <!-- 에러 메시지 표시 -->
    <div v-if="error" class="error-message">{{ error }}</div>

    <!-- 로딩이 완료되고 에러가 없을 때 콘텐츠 표시 -->
    <div v-else>
      <div class="profile">
        <img src="/img/imsi.png" alt="프로필 이미지"/><br/>
        <h2 class="d-inline">{{ myInfo.id }}</h2>
        <h2 style="font-weight: 100;" class="d-inline">님</h2>
        <h4 style="font-weight: lighter;">{{ myInfo.email }}</h4>
      </div><br/>

      <div
          v-for="(type, key) in propensityTypes"
          :key="key"
          class="propensity"
          @click="setActivePropensity(key)"
          :class="{ active: activePropensity === key }"
      >
        <h4>{{ type.label }}</h4>
      </div>

      <div
          class="myPage"
          @click="router.push('/mypage')"
      >
        <hr>
        <br>
        <h4>마이페이지</h4>
      </div>

      <div>
        <button
            class="writerBtn"
            @click="openPopup"
            :disabled="activePropensity !== userPropensity"
        >
          새 글 작성하기
        </button>
      </div>
      <br/><br/>
    </div>
  </div>
  <br><br>
  <div class="warnSign">
    <h6>
      커뮤니티는 게시판 제공만 하고 있습니다<br>
      서비스는
      <a href="/communityPrivacy" class="d-inline"> 커뮤니티정책</a>에 따라 운영됩니다
    </h6>
  </div>

  <!-- WriterPopup 컴포넌트 (모달) -->
  <WriterPopup v-if="showModal" @close="closePopup"/>

  <!-- 흐림 배경 -->
  <div v-if="showModal" class="overlay" @click="closePopup"></div>
</template>

<style scoped>
.bc {
  position: fixed;
  padding-bottom: 375px;
}

.warnSign h6{
  color: #4c4c4c;
}

a{
  color:#0f9d58;
}

.writerBtn {
  width: 190px;
  height: 40px;
  border: none;
  border-radius: 20px;
  font-size: 20px;
  color: white;
  background-color: rgba(67, 140, 116, 1);
}

.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5); /* 흐린 배경 */
  z-index: 10; /* 팝업 뒤에 위치하도록 */
}

.writerBtn:disabled{
  background-color: rgba(225, 225, 225, 0.5); /* 흐린 배경 */
  color: #8f8f8f;
}
.writerBtn:hover{
  background-color: rgba(67, 140, 116, 0.31);
  color: rgba(67, 140, 116, 1);
}

.propensity {
  width: 180px;
  padding: 10px;
  cursor: pointer;
  margin: 10px;
}

.myPage{
  padding: 10px;
  cursor: pointer;
  margin: 10px;

}

.propensity:hover {
  width: 180px;
  height: 50px;
  border: 1px solid rgba(67, 140, 116, 1);
  border-radius: 20px;
  padding: 10px;
  margin: 10px;
}

.propensity.active {
  padding: 10px;
  width: 180px;
  height: 50px;
  border: 1px solid rgba(67, 140, 116, 1);
  border-radius: 20px;
  color: rgba(67, 140, 116, 1);
  margin: 10px;
}

.myPage:hover{
  border-radius: 20px;
  margin: 10px;
}

.communityPanel {
  width: 250px;
  height: 630px;
  border-radius: 30px;
  padding: 30px;
  background-color: white;
}

.profile {
  padding: 25px;
  margin-top: -30px;
}

.profile img {
  width: 80px;
  border-radius: 50%;
}
</style>