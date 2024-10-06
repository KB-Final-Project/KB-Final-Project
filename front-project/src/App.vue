<template>
  <div id="app">
    <AppHeader v-if="!$route.meta.hideHeaderFooter" />
    <router-view id="mainContent" :class="{ 'no-padding': $route.meta.hidePadding }"></router-view>
    <AppFooter v-if="!$route.meta.hideHeaderFooter" />
  </div>
</template>

<script>
import AppHeader from './components/AppHeader.vue';
import AppFooter from './components/AppFooter.vue';
import {useRouter} from "vue-router";
import {onMounted} from "vue";

export default {
  components: {
    AppHeader,
    AppFooter,
  },
  setup() {
    const router = useRouter();

    // 페이지가 변경될 때마다 스크롤을 맨 위로 이동
    onMounted(() => {
      router.afterEach(() => {
        window.scrollTo(0, 0);
      });
    });
  },
};
</script>

<style>
/* 전역 스타일 적용 */
@import './assets/css/navbar.css';
@import './assets/css/theme.css';
@import './assets/css/theme.min.css';
@import './assets/css/style.bundle';

#mainContent {
  padding-top: 80px;
}

.no-padding {
  padding-top: 0 !important;
}
</style>
