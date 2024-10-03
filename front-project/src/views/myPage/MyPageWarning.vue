<template>
  <div>
    <br><br><br><br>
    <h1><i class="ai-octagon-alert"></i> 잠깐만요!</h1><br><br>
    <div class="profileBox">
      <i class="d-inline ai-lock-open"></i>
      <h2 class="d-inline"> 마이페이지 설정을 하기전에 비밀번호를 입력해주세요</h2>
      <br> <br>
      <div class="formMyInfo d-flex flex-wrap justify-content-center">
        <div class="mb-3">
          <label class="form-label" for="pass-visibility1">현재 비밀번호</label>
          <div class="password-toggle">
            <input class="form-control" type="password" id="pass-visibility1" value="hidden@password">
            <label class="password-toggle-btn" aria-label="Show/hide password">
              <input class="password-toggle-check" type="checkbox">
              <span class="password-toggle-indicator"></span>
            </label>
          </div><br>
          <div class="btn d-flex">
            <button class="cancelBtn" type="reset">취소</button>
            <button class="submitBtn" type="submit">확인</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

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
.form-control{
  border: none;
  margin-top: -23px;
  height: 50px;
}

h1{
  margin: 20px;
}
.profileBox {
  border-radius: 30px;
  background-color: white;
  padding: 50px;
}
.ai-octagon-alert{
  font-size:40px;
  font-weight: 700;
  vertical-align: text-bottom;
  color: rgba(68, 140, 116, 1);
}
.ai-lock-open{
  font-size:30px;
  vertical-align: text-bottom;
  color: rgba(68, 140, 116, 1);
}

.cancelBtn{
  width: 150px;
  height: 40px;
  border-radius: 10px;
  background-color: lightgrey;
  border: 1px solid rgba(153, 153, 153, 0.6)
}

.cancelBtn:active{
  background-color:  rgba(68, 140, 116, 1);
  color: white;
}

.btn{
  display: flex;
  gap: 20px;
  font-size: 15px;
  justify-content: center;
}

.submitBtn:active{
  background-color:  lightgrey;
  color: black;
}

.submitBtn{
  width: 150px;
  height: 40px;
  border-radius: 10px;
  color: white;
  border: 1px solid rgba(153, 153, 153, 0.6);
  background-color: rgba(68, 140, 116, 1);
}


</style>

<script setup>

</script>