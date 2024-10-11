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
              <h3>{{myInfo.name}}</h3>
            </div>
            <div class="button-group position-absolute top-0 end-0 mt-5 me-5">
              <button class="cancel me-3" @click="cancelPost">취소</button>
              <button class="submit" @click="createBoardPost">작성</button>
            </div>
          </div>
          <div class="pt-3">
            <p class="fs-6 fw-normal">
              <input v-model="postType" placeholder="Type" />
              <input v-model="postTitle" placeholder="Title" />
              <textarea v-model="postContent" class="writer" placeholder="무슨 일이 일어나고 있나요?"></textarea>
            </p>
            <div class="filebox">
              <label for="file">
                <i class="ai-image"></i>
              </label>
              <input type="file" id="file" @change="handleFileChange" style="display: none;" />
              <br /><br />
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

const authStore = useAuthStore();
const postType = ref('');
const postTitle = ref('');
const postContent = ref('');
const selectedFiles = ref([]);

const myInfo = reactive({
  name: '이사벨라',
  email: 'abcd@gmail.com',
  propensity: 1, // 안정형
});


onMounted(()=>{
  getMyInfo();
})

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
  formData.append('type', postType.value);
  formData.append('title', postTitle.value);
  formData.append('content', postContent.value);
  
  const token = authStore.getToken();
  
  selectedFiles.value.forEach(file => {
    formData.append('files', file);
  });

  try {
  // 게시판 정보를 가져오는 API 호출 (여기서 bno를 가져옵니다)
  const boardResponse = await axios.get('http://localhost:8080/api/board/{게시판_id}', {
    headers: {
      'Authorization': `Bearer ${token}`,
    },
  });

  // const bno = boardResponse.data.bno; // 여기서 bno를 가져옵니다.
  const bno = 1;

  // 이제 bno를 사용하여 board_post를 생성합니다.
  const postResponse = await axios.post('http://localhost:8080/api/board/post', {
    bno: bno,  // 가져온 bno 사용
    title: postTitle.value,
    content: postContent.value,
    type: postType.value,
    // 기타 필요한 필드들
  }, {
    headers: {
      'Authorization': `Bearer ${token}`,
    },
  });

    console.log('Post created:', postResponse.data);
  } catch (error) {
    console.error('Error creating post:', error);
  }
}


function cancelPost() {
  postType.value = '';
  postTitle.value = '';
  postContent.value = '';
  selectedFiles.value = [];
}
</script>
<style scoped>
.filebox {
  display: inline-block;
  cursor: pointer;
  margin-left: 20px;
}

.ai-image {
  font-size: 24px;
  cursor: pointer;
  color: rgba(67, 140, 116, 1);
}

.submit{
  width: 100px;
  height: 30px;
  border-radius: 20px;
  border: none;
  background-color: rgb(67, 140, 116);
  color: white;
  font-size: 15px;
}
.cancel{
  width: 100px;
  height: 30px;
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
}
</style>

