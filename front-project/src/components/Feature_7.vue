<template>
  <section>
    <br><br><br><br><br>
    <div class="container text-center investDictionary" @click="goToDic">
      <div class="text-start" >
        <b class="title-txt">HOW TO</b><br>
        <b class="title-txt">INVEST</b><br>
        <b class="content-txt">투자의 A to Z를 알려드립니다</b><br>
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
  padding: 60px;
  border: none;
  border-radius: 30px;
  background-color: rgba(68, 140, 116, 1);
  width: 1200px;
  height:400px;
}
.investDictionary img{
  margin-top: -250px;
  margin-left: 50px;
}

.title-txt {
  font-size: 50px;
  line-height: 1.1; /* 글자 위아래 간격 줄이기 */
}

.content-txt {
  font-size: 30px;
  line-height: 2;
}

</style>

<script setup>
import {useRouter} from "vue-router";

const router = useRouter();

const goToDic = () => {
  router.push('/dictionary');
}
</script>
