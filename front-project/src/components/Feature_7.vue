<template>
  <section>
    <br><br><br><br><br>
    <div class="container text-center investDictionary" @click="goToDic">
      <div class="text-start" >
        <h1>HOW TO</h1>
        <h1>INVEST</h1>
        <h4>투자의 처음과 끝을 알려드립니다.</h4>
        <br><br><br><br>
        <h4>용어사전 바로가기 <i class="ai-arrow-right"></i></h4>
        <img src="/img/how.png">
      </div>
    </div>
    <br> <br> <br> <br>
    <br><br><br><br><br>
  </section>
</template>

<style scoped>
.investDictionary {
  transition: transform 0.6s ease;
  cursor: pointer;
}

.investDictionary:hover{
  transform: scale(1.1);
  z-index: 11111;
}

.investDictionary{
  color: white;
  padding: 100px;
  border: none;
  border-radius: 30px;
  background-color: rgba(68, 140, 116, 1);
  width: 1200px;
  height:400px;
}
h1{
  font-weight: 700;
}
.investDictionary img{
  margin-top: -250px;
  margin-left: 50px;
}

</style>

<script setup>
import {useRouter} from "vue-router";

const router = useRouter();

const goToDic = () => {
  router.push('dictionary');
}
</script>
