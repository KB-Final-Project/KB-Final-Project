<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import WriterPopup from './WriterPopup.vue';
import axios from 'axios';

const activePropensity = ref('');
const showModal = ref(false);
const userPropensity = ref('');

const router = useRouter();
const route = useRoute();

const dummyUser = {
  name: '이사벨라',
  email: 'abcd@gmail.com',
  propensity: 1, // 안정형
};

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
  const { propensity } = dummyUser; // 이사벨라의 propensity를 가져옴
  setUserPropensity(propensity);
  setActivePropensityByType(propensity);
});
</script>

<template>
  <div class="communityPanel d-inline-block text-start">
    <div class="profile">
      <img src="/img/imsi.png" /><br />
      <h2 class="d-inline">이사벨라</h2><h2 style="font-weight: 100;" class="d-inline">님</h2>
      <h2 style="font-weight: lighter;">abcd@gmail.com</h2>
    </div>
    <br />
    <div
        class="propensity"
        @click="setActive('안정형')"
        :class="{ active: activePropensity === '안정형' }"
    >
      <h2>안정형</h2>
    </div>
    <div
        class="propensity"
        @click="setActive('중립형')"
        :class="{ active: activePropensity === '중립형' }"
    >
      <h2>중립형</h2>
    </div>
    <div
        class="propensity"
        @click="setActive('적극투자형')"
        :class="{ active: activePropensity === '적극투자형' }"
    >
      <h2>적극투자형</h2>
    </div>
    <div
        class="propensity"
        @click="setActive('공격투자형')"
        :class="{ active: activePropensity === '공격투자형' }"
    >
      <h2>공격투자형</h2>
    </div>
    <div
        class="myPage"
        @click="router.push('/mypage')"
    >
      <hr><br>
      <h2>마이페이지</h2>
    </div>
    <br />
    <div>
      <button
          class="writerBtn"
          @click="openPopup"
          :disabled="userPropensity !== activePropensity"
      >새 글 작성하기
      </button>
    </div>
    <br />
  </div>
  <br> <br> <br> <br>
  <div class="warnSign">
    <h5>커뮤니티는 게시판 제공만 하고 있습니다<br>
      서비스는
      <a href="/communityPrivacy" class="d-inline"> 커뮤니티정책</a>에 따라 운영됩니다</h5>
  </div>


  <!-- WriterPopup 컴포넌트 (모달) -->
  <WriterPopup v-if="showModal" @close="closePopup" />

  <!-- 흐림 배경 -->
  <div v-if="showModal" class="overlay" @click="closePopup"></div>
</template>

<style scoped>
.warnSign p{
  color: #0f9d58;
}

a{
  color:#0f9d58;
}

.writerBtn {
  width: 300px;
  height: 50px;
  border: none;
  border-radius: 10px;
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

.propensity {
  width: 300px;
  height: 80px;
  padding: 25px;
  cursor: pointer;
}

.myPage{
  padding: 25px;
  cursor: pointer;
}
.propensity:hover {
  border: 1px solid rgba(67, 140, 116, 1);
  border-radius: 20px;
}
.propensity.active {
  border: 1px solid rgba(67, 140, 116, 1);
  border-radius: 20px;
  color: rgba(67, 140, 116, 1);
}
.myPage:hover{
  border-radius: 20px
}
.communityPanel {
  position: relative;
  width: 350px;
  background-color: white;
  border-radius: 30px;
  padding: 15px;
}
.profile {
  padding: 25px;
}
.profile img {
  width: 150px;
  border-radius: 50%;
}
</style>
