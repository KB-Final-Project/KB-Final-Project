<template>
  <section>
    <div class="newsBox">
      <div class="container">
        <br><br><br><br>
        <h1>실시간 주요뉴스</h1>
        <h4>더보기 > </h4>
        <br>
        <div class="newsContent">
          <div class="newsMain">
            <h3>뉴스 날짜 & 제목</h3>
            <img src="/img/news.jpg">
          </div>
          <div class="sideNews">
            <table class="newsTable">
              <tbody>
              <tr>
                <td style="width: 20%; font-size:30px;">뉴스 제목</td>
                <td style="width: 10%; font-size:20px; color: gray;">뉴스 날짜</td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    <br><br><br><br>
    </div>
  </section>
</template>

<style scoped>

.newsMain h3{
  position: absolute;
  font-size: 40px;
  font-weight: 700;
  color: white;
  margin-top: 300px;
  margin-left: 150px;
  z-index: 3;
}
.newsBox {
  width: 100%;
  position: relative;
  padding: 20px 0;
  background-color: rgba(249, 250, 251, 1);
}

.newsBox h1 {
  padding: 0 90px;
  font-weight: 700;
}

.newsContent {
  display: flex;
  justify-content: space-between;
  padding: 0 90px;
}

.newsMain img {
  border-radius: 30px;
  width: 500px;
  height: 500px;
}

.sideNews {
  width: 40%;
  margin-left: 20px;
}

.container h4 {
  text-align: right;
  margin-right: 70px;
  margin-top: -30px;
}
</style>

<script setup lang="ts">
</script>
