<template>
  <div class="content fs-6 d-flex flex-column flex-column-fluid" id="kt_content">
    <div class="post fs-6 d-flex flex-column-fluid" id="kt_post">
      <div class="container-xxl">
        <!-- 왼쪽에 Community Panel -->
        <div class="col-xl-3">
          <CommunityPanel />
        </div>

        <!-- 중앙에 CommunityWriter와 router-view -->
        <div class="col-xl-6">
          <!-- CommunityWriter 페이지 -->
          <CommunityWriter />

          <!-- 아래에 router-view -->
          <router-view></router-view>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CommunityPanel from "@/views/menu/community/CommunityPanel.vue";
import CommunityWriter from "@/views/menu/community/CommunityWriter.vue";  // CommunityWriter 컴포넌트 추가

export default {
  name: 'Community',
  components: {
    CommunityPanel,
    CommunityWriter
  },
};
</script>

<style scoped>
.container-xxl {
  display: flex;
  justify-content: center;
  gap: 20px;
}

.content{
  background-color: rgba(249, 249, 249, 1);
}

/* 패널 크기 */
.col-xl-3 {
  width: 350px; /* CommunityPanel의 고정 너비 */
}

/* 메인 콘텐츠 크기 */
.col-xl-6 {
  flex-grow: 1; /* 나머지 공간을 차지하게 설정 */
  padding-left: 20px;
}
</style>
