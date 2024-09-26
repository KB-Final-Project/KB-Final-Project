<script setup>
import { useFundStore } from '@/stores/fundStore';

const fundStore = useFundStore();

const fundDetails = fundStore.fundDetails;
</script>

<template>
  <div class="bc">
    <br><br>
    <div class="container text-start">
      <div class="fundSubject position-relative">
        <h1>{{ fundDetails.name }}</h1>
        <img class="position-absolute bottom-25 end-25" style="width:100px; height:100px;" v-if="fundDetails.img" :src="fundDetails.img" alt="펀드 이미지" />
      </div><br>
      <div class="text-start m-4">
        <h2>{{ fundDetails.name }} 대표 펀드 수익률</h2>
        <h4>실시간으로 가장 수익률이 좋은 펀드를 확인해보세요</h4>
        <div class="d-inline-block fundDetailBox">
          content
        </div>
        <div class="d-inline-block fundDetailBox">
          content
        </div>
        <div class="d-inline-block fundDetailBox">
          content
        </div>
      </div>
      <div class="container text-start m-4">
        <h2>테마 펀드 전체 상품 리스트</h2>
        <h3>{{ fundDetails.name }} 테마에 투자 가능한 전체 상품을 확인해보세요</h3><br>
        <div class="themeList">
          <div class="d-flex justify-content-between align-items-center">
            <h4 class="d-inline">한국투자 NBSP 삼성그룹적립식증권투자신탁2호(주식)-A</h4>
            <h3 class="d-inline">수익률 -3.08%(3개월 누적)</h3>
          </div>
        </div>

      </div>
      <!-- Pagination basic example -->
      <nav aria-label="Page navigation example">
        <ul class="pagination">
          <li class="page-item">
            <a href="#" class="page-link">이전</a>
          </li>
          <li class="page-item d-sm-none">
            <span class="page-link pe-none">2 / 5</span>
          </li>
          <li class="page-item d-none d-sm-block">
            <a href="#" class="page-link">1</a>
          </li>
          <li class="page-item active d-none d-sm-block" aria-current="page">
      <span class="page-link">
        2
        <span class="visually-hidden">(current)</span>
      </span>
          </li>
          <li class="page-item d-none d-sm-block">
            <a href="#" class="page-link">3</a>
          </li>
          <li class="page-item d-none d-sm-block">
            <a href="#" class="page-link">4</a>
          </li>
          <li class="page-item d-none d-sm-block">
            <a href="#" class="page-link">5</a>
          </li>
          <li class="page-item">
            <a href="#" class="page-link">다음</a>
          </li>
        </ul>
      </nav><br><br>
    </div>

  </div>
</template>

<style scoped>
.page-link{
  color: rgba(68, 140, 116, 1);
}

.active>.page-link{
  background-color: rgba(68, 140, 116, 1);
  border: none;

}
.page-link:hover{
  color: white;
}

.pagination {
  --bs-pagination-color: rgba(68, 140, 116, 1);
  --bs-pagination-hover-color:rgba(68, 140, 116, 1);

}

.themeList{
  display: block;
  border: 1px solid rgba(153, 153, 153, 0.6);
  border-radius: 20px;
  width: 130%;
  height: 70px;
  background-color: white;
  margin: 10px;
  padding: 20px;
  align-content: space-between;
}

.bc{
  background-color: rgba(241, 243, 248, 1);
  top: 0;
  left: 0;
  height: 100%;
}
.container {
  width: 70%;
}

.fundDetailBox{
  width: 300px;
  height: 300px;
  border: 1px solid rgba(153, 153, 153, 0.6);
  background-color: white;
  border-radius: 30px;
  margin: 10px;
}

.fundSubject{
  border-radius: 30px;
  background-color: #78a0d5;
  height: 300px;
  padding: 100px;
}

h1{
  font-weight: 700;
  color: white;
  font-size: 30px;
}
</style>