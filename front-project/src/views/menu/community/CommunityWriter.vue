<template>
  <div class="container">
    <div class="col">
      <form @submit.prevent="submit">
        <div class="feeds card mb-5 mb-xxl-8">
          <div class="card-body pb-0">
            <div class="d-flex align-items-center">
              <div class="symbol symbol-55pxpx me-5">
                <img src="/img/imsi.png" alt="User Image" />
              </div>
              <div class="d-flex flex-column">
                <h3 class="user-name">{{ myInfo.name }}</h3>
              </div>
              <div class="button-group position-absolute top-0 end-0 mt-3 me-5">
                <button type="button" class="cancel me-3" @click="cancelPost">취소</button>
                <button type="submit" class="submit">작성</button>
              </div>
            </div>
            <div class="pt-3">
              <input v-model="postTitle" id="title" class="subject" placeholder="제목을 입력하세요" />
              <textarea v-model="postContent" id="content" class="writer" placeholder="무슨 일이 일어나고 있나요?"></textarea>
              <div class="filebox">
                <label for="files" class="file-label">
                  <i class="ai-image"></i>
                </label>
                <input type="file" id="files" ref="files" style="display: none;" />
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
  font-size: 24px;
  cursor: pointer;
  color: rgba(67, 140, 116, 1);
}

.submit{
  width: 100px;
  height: 50px;
  border-radius: 20px;
  border: none;
  background-color: rgb(67, 140, 116);
  color: white;
  font-size: 15px;
}
.cancel{
  width: 100px;
  height: 50px;
  border-radius: 20px;
  border: none;
  font-size: 15px;
}

.card{
  border: none;
}

.feeds{
  border-radius: 30px;
}
.writer{
  text-align: start;
  margin-left: 15px;
  width: 800px;
  height: 180px;
  border-radius: 20px;
  padding: 20px;
  border: 1px solid lightgrey;
  resize: none;
  font-size: 20px;
  margin-top: 10px;
  margin-bottom: 20px;
}

.subject{
  text-align: start;
  margin-left: 15px;
  width: 800px;
  height: 60px;
  border-radius: 20px;
  padding: 20px;
  border: 1px solid lightgrey;
  resize: none;
  font-size: 20px;
}

</style>

<script setup>
import { ref, onMounted, reactive } from 'vue';
import { useAuthStore } from '@/stores/auth'; // 스토어 가져오기
import boardApi from '@/api/boardApi';
import { useRoute, useRouter } from 'vue-router'; // Vue Router를 사용하여 URL 파라미터 접근

const auth = useAuthStore();
const postTitle = ref('');
const postContent = ref('');
const files = ref(null); // Ref for the file input

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

onMounted(() => {
  getMyInfo();
});

const submit = async () => {
  if (!confirm('등록할까요?')) return;

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
</script>
