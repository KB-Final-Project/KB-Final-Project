<template>
  <div class="dic">
    <div class="d-lg-flex h-100 position-relative">
      <!-- Home button -->
      <router-link
        class="text-nav btn btn-icon bg-light border rounded-circle position-absolute top-0 end-0 p-0 mt-3 me-3 mt-sm-4 me-sm-4"
        to="/"
        data-bs-toggle="tooltip"
        data-bs-placement="left"
        title="Back to home"
      >
        <i class="ai-home"></i>
      </router-link>

      <!-- Sign up form -->
      <div class="signUpPage d-flex flex-column align-items-center justify-content-center w-lg-50 px-3 h-100 px-lg-5 pt-5">
        <div class="w-100" style="max-width: 526px;">
          <h1 style="font-size: 40px; font-weight: 700;">회원가입</h1><br />
          <p class="pb-3 mb-3 mb-lg-4">
            이미 계정이 있으신가요?&nbsp;&nbsp;
            <router-link class="login" to="/signin">로그인</router-link>
          </p>
          <form class="needs-validation" @submit.prevent="join" novalidate>
            <div class="row row-cols-1 row-cols-sm-2">
              <div class="col mb-4">
                <input
                  v-model="member.name"
                  class="form-control form-control-lg ps-5"
                  type="text"
                  placeholder="이름을 입력하세요"
                  required
                />
              </div>
              <div class="col mb-4">
                <div class="d-flex">
                  <input
                    v-model="member.id"
                    class="form-control form-control-lg ps-5 me-2"
                    type="text"
                    placeholder="아이디를 입력하세요"
                    required
                    @input="changeId"
                  />
                  <button type="button" class="btn btn-success btn-sm py-0 me-2" @click="checkId">ID 중복 확인</button>
                </div>
                <span :class="checkError ? 'text-danger' : 'text-success'">{{ checkError }}</span>
              </div>
            </div>
            <div class="password-toggle mb-4">
              <input
                v-model="member.email"
                class="form-control form-control-lg ps-5"
                type="email"
                placeholder="이메일을 입력하세요"
                required
              />
            </div>
            <div class="password-toggle mb-4">
              <input
                v-model="member.password"
                class="form-control form-control-lg ps-5"
                type="password"
                placeholder="비밀번호를 입력하세요"
                required
              />
              <label class="password-toggle-btn" aria-label="Show/hide password">
                <input class="password-toggle-check" type="checkbox" />
                <span class="password-toggle-indicator"></span>
              </label>
            </div>
            <div class="password-toggle mb-4">
              <input
                v-model="member.password2"
                class="form-control form-control-lg ps-5"
                type="password"
                placeholder="비밀번호를 확인하세요"
                required
              />
              <label class="password-toggle-btn" aria-label="Show/hide password">
                <input class="password-toggle-check" type="checkbox" />
                <span class="password-toggle-indicator"></span>
              </label>
            </div>
            <div class="pb-4">
              <div class="form-check my-2">
                <input v-model="agreeTerms" class="form-check-input" type="checkbox" id="terms" />
                <label class="form-check-label ms-1" for="terms"><a href="#">이용약관</a>에 동의합니다</label>
              </div>
            </div>
            <button class="signUpBtn w-100 mb-4" type="submit">회원가입</button>
            <h2 style="font-size: 15px; font-weight: 700;" class="h6 text-center pt-3 pt-lg-4 mb-4">간편 로그인</h2>
            <div class="row row-cols-1 row-cols-sm-2 gy-3">
              <div class="col">
                <a class="btn btn-icon btn-outline-secondary btn-facebook btn-lg w-100" @click.prevent="kakaoJoin">
                  <i class="ai-facebook fs-xl me-2"></i>카카오 회원가입
                </a>
              </div>
              <div class="col">
                <a class="btn btn-icon btn-outline-secondary btn-google btn-lg w-100" href="#">
                  <i class="ai-google fs-xl me-2"></i>Google
                </a>
              </div>
            </div>
          </form>
        </div>
      </div>
      <!-- Cover image -->
      <signCoverImage></signCoverImage>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import signCoverImage from "@/views/SignCoverImage.vue";
import authApi from '@/api/authApi';

const router = useRouter();
const route = useRoute();
const checkError = ref('');

const member = reactive({
  id: '',
  name: '',
  email: '',
  password: '',
  password2: '',
  kakaoId: '',
});

const disableSubmit = ref(true);
const checkId = async () => {
  if (!member.id) {
    return alert('사용자 ID를 입력하세요.');
  }

  const isTaken = await authApi.checkId(member.id);
  disableSubmit.value = isTaken;
  checkError.value = isTaken ? '이미 사용중인 ID입니다.' : '사용가능한 ID입니다.';
};

const changeId = () => {
  disableSubmit.value = true;
  if (member.id) {
    checkError.value = 'ID 중복 체크를 하셔야 합니다.';
  } else {
    checkError.value = '';
  }
};

const join = async () => {
  if (member.password !== member.password2) {
    return alert('비밀번호가 일치하지 않습니다.');
  }

  try {
    await authApi.create(member);
    router.push({name:'LoginPageForKakao', replace: true});
  } catch (e) {
    console.error(e);
    alert('회원가입에 실패했습니다.'); 
  }
};

onMounted(async () => {
  if (route.query.code != null) {
    const data = await authApi.getKakaoInfo(route.query.code);
    console.log(data.email);
    member.email = data.email;
    member.name = data.nickname;
    member.kakaoId = data.id;
  }
});
</script>

<style scoped>
.signUpBtn {
  width: 526px;
  height: 56px;
  border-radius: 30px;
  background-color: #37715d;
  color: white;
  font-size: 20px;
  border: none;
}

.signUpBtn:hover {
  border: 1px solid lightgrey;
  background-color: lightgrey;
  color: black;
}

.col.mb-4 input {
  width: 252px;
  height: 61px;
  border-radius: 20px;
}

.password-toggle.mb-4 input {
  width: 526px;
  height: 61px;
  border-radius: 20px;
}

.w-100 p {
  font-size: 15px;
}

.login {
  color: #37715d;
}

.form-check-label.ms-1 a {
  color: #37715d;
}

.form-check-label.ms-1 {
  font-size: 15px;
}

.dic {
  height: 100vh;
}

.signUpPage {
  margin: 0 auto;
  justify-content: center;
  padding: 20px;
}

.form-check input:checked {
  background-color: rgba(68, 140, 116, 1);
  border-color: rgba(68, 140, 116, 1);
}
</style>
