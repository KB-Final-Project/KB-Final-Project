<template>
<div class="dic">
  <div class="d-lg-flex h-100 position-relative">
    <!-- Home button -->
    <router-link class="text-nav btn btn-icon bg-light border rounded-circle position-absolute top-0 end-0 p-0 mt-3 me-3 mt-sm-4 me-sm-4" to="/" data-bs-toggle="tooltip" data-bs-placement="left" title="Back to home">
      <i class="ai-home"></i>
    </router-link>
    
    <!-- Sign up form -->
    <div class="signUpPage d-flex flex-column align-items-center justify-content-center w-lg-50 px-3 h-100 px-lg-5 pt-5">
      <div class="w-100" style="max-width: 526px;">
        <h1 style="font-size: 40px; font-weight: 700;">회원가입</h1><br>
        <p class="pb-3 mb-3 mb-lg-4">
          이미 계정이 있으신가요?&nbsp;&nbsp;
          <router-link class="login" to="/signin">로그인</router-link>
        </p>
        <form class="needs-validation" @submit.prevent="handleSignup" novalidate>
          <div class="row row-cols-1 row-cols-sm-2">
            <div class="col mb-4">
              <input v-model="name" class="form-control form-control-lg  ps-5" type="text" placeholder="이름을 입력하세요" required>
            </div>
            <div class="col mb-4">
              <input v-model="email" class="form-control form-control-lg  ps-5" type="email" placeholder="이메일을 입력하세요" required>
            </div>
          </div>
          <div class="password-toggle mb-4">
            <input v-model="password" class="form-control form-control-lg ps-5" type="password" placeholder="비밀번호를 입력하세요" required>
            <label class="password-toggle-btn" aria-label="Show/hide password">
              <input class="password-toggle-check" type="checkbox">
              <span class="password-toggle-indicator"></span>
            </label>
          </div>
          <div class="password-toggle mb-4">
            <input v-model="confirmPassword" class="form-control form-control-lg ps-5" type="password" placeholder="비밀번호를 확인하세요" required>
            <label class="password-toggle-btn" aria-label="Show/hide password">
              <input class="password-toggle-check" type="checkbox">
              <span class="password-toggle-indicator"></span>
            </label>
          </div>
          <div class="pb-4">
            <div class="form-check my-2">
              <input v-model="agreeTerms" class="form-check-input" type="checkbox" id="terms">
              <label class="form-check-label ms-1" for="terms"><a href="#">이용약관</a>에 동의합니다</label>
            </div>
          </div>
          <button class="signUpBtn w-100 mb-4" type="submit">회원가입</button>
          <h2 style="font-size: 15px;font-weight: 700;" class="h6 text-center pt-3 pt-lg-4 mb-4">간편 로그인</h2>
          <div class="row row-cols-1 row-cols-sm-2 gy-3">
            <div class="col"><a class="btn btn-icon btn-outline-secondary btn-google btn-lg w-100" href="#"><i class="ai-google fs-xl me-2"></i>Google</a></div>
            <div class="col"><a class="btn btn-icon btn-outline-secondary btn-facebook btn-lg w-100" href="#"><i class="ai-facebook fs-xl me-2"></i>Facebook</a></div>
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
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import signCoverImage from "@/views/SignCoverImage.vue";


const router = useRouter();
const name = ref('');
const email = ref('');
const password = ref('');
const confirmPassword = ref('');
const agreeTerms = ref(false);

const handleSignup = () => {
  // 여기에 회원가입 로직을 구현합니다.
  console.log('Name:', name.value);
  console.log('Email:', email.value);
  console.log('Password:', password.value);
  console.log('Confirm Password:', confirmPassword.value);
  console.log('Agree to Terms:', agreeTerms.value);
  
  // 회원가입 성공 시 로그인 페이지로 이동
  router.push('/signin');
};
</script>

<style scoped>
.signUpBtn{
  width: 526px;
  height: 56px;
  border-radius: 30px;
  background-color: #37715d;
  color: white;
  font-size: 20px;
}

.signUpBtn:hover{
   border: 1px solid lightgrey;
   background-color: lightgrey;
   color: black;
 }

.col.mb-4 input{
  width: 252px;
  height: 61px;
  border-radius: 20px;
}
.password-toggle.mb-4 input{
  width: 526px;
  height: 61px;
  border-radius: 20px;
}
.w-100 p{
  font-size: 15px;
}
.login{
  color: #37715d;
}
.form-check-label.ms-1 a{
  color: #37715d;
}

.form-check-label.ms-1{
  font-size: 15px;
}

.dic{
  height: 100vh;
}
.signUpPage{
  margin: 0 auto;
  justify-content: center;
  padding: 20px;
}
.content{
  padding: 0;
}

.form-check input:checked{
  background-color: rgba(68, 140, 116, 1);
  border-color:rgba(68, 140, 116, 1);
}
</style>