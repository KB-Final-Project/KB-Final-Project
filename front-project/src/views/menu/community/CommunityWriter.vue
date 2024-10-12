<template>
  <div class="container">
    <div class="col">
      <div class="feeds card mb-5 mb-xxl-8">
        <div class="card-body pb-0">
          <div class="d-flex align-items-center">
            <div class="symbol symbol-45px me-5">
              <img src="/img/imsi.png" />
            </div>
            <div class="d-flex flex-column">
              <h3 class="user-name">{{ myInfo.name }}</h3>
              <p class="user-email">{{ myInfo.email }}</p>
            </div>
            <div class="button-group position-absolute top-0 end-0 mt-5 me-5">
              <button class="cancel me-3" @click="cancelPost">취소</button>
              <button class="submit" @click="createBoardPost">작성</button>
            </div>
          </div>
          <div class="pt-3">
            <input v-model="postTitle" class="input-title" placeholder="제목을 입력하세요" />
            <textarea v-model="postContent" class="writer" placeholder="무슨 일이 일어나고 있나요?"></textarea>
            <div class="filebox">
              <label for="file" class="file-label">
                <i class="ai-image"></i> 파일 선택
              </label>
              <input type="file" id="file" @change="handleFileChange" style="display: none;" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue';
import { useAuthStore } from '@/stores/auth'; // 스토어 가져오기
import axios from 'axios';
import { useRoute } from 'vue-router'; // Vue Router를 사용하여 URL 파라미터 접근

const authStore = useAuthStore();
const postTitle = ref('');
const postContent = ref('');
const selectedFiles = ref([]);

const myInfo = reactive({
  name: '이사벨라',
  email: 'abcd@gmail.com',
  propensity: 1, // 안정형
});

// URL에서 type 가져오기
const route = useRoute();
const postType = ref(route.path.split('/').pop()); // URL의 마지막 부분에서 type을 가져옴

onMounted(() => {
  getMyInfo();
});

function handleFileChange(event) {
  selectedFiles.value = Array.from(event.target.files);
}

async function getMyInfo() {
  const authValue = localStorage.getItem('auth');

  if (authValue) {
    try {
      const authData = JSON.parse(authValue);
      if (authData.email) {
        myInfo.name = authData.name;
        myInfo.email = authData.email; // email 값 추출
      } else {
        console.log('Email not found in auth data');
      }
    } catch (error) {
      console.error('Error parsing auth data:', error);
    }
  } else {
    console.log('Auth value not found');
  }
}

async function createBoardPost() {
  const formData = new FormData();
  formData.append('title', postTitle.value);
  formData.append('content', postContent.value);

  const token = authStore.getToken();

  selectedFiles.value.forEach(file => {
    formData.append('files', file);
  });

  // type에 따라 bno를 설정
  let bno;

  switch (postType.value) {
    case 'stability':
      bno = 1;
      break;
    case 'neutral':
      bno = 2;
      break;
    case 'activeInvestment':
      bno = 3;
      break;
    case 'aggressiveInvestment':
      bno = 4;
      break;
    default:
      console.error('Invalid type');
      return; // 잘못된 type인 경우 함수 종료
  }

  // bno와 type을 formData에 추가
  formData.append('bno', bno);
  formData.append('type', postType.value);

  try {
    const postResponse = await axios.post(`http://localhost:8080/api/board/${postType.value}`, formData, {
      headers: {
        'Authorization': `Bearer ${token}`,
      },
    });

    console.log('Post created:', postResponse.data);
  } catch (error) {
    console.error('Error creating post:', error);
    console.error('Error creating post:', error.response ? error.response.data : error.message);
  }
}

function cancelPost() {
  postTitle.value = '';
  postContent.value = '';
  selectedFiles.value = [];
}
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: auto;
}

.user-name {
  font-size: 1.5rem;
  font-weight: bold;
}

.user-email {
  font-size: 0.9rem;
  color: grey;
}

.input-title {
  width: 100%;
  padding: 10px;
  margin-top: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

.writer {
  text-align: start;
  margin-top: 10px;
  width: 100%;
  height: 180px;
  border-radius: 5px;
  padding: 10px;
  border: 1px solid lightgrey;
  resize: none;
  font-size: 16px;
}

.filebox {
  display: inline-block;
  cursor: pointer;
  margin-top: 10px;
}

.file-label {
  cursor: pointer;
  color: rgba(67, 140, 116, 1);
}

.ai-image {
  font-size: 24px;
  color: rgba(67, 140, 116, 1);
}

.submit {
  width: 100px;
  height: 30px;
  border-radius: 20px;
  border: none;
  background-color: rgb(67, 140, 116);
  color: white;
  font-size: 15px;
  margin-top: 10px;
}

.cancel {
  width: 100px;
  height: 30px;
  border-radius: 20px;
  border: none;
  font-size: 15px;
  margin-top: 10px;
}

.card {
  border: none;
}

.feeds {
  border-radius: 20px;
  padding: 20px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}
</style>
