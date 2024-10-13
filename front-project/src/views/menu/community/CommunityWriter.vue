<template>
  <div class="container">
    <div class="col">
      <form @submit.prevent="submit">
        <div class="feeds card mb-5 mb-xxl-8">
          <div class="card-body pb-0">
            <div class="d-flex align-items-center">
              <div class="button-group position-absolute top-0 end-0 mt-3 me-5">
                <button type="button" class="cancel me-3" @click="cancelPost">취소</button>
                <button type="submit" class="submit">작성</button>
              </div>
            </div>
            <div>
              <input v-model="postTitle" id="title" class="subject" placeholder="제목을 입력하세요" />
              <textarea v-model="postContent" id="content" class="writer" placeholder="무슨 일이 일어나고 있나요?"></textarea>
              <div class="filebox">
                <label for="files" class="file-label">
                  <i class="ai-image"></i>
                </label>
                <input type="file" id="files" ref="files" @change="handleFileChange" style="display: none;" />
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>


<style scoped>
.filebox {
  display: inline-block;
  cursor: pointer;
  margin-left: 20px;
  margin-bottom: 20px;
}

.ai-image {
  font-size: 20px;
  cursor: pointer;
  color: rgba(67, 140, 116, 1);
  margin-left: -10px;
}

.submit{
  width: 80px;
  height: 30px;
  border-radius: 20px;
  border: none;
  background-color: rgb(67, 140, 116);
  color: white;
  font-size: 12px;
}
.cancel{
  width: 80px;
  height: 30px;
  border-radius: 20px;
  background-color: lightgrey;
  color: white;
  border: none;
  font-size: 12px;
}


.feeds{
  width: 600px;
  height: 250px;
  border-radius: 30px;
  border: none;
  background-color: white;
  padding: 10px;
}
.writer{
  text-align: start;
  width: 550px;
  height: 100px;
  border-radius: 20px;
  padding: 20px;
  border: 1px solid lightgrey;
  resize: none;
  font-size: 15px;
  margin-top: 10px;
  margin-bottom: 10px;
  margin-left: -10px;
}

.subject{
  text-align: start;
  width: 550px;
  height: 30px;
  border-radius: 20px;
  margin-top: 20px;
  padding: 20px;
  border: 1px solid lightgrey;
  resize: none;
  font-size: 15px;
  margin-left: -10px;
}

</style>
<script setup>
import axios from "axios";
import { ref, onMounted, reactive } from 'vue';
import { useAuthStore } from '@/stores/auth'; // 스토어 가져오기
import boardApi from '@/api/boardApi';
import { useRoute, useRouter } from 'vue-router'; // Vue Router를 사용하여 URL 파라미터 접근

const authStore = useAuthStore();
const auth = useAuthStore();
const postTitle = ref('');
const postContent = ref('');
const files = ref(null); // Ref for the file input
const selectedFiles = ref([]);


const router = useRouter(); // Initialize router

const article = reactive({
  memberId: auth.id,
  title: '',
  content: '',
  files: null,
});

const myInfo = reactive({
  name: '이사벨라',
  email: 'abcd@gmail.com',
  propensity: 1, // 안정형
});

const route = useRoute();
const postType = ref(route.path.split('/').pop()); // URL의 마지막 부분에서 type을 가져

onMounted(() => {
  getMyInfo();
});

function handleFileChange(event) {
  selectedFiles.value = Array.from(event.target.files);
}

const submit = async () => {
  if (!confirm('등록할까요?')) return;

  // 사용자가 확인 버튼을 눌렀을 때 게시물 등록 함수 호출
  await createBoardPost();

  // Assign form data to article
  article.title = postTitle.value;
  article.content = postContent.value;

  // Handle file selection
  if (files.value && files.value.files.length > 0) {
    article.files = files.value.files;
  } else {
    article.files = null; // Or handle accordingly if no files are selected
  }

  try {
    await boardApi.create(article);
    router.push('/community/stability');
  } catch (error) {
    console.error('Error creating board post:', error);
    // Optionally, display an error message to the user
  }
};

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

function cancelPost() {
  postTitle.value = '';
  postContent.value = '';
  if (files.value) {
    files.value.value = ''; // Clear the file input
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
</script>
