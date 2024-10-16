<script setup>
import {ref, onMounted, computed} from "vue";
import axios from "axios";
import {useAuthStore} from "@/stores/auth";
import api from '@/api/boardApi';

const postType = ref(1); // 게시판 타입 (예: 1: 안정형)
const posts = ref([]); // 게시글 목록
const visibleCount = ref(5); // 보여질 게시글 수
const newReply = ref({}); // 댓글 입력을 위한 ref
const postRefs = ref([]); // hidden input 참조 배열
const replies = ref({}); // 각 게시글의 댓글을 저장할 객체


const props = defineProps({ username: String });

const avatar = `/api/member/${props.username}/avatar`;
const auth = useAuthStore();

// 게시글 목록을 가져오는 함수
const fetchReplies = async (postId) => {
  try {
    const response = await api.getReplies(postId); // 각 ID에 대해 개별적으로 요청
    replies.value[postId] = response; // 댓글 목록을 postId를 키로 하는 객체에 저장
  } catch (error) {
    console.error("Error fetching replies:", error);
  }
};

// 게시글 목록을 가져오는 함수 수정
const fetchBoardPosts = async () => {
  try {
    const response = await axios.get(`/api/board/${postType.value}/posts`);
    posts.value = response.data.postList;
    // 게시글 목록을 가져온 후 각 게시글의 postId에 대해 fetchReplies 호출
    for (const post of posts.value) {
      await fetchReplies(post.postId); // 각 ID에 대해 개별적으로 댓글 가져오기
    }

    console.log("Fetched posts:", posts.value);
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

// const handleReply = async (index) => {
//   const token = localStorage.getItem('token');
//   try {
//     const requestBody = {
//       postId: posts.value[index].postId,
//       writer: auth.userId,
//       content: newReply.value[index],
//     };
//     // Changed to POST for submitting replies
//     const response = await axios.post(`/api/board/replyPlus/${posts.value[index].postId}`, requestBody, {
//       headers: {
//         Authorization: `Bearer ${token}`, // Authorization 헤더에 JWT 토큰 추가
//       },
//     })
//     newReply.value[posts.value[index].postId] = ""; // Clear the reply input
//   } catch (error) {
//     console.error("Error adding reply:", error);
//   }
// };


// const handleReply = async (postId) => {
//   if (!newReply.value[postId] || newReply.value[postId].trim() === "") return; // 댓글 내용이 비어있으면 리턴
//   try {
//     const response = await api.createReply(postId, { content: newReply.value[postId] }); // API 호출
//     replies.value[postId].push(response); // 새 댓글을 해당 게시글의 댓글 목록에 추가
//     newReply.value[postId] = ""; // 해당 게시글의 댓글 입력 필드 초기화
//   } catch (error) {
//     console.error("Error adding reply:", error);
//   }
// };

console.log('제발' + postRefs.value);
const handleDelete = async (index) => {
  if (!confirm('삭제할까요?')) return;

  await api.delete(posts.value[index].postId);

  reloadPosts();  // 페이지 새로 고침 추가
};

const reloadPosts = async () => {
  await fetchBoardPosts(); // 게시글을 다시 불러오는 함수 호출
};


// 컴포넌트가 마운트될 때 게시글 목록을 가져옴
onMounted(() => {
  fetchBoardPosts();
});
</script>

<template>
  <div class="bc">
    <div
        v-for="(post, index) in visiblePosts"
        :key="post.postId"
        class="feeds card mb-5 mb-xxl-8"
    >
      <div class="card-body pb-0">
        <div class="d-flex align-items-center">
          <div class="symbol symbol-45px me-5">
            <img :src="avatar" class="avatar avatar-sm" /><br/>
          </div>
          <div class="d-flex flex-column">
            <p class="name text-gray-800 mb-1 fw-bolder">{{ post.authorId }}</p>
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
          <input type="hidden" :value="post.postId" :ref="postRefs">
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
        <!-- 댓글 목록 표시 -->
        <div class="replies">
          <div v-for="reply in replies[post.postId] || []" :key="reply.rno" class="reply">
            <h5>{{ reply.content }}</h5>
            <h5>{{ formatDate(reply.createDate) }}</h5>
          </div>
        </div>
        <!-- 댓글 입력란 -->
        <form class="reply position-relative pb-3" @submit.prevent="handleReply(index)">
          <input
              v-model="newReply[index]"
              data-kt-autosize="true"
              class="form-control border-0 p-0 pe-10 resize-none min-h-25px"
              placeholder="댓글"
          />
          <div class="position-absolute top-0 end-0 me-n5">
            <button class="btn btn-icon btn-sm btn-active-color-primary ps-0" type="submit">
              <i class="ai-edit-alt"></i>
            </button>
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
.bc{
  font-family: J3;
}
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

.replies {

  margin-top: 20px; /* 댓글 목록 상단 여백 추가 */
}

.reply {
  display:flex;
  justify-content: space-between;
  padding: 10px; /* 댓글 여백 추가 */
  border-bottom: 1px solid lightgrey;
}
</style>
