<script setup>
import { ref, onMounted, reactive } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import WriterPopup from './WriterPopup.vue';
import axios from 'axios';
import { useAuthStore } from '@/stores/auth';

const activePropensity = ref('');
const showModal = ref(false);
const userPropensity = ref('');

const router = useRouter();
const route = useRoute();

const myInfo = reactive({
  name: '이사벨라',
  email: 'abcd@gmail.com',
  propensity: 1, // 안정형
});

const myInfoStore = useAuthStore();

async function fetchTypes() {
  try {
    const response = await axios.get('/types');
    const typeValue = response.data[0];
    setUserPropensity(typeValue);
    setActivePropensityByType(typeValue);
  } catch (error) {
    console.error('Error fetching types:', error);
  }
}

async function getMyInfo() {
  const authValue = localStorage.getItem('auth');
  
  if (authValue) {
    try {
      const authData = JSON.parse(authValue);
      if (authData.email) {
        myInfo.name = authData.name;
        myInfo.email = authData.email; // email 값 추출
      } else {
        console.log('Email not found in auth data');
      }
    } catch (error) {
      console.error('Error parsing auth data:', error);
    }
  } else {
    console.log('Auth value not found');
  }
}

function setUserPropensity(typeValue) {
  switch (typeValue) {
    case 1:
      userPropensity.value = '안정형';
      break;
    case 2:
      userPropensity.value = '중립형';
      break;
    case 3:
      userPropensity.value = '적극투자형';
      break;
    case 4:
      userPropensity.value = '공격투자형';
      break;
    default:
      console.error('유효하지 않은 유형');
  }
}

function setActivePropensityByType(typeValue) {
  switch (typeValue) {
    case 1:
      setActive('안정형');
      break;
    case 2:
      setActive('중립형');
      break;
    case 3:
      setActive('적극투자형');
      break;
    case 4:
      setActive('공격투자형');
      break;
    default:
      console.error('유효하지 않은 유형');
  }
}

function setActive(propensity) {
  activePropensity.value = propensity;
  let targetRoute = '';
  switch (propensity) {
    case '안정형':
      targetRoute = '/community/stability';
      break;
    case '중립형':
      targetRoute = '/community/neutral';
      break;
    case '적극투자형':
      targetRoute = '/community/activeInvestment';
      break;
    case '공격투자형':
      targetRoute = '/community/aggressiveInvestment';
      break;
    default:
      return;
  }
  if (route.path !== targetRoute) {
    router.push(targetRoute);
  }
}

function openPopup() {
  showModal.value = true;
}

function closePopup() {
  showModal.value = false;
}

onMounted(() => {
  getMyInfo();
  const { propensity } = myInfo; // 이사벨라의 propensity를 가져옴
  setUserPropensity(propensity);
  setActivePropensityByType(propensity);
});
</script>

<template>
  <div class="communityPanel">
    <div class="profile">
      <img src="/img/imsi.png" alt="Profile Picture" />
      <h2 class="name">{{ myInfo.name }}님</h2>
      <h3 class="email">{{ myInfo.email }}</h3>
    </div>
    <div class="propensities">
      <div
        class="propensity"
        @click="setActive('안정형')"
        :class="{ active: activePropensity === '안정형' }"
      >
        <h3>안정형</h3>
      </div>
      <div
        class="propensity"
        @click="setActive('중립형')"
        :class="{ active: activePropensity === '중립형' }"
      >
        <h3>중립형</h3>
      </div>
      <div
        class="propensity"
        @click="setActive('적극투자형')"
        :class="{ active: activePropensity === '적극투자형' }"
      >
        <h3>적극투자형</h3>
      </div>
      <div
        class="propensity"
        @click="setActive('공격투자형')"
        :class="{ active: activePropensity === '공격투자형' }"
      >
        <h3>공격투자형</h3>
      </div>
    </div>
    <div class="myPage" @click="router.push('/mypage')">
      <hr>
      <h3>마이페이지</h3>
    </div>
    <button
      class="writerBtn"
      @click="openPopup"
      :disabled="userPropensity !== activePropensity"
    >
      새 글 작성하기
    </button>
    <div class="warnSign">
      <h5>
        커뮤니티는 게시판 제공만 하고 있습니다<br>
        서비스는
        <a href="/communityPrivacy" class="d-inline"> 커뮤니티정책</a>에 따라 운영됩니다
      </h5>
    </div>
    <WriterPopup v-if="showModal" @close="closePopup" />
    <div v-if="showModal" class="overlay" @click="closePopup"></div>
  </div>
</template>

<style scoped>
.communityPanel {
  position: relative;
  width: 350px;
  background-color: #ffffff;
  border-radius: 20px;
  padding: 20px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.profile {
  text-align: center;
  margin-bottom: 20px;
}

.profile img {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  border: 2px solid rgba(67, 140, 116, 1);
}

.name {
  font-size: 1.5rem;
  margin: 10px 0;
}

.email {
  font-size: 0.9rem;
  color: #777;
}

.propensities {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.propensity {
  padding: 15px;
  cursor: pointer;
  border-radius: 10px;
  transition: background-color 0.3s;
}

.propensity:hover {
  background-color: rgba(67, 140, 116, 0.1);
}

.propensity.active {
  background-color: rgba(67, 140, 116, 0.3);
  font-weight: bold;
}

.myPage {
  padding: 15px;
  cursor: pointer;
  text-align: center;
  border-top: 1px solid #ccc;
  margin-top: 10px;
}

.writerBtn {
  width: 100%;
  height: 50px;
  border: none;
  border-radius: 10px;
  font-size: 20px;
  color: white;
  background-color: rgba(67, 140, 116, 1);
  margin-top: 20px;
  transition: background-color 0.3s;
}

.writerBtn:disabled {
  background-color: rgba(225, 225, 225, 0.5);
  color: #8f8f8f;
}

.warnSign {
  text-align: center;
  margin-top: 20px;
  font-size: 0.9rem;
  color: #0f9d58;
}

a {
  color: #0f9d58;
}

.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 10;
}
</style>
