<template>
  <section>
    <br><br>
    <div class="todayBox text-center">
      <br><br>
      <h2 class="review-section__title mb-4">TODAY</h2>
      <div class="d-flex mt-4 justify-content-center text-start gap-4">
        <div class="todayStock">
          <h3>현재 증시</h3>
          <div class="todayCo">
            코스피 코스닥
          </div>
        </div>
        <div class="todayExchange">
          <h3 class="d-inline-block">환율 정보</h3>
          <a class="d-inline-block mx-xl-20" href="#">더보기<i class="ai-chevron-right"></i></a>
          <div class="exchangeBox">
            <div class="exchangeTitle">환율</div>
            <div class="currency-buttons">
              <button class="exchangeCountry1">일본</button>
              <button class="exchangeCountry2">중국</button>
              <button class="exchangeCountry1">EU</button>
              <button class="exchangeCountry2">호주</button>
              <button class="exchangeCountry1">영국</button>
            </div>
          </div>
        </div>
      </div>
      <br><br>
    </div>

  </section>
</template>

<script>
</script>

<style scoped>
.review-section__title {
  font-size: 40px;
  font-family: J6;
}

.currency-buttons {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  padding: 20px;
}

.todayBox {
  width: 100%;
  background-color: rgba(249, 250, 251, 1);
  padding: 20px 0;
}

.todayBox h3 {
  font-size: 30px;
}

.todayCo {
  width: 700px;
  height: 400px;
  border: 1px solid lightgrey;
  border-radius: 30px;
  padding: 30px;
  background-color: white;
}

.exchangeBox {
  width: 400px;
  height: 400px;
  border: 1px solid lightgrey;
  border-radius: 30px;
  padding: 30px;
  background-color: white;
  display: flex;
  flex-direction: column; /* 내용 세로 정렬 */
  align-items: end; /* 가운데 정렬 */
}

.exchangeCountry1 {
  width: 80px;
  height: 40px;
  border-radius: 20px;
  border: none;
  color: white;
  background-color: rgba(67, 139, 115, 1);
  font-size: 20px;
}

h3 {
  font-family: J4;
  margin: 20px;
}

.exchangeCountry2 {
  width: 80px;
  height: 40px;
  border-radius: 20px;
  border: none;
  font-size: 20px;
  color: white;
  background-color: rgba(144, 218, 170, 1);
}

.exchangeTitle{
  width: 300px;
}

button {
  font-family: J3;
}
</style>
