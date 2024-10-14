<script setup>
import { ref, onMounted, computed } from "vue";
import axios from "axios";

const postType = ref(1); // 게시판 타입 (예: 1: 안정형)
const posts = ref([]); // 게시글 목록
const visibleCount = ref(5); // 보여질 게시글 수
const newReply = ref(""); // 댓글 입력을 위한 ref

// 게시글 목록을 가져오는 함수
const fetchBoardPosts = async () => {
  try {
    const response = await axios.get(`/api/board/${postType.value}/posts`); // 토큰 없이 게시글 목록 가져오기
    posts.value = response.data.postList; // 게시글 목록 설정
    console.log('Fetched posts:', posts.value); // 로깅
  } catch (error) {
    console.error("Error fetching posts:", error); // 에러 처리
  }
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
  return date.toLocaleString("en-US", {
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

// 좋아요 버튼 클릭 처리 함수
const handleLike = async (postId, post) => {
  try {
    const response = await axios.post(`/api/board/${postId}/like`); // 토큰 없이 좋아요 추가
    if (response.status === 200) {
      post.likesCount++; // 좋아요 수 증가
    } else {
      console.error("Failed to like the post");
    }
  } catch (error) {
    console.error("Error liking the post:", error);
  }
};

// 게시글 삭제 처리 함수
const handleDelete = async (postId) => {
  const confirmed = confirm('정말로 이 게시글을 삭제하시겠습니까?');
  if (!confirmed) {
    return; // 사용자가 취소한 경우 삭제 중단
  }

  try {
    const response = await axios.delete(`/api/board/${postId}`); // 토큰 없이 게시글 삭제
    if (response.status === 200) {
      posts.value = posts.value.filter((post) => post.postId !== postId); // 삭제 후 게시글 목록 갱신
    } else {
      console.error("Failed to delete the post");
    }
  } catch (error) {
    console.error("Error deleting the post:", error);
  }
};

// 댓글 추가 처리 함수
const handleReply = async (postId) => {
  try {
    const response = await axios.post(`/api/board/reply/${postId}`, {
      content: newReply.value,
    }); // 토큰 없이 댓글 추가
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
};

// 컴포넌트가 마운트될 때 게시글 목록을 가져옴
onMounted(() => {
  fetchBoardPosts();
});
</script>

<template>
  <div>
    <div
        v-for="post in visiblePosts"
        :key="post.postId"
        class="feeds card mb-5 mb-xxl-8"
    >
      <div class="card-body pb-0">
        <div class="d-flex align-items-center">
          <div class="symbol symbol-45px me-5">
            <img :src="require('@/assets/media/avatars/300-25.jpg')" alt="" />
          </div>
          <div class="d-flex flex-column">
            <p class="name text-gray-800 mb-1 fw-bolder">{{ post.authorId }}</p>
            <span class="text-gray-500 fw-semibold">
              {{ formatDate(post.createdDate) }}
            </span>
          </div>
        </div>
        <div class="pt-5">
          <div class="optionBtn text-end">
            <button
                type="button"
                class="btn btn-secondary btn-icon"
                aria-label="Edit"
                @click="handleReply(post.postId)"
            >
              <i class="ai-edit-alt"></i>
            </button>

            <button
                type="button"
                class="btn btn-outline-danger btn-icon"
                aria-label="Delete"
                @click="handleDelete(post.postId)"
            >
              <i class="ai-trash"></i>
            </button>
          </div>
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
          <div class="d-flex align-items-center">
            <a href="#" class="btn btn-sm btn-color-muted btn-active-light-primary fw-bolder fs-6 py-1 px-2 me-4">
              <i class="ai-message fs-2"></i>{{ post.commentCount }}
            </a>
            <a
                href="#"
                class="btn btn-sm btn-color-muted btn-active-light-danger fw-bold fs-6 py-1 px-2"
                @click="handleLike(post.postId, post)"
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
              <i class="ai-edit-alt" @click="handleReply(post.postId)"></i>
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
  <input type="hidden" :value="postType" />
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
