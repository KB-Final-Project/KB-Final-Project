<template>
  <div>
    <br><br>
    <h2>설정</h2><br><br>
    <div class="profileBox">
      <i class="d-inline ai-user"></i>
      <h3 class="d-inline"> 계정 정보</h3>
      <br><br>
      <div class="formMyInfo d-flex flex-wrap">
        <div class="form-group">
          <label for="name">이름</label><br>
          <input class="form" id="name" type="text" v-model="info.value.name">
        </div>
        <div class="form-group">
          <label for="email">이메일</label><br>
          <input class="form" id="email" type="email" v-model="info.value.email">
        </div>
      </div>
      <div class="btn d-flex justify-content-end align-items-end">
        <button class="cancelBtn" type="reset">취소</button>
        <button class="submitBtn" type="submit" @click="updateInfo">확인</button>
      </div>
    </div><br><br>

    <div class="passwordBox">
      <i class="ai-lock-closed"></i>
      <h3 class="d-inline"> 비밀번호</h3>
      <br><br>
      <div class="formMyInfo d-flex flex-wrap">
        <div class="mb-3">
          <label class="form-label" for="pass-visibility1">현재 비밀번호</label>
          <div class="password-toggle">
            <input class="form-control" type="password" id="pass-visibility1" v-model="password.oldPassword">
            <label class="password-toggle-btn" aria-label="Show/hide password">
              <input class="password-toggle-check" type="checkbox" @click="togglePasswordVisibility('oldPassword')">
              <span class="password-toggle-indicator"></span>
            </label>
          </div>
        </div>
        <div class="mb-3">
          <label class="form-label" for="pass-visibility2">새 비밀번호</label>
          <div class="password-toggle">
            <input class="form-control" type="password" id="pass-visibility2" v-model="password.newPassword">
            <label class="password-toggle-btn" aria-label="Show/hide password">
              <input class="password-toggle-check" type="checkbox" @click="togglePasswordVisibility('newPassword')">
              <span class="password-toggle-indicator"></span>
            </label>
          </div>
        </div>
        <div>
          <label class="form-label" for="pass-visibility3">새 비밀번호 확인</label>
          <div class="password-toggle">
            <input class="form-control" type="password" id="pass-visibility3" v-model="password.confirmPassword">
            <label class="password-toggle-btn" aria-label="Show/hide password">
              <input class="password-toggle-check" type="checkbox" @click="togglePasswordVisibility('confirmPassword')">
              <span class="password-toggle-indicator"></span>
            </label>
          </div>
        </div>
      </div>
      <div class="warning">
        <i class="d-inline ai-circle-alert"></i>
        <h5 class="d-inline"> 비밀번호는  8자 이상 영문/숫자/특수문자 중, 2가지 이상 포함</h5>
      </div>
      <div class="btn d-flex justify-content-end align-items-end">
        <button class="cancelBtn" type="reset">취소</button>
        <button class="submitBtn" type="submit" @click="changePassword">확인</button>
      </div>
    </div><br><br>

    <div class="profileBox">
      <i class="d-inline ai-image"></i>
      <h2 class="d-inline"> 프로필 사진</h2>
      <h4 class="m-5">PNG, JPG 500px로 등록</h4>
      <br><br>
      <div class="avatarBox d-flex justify-content-center">
        <div class="avatar">
          <label for="avatar" class="form-label">
            <img :src="avatarPath" class="avatar"/><i class="ai-camera"></i>
            <input type="file" class="form-control" ref="avatar" id="avatar" style="display:none" accept="image/png, image/jpeg" @change="uploadAvatar" />
          </label>
        </div>
      </div>
      <div class="btn d-flex justify-content-end align-items-end">
        <button class="cancelBtn" type="reset">취소</button>
        <button class="submitBtn" type="submit" @click="updateAvatar">확인</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";

const loading = ref(true);
const password = ref({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
});
const info = ref({
  value: {
    name: '',
    email: '',
  },
});
const avatarPath = ref('/img/imsi.png'); // Default avatar path

const changeMyInfo = async () => {
  loading.value = true;
  try {
    const response = await axios.get('/api/member/{id}'); // ID를 실제로 사용해야 합니다.
    info.value.name = response.data.member.name;
    info.value.email = response.data.member.email;
  } catch (error) {
    console.error('정보 가져오기 에러:', error);
  } finally {
    loading.value = false;
  }
};

const updateInfo = async () => {
  loading.value = true;
  try {
    await axios.put('/api/member/{id}', info.value); // ID를 실제로 사용해야 합니다.
    alert('정보가 성공적으로 업데이트되었습니다.');
  } catch (error) {
    console.error('정보 업데이트 에러:', error);
  } finally {
    loading.value = false;
  }
};

const changePassword = async () => {
  if (password.value.newPassword !== password.value.confirmPassword) {
    alert('새 비밀번호가 일치하지 않습니다.');
    return;
  }
  loading.value = true;
  try {
    await axios.put('/api/member/{id}/changepassword', {
      oldPassword: password.value.oldPassword,
      newPassword: password.value.newPassword,
    });
    alert('비밀번호가 성공적으로 변경되었습니다.');
  } catch (error) {
    console.error('비밀번호 변경 에러:', error);
  } finally {
    loading.value = false;
  }
};

const uploadAvatar = (event) => {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      avatarPath.value = e.target.result; // Display the uploaded image
    };
    reader.readAsDataURL(file);
  }
};

const updateAvatar = async () => {
  const file = document.getElementById('avatar').files[0];
  const formData = new FormData();
  formData.append('avatar', file);

  loading.value = true;
  try {
    await axios.post('/api/member/avatar', formData); // Upload avatar to the server
    alert('프로필 사진이 성공적으로 변경되었습니다.');
  } catch (error) {
    console.error('아바타 업로드 에러:', error);
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  changeMyInfo();
});
</script>

<style scoped>
.ai-camera {
  font-size: 40px;
  margin-top: -90px;
  color: white;
  font-weight: 700;
}

.avatar {
  width: 150px;
  cursor: pointer;
  text-align: center;
}
.warning {
  width: 55%;
  height: 40px;
  margin: 20px;
  border-radius: 20px;
  padding: 10px;
  background-color: rgba(211, 211, 211, 0.24);
}
.ai-circle-alert {
  font-size: 20px;
  vertical-align: text-bottom;
}

.btn {
  display: flex;
  gap: 20px;
  font-size: 15px;
}

h1 {
  margin: 20px;
}

.form-check-input {
  width: 20px;
  height: 20px;
}

.form-check-input:checked {
  background-color: rgba(68, 140, 116, 1);
  border-color: rgba(68, 140, 116, 1);
}

.form-check-label {
  font-size: 20px;
  padding-left: 5px;
}

.form {
  width: 400px;
  height: 50px;
  border-radius: 20px;
  padding: 30px;
  border: 1px solid rgba(153, 153, 153, 0.6);
}
.password-toggle {
  width: 400px;
  height: 50px;
  border-radius: 20px;
  padding: 30px;
  border: 1px solid rgba(153, 153, 153, 0.6);
}

.formMyInfo {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: 20px;
  margin: 20px;
}

label {
  font-size: 15px;
}

.form-group {
  flex: 1 1 45%;
}

.profileBox {
  border-radius: 30px;
  background-color: white;
  padding: 30px;
  font-size: 20px;
}
.passwordBox {
  border-radius: 30px;
  background-color: white;
  padding: 30px;
}
.passwordBox .form-control {
  border: none;
  margin-top: -25px;
  width: 350px;
  height: 50px;
  margin-left: -10px;
}

.cancelBtn {
  width: 150px;
  height: 40px;
  border-radius: 10px;
  background-color: lightgrey;
  border: 1px solid rgba(153, 153, 153, 0.6)
}

.cancelBtn:active {
  background-color: rgba(68, 140, 116, 1);
  color: white;
}

.submitBtn:active {
  background-color: lightgrey;
  color: black;
}

.submitBtn {
  width: 150px;
  height: 40px;
  border-radius: 10px;
  color: white;
  border: 1px solid rgba(153, 153, 153, 0.6);
  background-color: rgba(68, 140, 116, 1);
}

.ai-user {
  font-size: 27px;
  vertical-align: text-bottom;
  color: rgba(68, 140, 116, 1);
}
.ai-lock-closed {
  font-size: 27px;
  vertical-align: text-bottom;
  color: rgba(68, 140, 116, 1);
}
.ai-image {
  font-size: 27px;
  vertical-align: text-bottom;
  color: rgba(68, 140, 116, 1);
}
</style>
