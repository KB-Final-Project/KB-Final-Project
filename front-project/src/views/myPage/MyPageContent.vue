<template>
  <div>
    <br><br><br><br>
    <h1>프로필 정보</h1><br><br>
    <div class="profileBox">
      <i class="d-inline ai-user"></i>
      <h2 class="d-inline"> 계정 정보</h2>
      <br> <br>
      <table>
        <tbody>
          <tr>
            <td>ID</td>
            <td>{{ myPage.id}}</td>
          </tr>
          <tr>
            <td>이름</td>
            <td>{{ myPage.name}}</td>
          </tr>
          <tr>
            <td>이메일</td>
            <td>{{ myPage.email}}</td>
          </tr>
          <tr>
            <td>가입날짜</td>
            <td>{{ myPage.createDate}}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped>

h1{
  margin: 20px;
}
.profileBox {
  border-radius: 30px;
  background-color: white;
  padding: 30px;
}
.ai-user{
  font-size:27px;
  vertical-align: text-bottom;
  color: rgba(68, 140, 116, 1);
}

table tbody tr td{
  font-size: 25px;
  padding: 10px;
}

table tbody tr td:nth-child(2){
  font-size: 25px;
  padding: 10px;
  color: #6c6c6c;
  width: 10%;
}

</style>

<script setup>
import axios from "axios";
import {onMounted, ref} from "vue";

const myPage = ref([]);
const loading = ref(true);

const fetchMyPage = async () => {
  loading.value = true;
  try {
    const response = await axios.get('/api/member/{id}');
    console.log(response);
    myPage.value = response.member.data
  } catch (error) {
    console.error('적금 상품 목록을 가져오는 중 오류 발생:', error);
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchMyPage();
});

</script>