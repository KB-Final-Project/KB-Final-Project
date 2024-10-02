<script setup>
import { ref, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';

const activePropensity = ref('');
const router = useRouter();
const route = useRoute();

function setActive(propensity) {
  activePropensity.value = propensity;

  // 현재 경로와 비교하여 동일하지 않으면 이동
  let targetRoute = '';
  switch (propensity) {
    case '안정형':
      targetRoute = '/community/stability';
      break;
    case '안정추구형':
      targetRoute = '/community/stabilitySeeking';
      break;
    case '위험중립형':
      targetRoute = '/community/riskNeutral';
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

  // 현재 경로와 비교해서 동일하지 않을 경우에만 이동
  if (route.path !== targetRoute) {
    router.push(targetRoute);
  }
}

// 라우트 변경 시 activePropensity 업데이트
watch(
    () => route.path,
    (newPath) => {
      // 경로가 변경될 때 activePropensity를 정확히 업데이트
      updateActivePropensity(newPath);
    },
    { immediate: true }
);

// 경로에 맞게 activePropensity 값을 설정하는 함수
function updateActivePropensity(path) {
  if (path === '/community/stability') {
    activePropensity.value = '안정형';
  } else if (path === '/community/stabilitySeeking') {
    activePropensity.value = '안정추구형';
  } else if (path === '/community/riskNeutral') {
    activePropensity.value = '위험중립형';
  } else if (path === '/community/activeInvestment') {
    activePropensity.value = '적극투자형';
  } else if (path === '/community/aggressiveInvestment') {
    activePropensity.value = '공격투자형';
  } else {
    activePropensity.value = '';
  }
}

// 페이지 로드시 경로를 확인하고 activePropensity 값을 설정
updateActivePropensity(route.path);
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
        @click="setActive('안정추구형')"
        :class="{ active: activePropensity === '안정추구형' }"
    >
      <h2>안정추구형</h2>
    </div>
    <div
        class="propensity"
        @click="setActive('위험중립형')"
        :class="{ active: activePropensity === '위험중립형' }"
    >
      <h2>위험중립형</h2>
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
        class="propensity"
        @click="router.push('/mypage')"
    >
      <h2 class="myPage">마이페이지</h2>
    </div>
    <br />
    <div>
      <button class="writerBtn">새 글 작성하기</button>
    </div>
    <br />
  </div>
</template>

<style scoped>
.writerBtn {
  width: 300px;
  height: 50px;
  border: none;
  border-radius: 10px;
  font-size: 20px;
  color: white;
  background-color: rgba(67, 140, 116, 1);
}
.propensity {
  padding: 20px;
  cursor: pointer;
}
.propensity:hover {
  border: 1px solid rgba(67, 140, 116, 1);
  border-radius: 20px

}
.propensity.active {
  border: 1px solid rgba(67, 140, 116, 1);
  border-radius: 20px;
  color: rgba(67, 140, 116, 1);
}
.propensity.myPage:hover{

  border-radius: 20px
}
.communityPanel {
  width: 350px;
  height: auto;
  background-color: white;
  border-radius: 30px;
  padding: 15px;
}
.profile {
  padding: 20px;
}
.profile img {
  width: 150px;
  border-radius: 50%;
}
</style>
