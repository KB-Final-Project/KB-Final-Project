<template>
  <div>
    <div v-for="post in posts" :key="post.title" class="feeds card mb-5 mb-xxl-8">
      <div class="card-body pb-0">
        <div class="d-flex align-items-center">
          <div class="symbol symbol-45px me-5">
            <img :src="require('@/assets/media/avatars/300-25.jpg')" alt="" />
          </div>
          <div class="d-flex flex-column">
            <a href="#" class="name text-gray-800 mb-1 fw-bolder">Brad Dennis</a>
            <span class="text-gray-500 fw-semibold">Yesterday at 5:06 PM</span>
          </div>
        </div>
        <div class="pt-5">
          <p class="text-gray-800 fw-normal mb-3">
            {{ post.title }} <!-- 게시글 제목 -->
          </p>
          <p class="text-gray-800 fw-normal mb-3">
            {{ post.content }} <!-- 게시글 내용 -->
          </p>
          <div class="d-flex align-items-center">
            <a href="#" class="btn btn-sm btn-color-muted btn-active-light-primary fw-bolder fs-6 py-1 px-2 me-4">
              <i class="ai-message fs-2">
                <span class="path1"></span>
                <span class="path2"></span>
                <span class="path3"></span>
              </i>24
            </a>
            <a href="#" class="btn btn-sm btn-color-muted btn-active-light-danger fw-bold fs-6 py-1 px-2">
              <i class="ai-heart fs-2">
                <span class="path1"></span>
                <span class="path2"></span>
              </i>75
            </a>
          </div>
        </div>
        <div class="separator pt-5 mb-3"></div>
        <form class="reply position-relative pb-3">
          <textarea data-kt-autosize="true" class="form-control border-0 p-0 pe-10 resize-none min-h-25px" rows="1" placeholder="댓글"></textarea>
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
  </div>
  <button class="moreBtn w-100 text-center" id="kt_widget_5_load_more_btn" >
        <span class="indicator-label">더보기</span>
        <span class="indicator-progress">Loading...
        <span class="spinner-border spinner-border-sm align-middle ms-2"></span></span>
      </button>
</template>

<style scoped>
.reply{
  font-size: 20px;
}
.reply textarea{
  font-size: 20px;
}

.pt-5 p{
  font-size: 25px;
}
.name{
  font-size: 20px;
}
.moreBtn{
  width: 200px;
  height: 50px;
  border: none;
  border-radius: 20px;
}

.moreBtn:hover{
  background-color: #438c74;
  color:white;
}

.feeds{
  border-radius: 30px;
  border: none;
}


</style>
<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const posts = ref([]); // 포스트 목록을 저장할 ref

const fetchPosts = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/board/3/posts');
    posts.value = response.data.postList; // postList를 저장
    console.log(posts.value); // 확인용 출력
  } catch (error) {
    console.error('Error fetching posts:', error); // 오류 처리
  }
};

onMounted(() => {
  fetchPosts(); // 컴포넌트가 마운트될 때 데이터 가져오기
});
</script>