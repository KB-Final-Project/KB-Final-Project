<template>
  <div>
    <br><br>
    <h2><i class="ai-octagon-alert"></i> 잠깐만요!</h2>
    <div class="profileBox">
      <i class="d-inline ai-lock-open"></i>
      <h3 class="d-inline"> 마이페이지 설정을 하기 전에 비밀번호를 입력해주세요</h3>
      <div class="formMyInfo d-flex flex-wrap justify-content-center">
        <div class="mb-3">
          <label class="form-label" for="pass-visibility1">현재 비밀번호</label>
          <div class="password-toggle">
            <input class="form-control" type="password" id="pass-visibility1" v-model="changePassword" />
            <label class="password-toggle-btn" aria-label="Show/hide password">
              <input class="password-toggle-check" type="checkbox" @click="togglePasswordVisibility" />
              <span class="password-toggle-indicator"></span>
            </label>
          </div>
          <div class="btn d-flex">
            <button class="cancelBtn" type="button" @click="cancel">취소</button>
            <button class="submitBtn" @click="checkPassword">확인</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, defineEmits, reactive } from 'vue';
import axios from 'axios';

// eslint-disable-next-line vue/valid-define-emits
const emit = defineEmits();
const changePassword = reactive({});

const togglePasswordVisibility = () => {
  const passwordField = document.getElementById('pass-visibility1');
  passwordField.type = passwordField.type === 'password' ? 'text' : 'password';
};

const token = JSON.parse(localStorage.getItem("auth"));

const checkPassword =() => {
  const password = token.password;
    if (password.eqauls(changePassword.oldPassword)) {
      emit('password-success');
    } else {
      alert('비밀번호가 일치하지 않습니다.');
    }
};

</script>

<style scoped>
.formMyInfo {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: 20px;
  margin: 20px;
}

.password-toggle {
  width: 400px;
  height: 50px;
  border-radius: 20px;
  padding: 30px;
  border: 1px solid rgba(153, 153, 153, 0.6);
}

.form-control {
  border: none;
  margin-top: -23px;
  height: 50px;
}

.profileBox {
  border-radius: 30px;
  background-color: white;
  padding: 50px;
}

.ai-octagon-alert {
  font-size: 35px;
  font-weight: 700;
  color: rgba(68, 140, 116, 1);
}

.ai-lock-open {
  font-size: 25px;
  color: rgba(68, 140, 116, 1);
}

.cancelBtn {
  width: 150px;
  height: 40px;
  border-radius: 10px;
  background-color: lightgrey;
}

.btn {
  display: flex;
  gap: 20px;
  justify-content: center;
}

.submitBtn {
  width: 150px;
  height: 40px;
  border-radius: 10px;
  color: white;
  background-color: rgba(68, 140, 116, 1);
}
</style>
