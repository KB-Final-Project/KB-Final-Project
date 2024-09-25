import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import axios from 'axios';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'aos/dist/aos.css';
import 'lightgallery/css/lightgallery.css';

// Import JS libraries
import 'bootstrap';
import SmoothScroll from 'smooth-scroll';
import AOS from 'aos';
import lightGallery from 'lightgallery';

// Axios 기본 URL 설정 (Spring 서버 API URL)
axios.defaults.baseURL = 'http://localhost:8080';

// Initialize plugins
new SmoothScroll('a[href*="#"]');
AOS.init();

const app = createApp(App);
app.config.globalProperties.$lightGallery = lightGallery;
app.config.globalProperties.$axios = axios;  // 전역에서 axios 사용 가능하게 설정

app.use(router).mount('#app');
