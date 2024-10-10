<template>
  <div class="container">
    <div class="col">
      <div
        class="feeds card mb-5 mb-xxl-8"
        v-for="(post, index) in posts"
        :key="post.id"
      >
        <div class="card-body pb-0">
          <div class="d-flex align-items-center">
            <div class="symbol symbol-45px me-5">
              <img :src="require('@/assets/media/avatars/300-25.jpg')" alt="" />
            </div>
            <div class="d-flex flex-column">
              <a href="#" class="name text-gray-800 mb-1 fw-bolder"
                >Brad Dennis</a
              >
              <span class="text-gray-500 fw-semibold"
                >Yesterday at 5:06 PM</span
              >
            </div>
          </div>
          <div class="pt-5">
            <p class="text-gray-800 fw-normal mb-3">안녕하세요</p>
            <li>
              <button class="delete-button" @click="openPopup(index)">
                삭제
              </button>
            </li>
            <div class="d-flex align-items-center">
              <a
                href="#"
                class="btn btn-sm btn-color-muted btn-active-light-primary fw-bolder fs-6 py-1 px-2 me-4"
              >
                <i class="ai-message fs-2"></i>24
              </a>
              <a
                href="#"
                class="btn btn-sm btn-color-muted btn-active-light-danger fw-bold fs-6 py-1 px-2"
              >
                <i class="ai-heart fs-2"></i>75
              </a>
            </div>
          </div>
          <div class="separator pt-5 mb-3"></div>
          <form class="reply position-relative pb-3">
            <textarea
              data-kt-autosize="true"
              class="form-control border-0 p-0 pe-10 resize-none min-h-25px"
              rows="1"
              placeholder="댓글"
            ></textarea>
            <div class="position-absolute top-0 end-0 me-n5">
              <span class="btn btn-icon btn-sm btn-active-color-primary pe-0">
                <i class="ki-duotone ki-paper-clip fs-2 mb-3"></i>
              </span>
              <span class="btn btn-icon btn-sm btn-active-color-primary ps-0">
                <i class="ki-duotone ki-geolocation fs-2 mb-3"></i>
              </span>
            </div>
          </form>
        </div>
      </div>
      <button class="moreBtn w-100 text-center" id="kt_widget_5_load_more_btn">
        <span class="indicator-label">더보기</span>
        <span class="indicator-progress"
          >Loading...
          <span
            class="spinner-border spinner-border-sm align-middle ms-2"
          ></span>
        </span>
      </button>
    </div>

    <div v-if="showPopup" class="popup">
      <h3>삭제 하시겠습니까?</h3>
      <div class="line"></div>
      <p>글을 삭제하시면 되돌릴 수 없습니다.</p>
      <div class="line"></div>
      <button @click="cancelDelete">취소</button>
      <button @click="confirmDelete">확인</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { fetchPosts, deletePost } from '@/api';

const posts = ref([]);
const showPopup = ref(false);
const postToDelete = ref(null);

const loadPosts = async () => {
  try {
    const response = await fetchPosts();
    posts.value = response.data;
  } catch (error) {
    console.error('Error fetching posts:', error);
  }
};

onMounted(loadPosts);

const openPopup = (index) => {
  postToDelete.value = index;
  showPopup.value = true;
};

const cancelDelete = () => {
  showPopup.value = false;
  postToDelete.value = null;
};

const confirmDelete = async () => {
  if (postToDelete.value !== null) {
    await deletePost(posts.value[postToDelete.value].id);
    posts.value.splice(postToDelete.value, 1);
    showPopup.value = false;
    postToDelete.value = null;
  }
};
</script>

<style scoped>
.reply {
  font-size: 20px;
}
.reply textarea {
  font-size: 20px;
}
.pt-5 p {
  font-size: 25px;
}
.name {
  font-size: 20px;
}
.moreBtn {
  width: 200px;
  height: 50px;
  border: none;
  border-radius: 20px;
}
.moreBtn:hover {
  background-color: #438c74;
  color: white;
}
.feeds {
  border-radius: 30px;
  border: none;
}

.popup {
  position: fixed;
  top: 20px;
  right: 20px;
  background-color: white;
  border: 1px solid #ccc;
  padding: 20px;
  z-index: 1000;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.popup h3 {
  margin: 0;
}
.popup .line {
  border-top: 1px solid #ccc;
  margin: 10px 0;
}
</style>
