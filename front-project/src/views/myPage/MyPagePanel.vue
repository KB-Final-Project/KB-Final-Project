<script setup>
import { ref } from 'vue';
import { defineEmits } from 'vue';

// eslint-disable-next-line vue/valid-define-emits
const emit = defineEmits();

const selectedMenu = ref('');

const navigateTo = (page) => {
  selectedMenu.value = page;
  emit('update-page', page);
};
</script>

<template>
  <div class="my-page-panel">
    <div class="nameBox">
      <img class="d-block profile" src="/img/imsi.png">
      <p class="name">이사벨라</p>
      <p class="nim">님</p>
      <p class="email">isabella@gmail.com</p>
    </div>
    <br><br>
    <div class="panel">
      <h4>마이 페이지</h4><br>
    </div>
    <div class="i">
      <div
          @click="navigateTo('profile')"
          :class="{ active: selectedMenu === 'profile' }"
      >
        <i class="ai-user-check d-inline"></i><h2 class="d-inline"> 프로필 정보</h2>
      </div>
      <div
          @click="navigateTo('settings')"
          :class="{ active: selectedMenu === 'settings' }"
      >
        <i class="ai-settings d-inline"></i><h2 class="d-inline"> 설정</h2>
      </div>
      <div
          @click="navigateTo('posts')"
          :class="{ active: selectedMenu === 'posts' }"
      >
        <i class="ai-file-text d-inline"></i><h2 class="d-inline"> 내가 쓴 글</h2>
      </div>
      <br><br><br><br><br>
      <div
          @click="navigateTo('withdraw')"
          :class="{ active: selectedMenu === 'withdraw' }"
      >
        <i class="ai-logout d-inline"></i><h2 class="d-inline"> 회원 탈퇴</h2>
      </div>
    </div>
  </div>
</template>

<style scoped>
.my-page-panel {
  width: 300px;
  background-color: rgba(247, 249, 252, 1);
  padding: 80px 20px 20px;
}

.profile {
  border-radius: 50%;
  width: 50%;
}

.name {
  font-size: 25px;
  display: inline-block;
}

.nim {
  color: #919090;
  font-size: 25px;
  display: inline-block;
}

.email {
  font-size: 20px;
  font-weight: 100;
  margin-top: -20px;
}

.panel h4 {
  color: #919090;
}

.i {
  font-size: 27px;
  vertical-align: text-bottom;
}

.i div {
  padding-bottom: 10px;
  cursor: pointer;
  transition: color 0.3s;
}

.i div:hover {
  color: rgba(68, 140, 116, 1);
}

.i div.active {
  color: green;
}
</style>
