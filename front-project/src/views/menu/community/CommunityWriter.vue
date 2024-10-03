<template>
  <div class="container">
    <div class="col">
      <div class="feeds card mb-5 mb-xxl-8">
        <div class="card-body pb-0">
          <div class="d-flex align-items-center">
            <div class="symbol symbol-45px me-5">
              <img src="/img/imsi.png"/>
            </div>
            <div class="d-flex flex-column">
              <h3>이사벨라</h3>
            </div>
            <div class="button-group position-absolute top-0 end-0 mt-5 me-5">
              <button class="cancel me-3">취소</button>
              <button class="submit">작성</button>
            </div>
          </div>
          <div class="pt-3">
            <p class="fs-6 fw-normal">
              <textarea class="writer"  type="text" placeholder="무슨 일이 일어나고 있나요?"></textarea>
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.submit{
  width: 100px;
  height: 30px;
  border-radius: 30px;
  border: none;
  background-color: rgb(67, 140, 116);
  color: white;
  font-size: 15px;
}
.cancel{
  width: 100px;
  height: 30px;
  border-radius: 30px;
  border: none;
  font-size: 15px;
}

.card{
  border: none;
}

.feeds{
  border-radius: 30px;
}
.writer{
  text-align: start;
  margin-left: 15px;
  width: 800px;
  height: 180px;
  border-radius: 20px;
  padding: 20px;
  border: 1px solid lightgrey;
  resize: none;
  font-size: 20px;
}
</style>

<script setup>
</script>