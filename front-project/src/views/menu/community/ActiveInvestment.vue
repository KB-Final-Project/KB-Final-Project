<script setup>
import { ref, onMounted, computed } from "vue";
import axios from "axios";
import {useAuthStore} from "@/stores/auth";
import api from '@/api/boardApi';
import {useRoute, useRouter} from "vue-router";
const postType = ref(3); // 게시판 타입 (예: 1: 안정형)
const posts = ref([]); // 게시글 목록
const visibleCount = ref(5); // 보여질 게시글 수
const newReply = ref(""); // 댓글 입력을 위한 ref
const postRefs = ref([]); // hidden input 참조 배열
const cr = useRoute();
const router = useRouter();

const auth = useAuthStore();

// 게시글 목록을 가져오는 함수

const fetchBoardPosts = async () => {
  try {
    const response = await axios.get(`/api/board/${postType.value}/posts`);
    posts.value = response.data.postList; // 게시글 목록 설정

    // 게시글 목록을 가져온 후 각 게시글의 postId를 postRefs에 저장
    postRefs.value = posts.value.map(post => post.postId); // postRefs 초기화
    console.log("Fetched posts:", posts.value);
    console.log("Post Refs:", postRefs.value); // postRefs 상태 확인
  } catch (error) {
    console.error("Error fetching posts:", error);
  }
};
const getPostIdFromRef = (index) => {
  if (index < 0 || index >= postRefs.value.length) {
    console.error(`Index ${index} is out of bounds. Available postRefs:`, postRefs.value);
    return null;
  }
  return postRefs.value[index];
};
// 보여질 게시글 계산
const visiblePosts = computed(() => {
  return posts.value.slice(0, visibleCount.value);
});

// 모든 게시글이 로드되었는지 확인
const allPostsLoaded = computed(() => {
  return visibleCount.value >= posts.value.length;
});

// 타임스탬프를 형식화하는 함수
const formatDate = (timestamp) => {
  const date = new Date(timestamp);
  return date.toLocaleString("ko-KR", {
    year: "numeric",
    month: "long",
    day: "numeric",
    hour: "numeric",
    minute: "numeric",
    hour12: true,
  });
};

// 더 많은 게시글 로드하는 함수
const loadMore = () => {
  visibleCount.value += 5;
};

const handleLike = async (index) => {
  const postId = getPostIdFromRef(index);
  if (postId) {
    try {
      await api.likePost(postId); // API 호출
    } catch (error) {
      console.error("Failed to like the post:", error);
    }
  }
};


const handleDelete = async (index) => {
  if (!confirm('삭제할까요?')) return;
  await api.delete(getPostIdFromRef(index));
  reloadPosts();  // 페이지 새로 고침 추가
};

const reloadPosts = async () => {
  await fetchBoardPosts(); // 게시글을 다시 불러오는 함수 호출
};

// 댓글 추가 처리 함수
const handleReply = async (index) => {
  const postId = getPostIdFromRef(index); // hidden input에서 postId를 가져옴
  if (postId) {
    try {
      const token = localStorage.getItem("auth"); // 로컬 스토리지에서 토큰을 가져옴
      const response = await axios.post(`/api/board/reply/${postId}`, {
        content: newReply.value,
      }, {
        headers: {
          Authorization: `Bearer ${token}`, // Authorization 헤더에 토큰 추가
        },
      });
      if (response.status === 200) {
        newReply.value = ""; // 댓글 추가 후 입력란 초기화
        alert("댓글이 달렸습니다.");
        fetchBoardPosts(); // 댓글 추가 후 게시물 목록 새로고침
      } else {
        console.error("Failed to add a reply");
      }
    } catch (error) {
      console.error("Error adding a reply:", error);
    }
  }
};

// 컴포넌트가 마운트될 때 게시글 목록을 가져옴
onMounted(() => {
  fetchBoardPosts();
});
</script>

<template>
  <div>
    <div
        v-for="(post, index) in visiblePosts"
        :key="post.postId"
        class="feeds card mb-5 mb-xxl-8"
    >
      <div class="card-body pb-0">
        <div class="d-flex align-items-center">
          <div class="symbol symbol-45px me-5">
            <img :src="require('@/assets/media/avatars/300-25.jpg')" alt=""/>
          </div>
          <div class="d-flex flex-column">
            <p class="name text-gray-800 mb-1 fw-bolder">{{ post.name }}</p>
            <span class="text-gray-500 fw-semibold">
              {{ formatDate(post.createdDate) }}
            </span>
          </div>
        </div>
        <div class="pt-5">
          <template v-if="auth.id == post.authorId">
            <div class="optionBtn text-end">
              <button
                  type="button"
                  class="btn btn-secondary btn-icon"
                  aria-label="Edit"
                  @click="handleReply(index)"
              >
                <i class="ai-edit-alt"></i>
              </button>

              <button
                  type="button"
                  class="btn btn-outline-danger btn-icon"
                  aria-label="Delete"
                  @click="handleDelete(index)"
              >
                <i class="ai-trash"></i>
              </button>
            </div>
          </template>
          <h3 class="fw-normal subject mb-3">{{ post.title }}</h3>
          <h5 class="fw-normal content mb-3">{{ post.content }}</h5>
          <div v-if="post.boardAttachFileList && post.boardAttachFileList.length > 0">
            <div v-for="attachment in post.boardAttachFileList" :key="attachment.postId" class="mb-3">
              <img
                  :src="`/api/board/file/${encodeURIComponent(attachment.renamedFilename)}`"
                  alt="Post Image"
                  class="post-image"
              />
            </div>
          </div>
          <input type="hidden" :value="post.postId" :ref="`${post.postId}`">
          <div class="d-flex align-items-center">
            <a class="btn btn-sm btn-color-muted btn-active-light-primary fw-bolder fs-6 py-1 px-2 me-4">
              <i class="ai-message fs-2"></i>{{ post.commentCount }}
            </a>
            <a
                class="btn btn-sm btn-color-muted btn-active-light-danger fw-bold fs-6 py-1 px-2"
                @click="handleLike(index)"
            >
              <i class="ai-heart fs-2"></i>{{ post.likesCount }}
            </a>
          </div>
        </div>
        <div class="separator pt-5 mb-3"></div>
        <form class="reply position-relative pb-3">
          <textarea
              v-model="newReply"
              data-kt-autosize="true"
              class="form-control border-0 p-0 pe-10 resize-none min-h-25px"
              rows="1"
              placeholder="댓글"
          ></textarea>
          <div class="position-absolute top-0 end-0 me-n5">
            <span class="btn btn-icon btn-sm btn-active-color-primary ps-0">
              <i class="ai-edit-alt" @click="handleReply(index)"></i>
            </span>
          </div>
        </form>
      </div>
    </div>
  </div>
  <button v-if="!allPostsLoaded" class="moreBtn text-center" @click="loadMore">
    <span class="indicator-label">더보기</span>
    <span class="indicator-progress">Loading...
      <span class="spinner-border spinner-border-sm align-middle ms-2"></span>
    </span>
  </button>
  <input type="hidden" :value="postType"/>
</template>

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
  margin-left: 10px;
  width: 700px;
  height: 50px;
  border: none;
  border-radius: 20px;
  cursor: pointer; /* Add cursor pointer for better UX */
}

.moreBtn:hover {
  background-color: #438c74;
  color: white;
}

.feeds {
  width: 700px;
  border-radius: 30px;
  border: none;
  margin-left: 10px;
}

.post-image {
  max-width: 600px;
  max-height: 600px;
}

.optionBtn {
  cursor: pointer;
  margin-top: -100px;
}

.subject {
  margin-top: 50px;
}

.content {
  margin-top: -20px;
}

.btn {
  background-color: white;
}
</style>