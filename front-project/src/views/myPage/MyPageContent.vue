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
            <td>이름</td>
            <td></td>
          </tr>
          <tr>
            <td>이메일</td>
            <td>dd</td>
          </tr>
          <tr>
            <td>전화번호</td>
            <td></td>
          </tr>
          <tr>
            <td>성별</td>
            <td></td>
          </tr>
          <tr>
            <td>투자성향</td>
            <td></td>
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

</script>