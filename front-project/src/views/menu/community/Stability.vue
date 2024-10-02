<template>
  <div class="container">
    <div class="col">
      <div class="feeds card mb-5 mb-xxl-8">
        <div class="card-body pb-0">
          <div class="d-flex align-items-center">
            <div class="symbol symbol-45px me-5">
              <img :src="require('@/assets/media/avatars/300-25.jpg')" alt="" />
            </div>
            <div class="d-flex flex-column">
              <a href="#" class="text-gray-800 text-hover-primary mb-1 fs-6 fw-bolder">Brad Dennis</a>
              <span class="text-gray-500 fw-semibold">Yesterday at 5:06 PM</span>
            </div>
          </div>
          <div class="pt-5">
            <p class="text-gray-800 fs-6 fw-normal mb-3">
              Outlines keep you honest. They stop you from indulging in poorly thought-out metaphors about driving and keep you focused on the overall structure of your post
            </p>
            <div class="d-flex align-items-center">
              <a href="#" class="btn btn-sm btn-color-muted btn-active-light-primary fw-bolder fs-6 py-1 px-2 me-4">
                <i class="ki-duotone ki-message-text-2 fs-2">
                  <span class="path1"></span>
                  <span class="path2"></span>
                  <span class="path3"></span>
                </i>24
              </a>
              <a href="#" class="btn btn-sm btn-color-muted btn-active-light-danger fw-bold fs-6 py-1 px-2">
                <i class="ki-duotone ki-heart fs-2">
                  <span class="path1"></span>
                  <span class="path2"></span>
                </i>75
              </a>
            </div>
          </div>
          <div class="separator pt-5 mb-3"></div>
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
      <button class="btn btn-primary w-100 text-center" id="kt_widget_5_load_more_btn" @click="loadMoreFeeds">
        <span class="indicator-label">More Feeds</span>
        <span class="indicator-progress">Loading...
        <span class="spinner-border spinner-border-sm align-middle ms-2"></span></span>
      </button>
    </div>
  </div>
</template>

<style scoped>
.feeds{
  border-radius: 30px;
}


</style>