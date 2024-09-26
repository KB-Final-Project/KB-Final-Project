<script setup>
import { ref } from 'vue';
import MyPagePanel from './MyPagePanel.vue';
import MyPageContent from './MyPageContent.vue';
import MyPageSettings from './MyPageSetting.vue';
import MyPagePosts from './MyPagePosts.vue';
import MyPageWithdraw from './MyPageWithdraw.vue';
import MyPageWarning from "@/views/myPage/MyPageWarning.vue";

const selectedPage = ref('warning');
</script>

<template>
  <div class="bc">
    <br><br>
    <div class="container">
      <MyPagePanel @update-page="selectedPage = $event" />
      <div class="content">
        <MyPageWarning v-if="selectedPage === 'warning'"/>
        <MyPageContent v-if="selectedPage === 'profile'" />
        <MyPageSettings v-if="selectedPage === 'settings'" />
        <MyPagePosts v-if="selectedPage === 'posts'" />
        <MyPageWithdraw v-if="selectedPage === 'withdraw'" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.bc {
  background-color: rgba(247, 249, 252, 1);
}

.container {
  display: flex;
}

.content {
  flex: 1;
  padding: 20px;
}
</style>
