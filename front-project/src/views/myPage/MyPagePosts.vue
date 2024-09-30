<template>
  <div>
    <br><br><br><br>
    <h1>내가 쓴 글</h1><br><br>
    <div class="profileBox">
      <i class="d-inline ai-dashboard"></i>
      <h2 class="d-inline"> 타임라인</h2>
      <br> <br>
      <div class="card mb-5 mb-xxl-8">
        <div class="card-body pb-0">
          <div class="d-flex align-items-center">
            <div class="symbol symbol-45px me-5">
              <img src="/img/imsi.png" alt="" />
            </div>
            <div class="d-flex flex-column">
              <a href="#" class="text-gray-800 text-hover-primary mb-1 fs-6 fw-bolder"><h2>이사벨라</h2></a>
              <span class="text-gray-500 fw-semibold">어제 3시간 30분 전</span>
            </div>
          </div>
          <div class="pt-5">
            <div class="bgi-no-repeat bgi-size-cover rounded min-h-250px mb-5" :style="{ backgroundImage: `url(${require('@/assets/media/stock/900x600/20.jpg')})` }"></div>
            <p class="text-gray-800 fs-3 fw-normal mb-3">집에 가고 싶다...옥상에서 한 장. ,,</p>
            <div class="d-flex align-items-center">
              <a href="#" class="btn btn-sm btn-color-muted btn-active-light-primary fw-bolder fs-6 py-1 px-2 me-4">
                <i class="ki-duotone ki-message-text-2 fs-2">
                  <span class="path1"></span>
                  <span class="path2"></span>
                  <span class="path3"></span>
                </i>24
              </a>
              <a href="#" class="btn btn-sm btn-color-muted btn-active-light-danger fw-bold fs-6 py-1 px-2">
                <i class="ki-duotone ki-heart fs-2 text-danger">
                  <span class="path1"></span>
                  <span class="path2"></span>
                </i>75
              </a>
            </div>
          </div>
          <div class="separator pt-4 mb-3"></div>
          <form class="position-relative pb-3">
            <textarea data-kt-autosize="true" class="form-control border-0 p-0 pe-10 resize-none min-h-25px" rows="1" placeholder="Reply.."></textarea>
            <div class="position-absolute top-0 end-0 me-n5">
              <span class="btn btn-icon btn-sm btn-active-color-primary pe-0">
                <i class="ki-duotone ki-paper-clip fs-2 mb-3"></i>
              </span>
              <span class="btn btn-icon btn-sm btn-active-color-primary ps-0">
                <i class="ki-duotone ki-geolocation fs-2 mb-3">
                  <span class="path1"></span>
                  <span class="path2"></span>
                </i>
              </span>
            </div>
          </form>
        </div>
      </div>
      <div class="d-flex justify-content-center">
      <br><button class="timeBtn">더보기</button><br>
      </div>
    </div>
  </div>
</template>

<style scoped>
.timeBtn{
  width: 250px;
  height: 40px;
  border-radius: 10px;
  color: white;
  border: 1px solid rgba(153, 153, 153, 0.6);
  background-color: rgba(68, 140, 116, 1);
  font-size:20px;
}
h1{
  margin: 20px;
}
.profileBox {
  border-radius: 30px;
  background-color: white;
  padding: 40px;
}
.ai-dashboard{
  font-size:27px;
  vertical-align: text-bottom;
  color: rgba(68, 140, 116, 1);
}

.card{
  border-radius: 30px;
}

</style>

<script setup>

</script>