<template>
  <header class="appHeader navbar navbar-expand-lg fixed-top bg-light">
    <div class="container">
      <router-link to="/" class="navbar-brand pe-sm-3">
        <span class="text-primary flex-shrink-0 me-2">
<<<<<<< Updated upstream
          <svg
            version="1.1"
            width="35"
            height="32"
            viewBox="0 0 36 33"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              fill="currentColor"
              d="M35.6,29c-1.1,3.4-5.4,4.4-7.9,1.9c-2.3-2.2-6.1-3.7-9.4-3.7c-3.1,0-7.5,1.8-10,4.1c-2.2,2-5.8,1.5-7.3-1.1c-1-1.8-1.2-4.1,0-6.2l0.6-1.1l0,0c0.6-0.7,4.4-5.2,12.5-5.7c0.5,1.8,2,3.1,3.9,3.1c2.2,0,4.1-1.9,4.1-4.2s-1.8-4.2-4.1-4.2c-2,0-3.6,1.4-4,3.3H7.7c-0.8,0-1.3-0.9-0.9-1.6l5.6-9.8c2.5-4.5,8.8-4.5,11.3,0L35.1,24C36,25.7,36.1,27.5,35.6,29z"
            ></path>
          </svg> </span
        >iNVeTI
=======
           </span><img class="invetiLogo" src="/img/inveti.png">
>>>>>>> Stashed changes
      </router-link>
      <button
        class="navbar-toggler ms-sm-3"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarNav"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <nav class="collapse navbar-collapse" id="navbarNav">
<<<<<<< Updated upstream
        <ul
          class="navbar-nav navbar-nav-scroll me-auto"
          style="--ar-scroll-height: 520px"
        >
          <li
            v-for="(item, index) in menuItems"
            :key="index"
            class="nav-item"
            :class="{ dropdown: item.subItems }"
          >
            <a
              v-if="!item.subItems"
              class="nav-link"
              :class="{ active: item.active }"
              :href="item.href"
              >{{ item.text }}</a
            >
            <template v-else>
              <a
                class="nav-link dropdown-toggle"
                :class="{ active: item.active }"
                href="#"
                data-bs-toggle="dropdown"
                :data-bs-auto-close="item.autoClose"
                aria-expanded="false"
                >{{ item.text }}</a
              >
              <ul
                class="dropdown-menu"
                :class="{ 'overflow-hidden p-0': item.megaMenu }"
              >
                <template v-if="item.megaMenu">
                  <div class="d-lg-flex">
                    <div
                      v-for="(column, colIndex) in item.subItems"
                      :key="colIndex"
                      class="mega-dropdown-column pt-1 pt-lg-3 pb-lg-4"
                    >
                      <ul class="list-unstyled mb-0">
                        <li
                          v-for="(subItem, subIndex) in column"
                          :key="subIndex"
                        >
                          <a class="dropdown-item" :href="subItem.href">{{
                            subItem.text
                          }}</a>
                          <span
                            v-if="subItem.bgImage"
                            class="mega-dropdown-column position-absolute top-0 end-0 h-100 bg-size-cover bg-repeat-0 rounded-3 rounded-start-0"
                            :style="{
=======
        <ul  class="navbar-nav navbar-nav-scroll me-auto" style="--ar-scroll-height: 520px;">
          <li v-for="(item, index) in menuItems" :key="index" class="nav-item" :class="{ dropdown: item.subItems }"
              @mouseenter="hoverDropdown(index, true)"
              @mouseleave="hoverDropdown(index, false)">
            <a style="font-size:18px;" v-if="!item.subItems" class="nav-link" :class="{ active: item.active }" :href="item.href">{{ item.text
              }}</a>
            <template v-else>
              <a style="font-size:18px;" class="nav-link dropdown-toggle" :class="{ active: item.active }" href="#" data-bs-toggle="dropdown"
                 :data-bs-auto-close="item.autoClose" aria-expanded="false">
                {{ item.text }}
                <i :class="dropdownStates[index] ? 'ai-chevron-up' : 'ai-chevron-down'"></i>
              </a>
              <ul class="dropdown-menu" :class="{ 'overflow-hidden p-0': item.megaMenu }">
                <template  v-if="item.megaMenu">
                  <div class="d-lg-flex">
                    <div v-for="(column, colIndex) in item.subItems" :key="colIndex"
                         class="mega-dropdown-column pt-1 pt-lg-3 pb-lg-4">
                      <ul class="list-unstyled mb-0">
                        <li v-for="(subItem, subIndex) in column" :key="subIndex">
                          <a style="font-size:17px;" class="dropdown-item" :href="subItem.href">{{
                              subItem.text
                            }}</a>
                          <span v-if="subItem.bgImage"
                                class="mega-dropdown-column position-absolute top-0 end-0 h-100 bg-size-cover bg-repeat-0 rounded-3 rounded-start-0"
                                :style="{
>>>>>>> Stashed changes
                              backgroundImage: `url(${subItem.bgImage})`,
                            }"
                          ></span>
                        </li>
                      </ul>
                    </div>
                    <div
                      class="mega-dropdown-column position-relative border-start zindex-3"
                    ></div>
                  </div>
                </template>
                <template v-else>
<<<<<<< Updated upstream
                  <li
                    v-for="(subItem, subIndex) in item.subItems"
                    :key="subIndex"
                    :class="{ dropdown: subItem.subItems }"
                  >
                    <template v-if="!subItem.subItems">
                      <a class="dropdown-item" :href="subItem.href">{{
                        subItem.text
                      }}</a>
                    </template>
                    <template v-else>
                      <a
                        class="dropdown-item dropdown-toggle"
                        href="#"
                        data-bs-toggle="dropdown"
                        aria-expanded="false"
                        >{{ subItem.text }}</a
                      >
                      <ul class="dropdown-menu">
                        <li
                          v-for="(grandChild, grandIndex) in subItem.subItems"
                          :key="grandIndex"
                        >
                          <a class="dropdown-item" :href="grandChild.href">{{
                            grandChild.text
                          }}</a>
=======
                  <li v-for="(subItem, subIndex) in item.subItems" :key="subIndex"
                      :class="{ dropdown: subItem.subItems }">
                    <template v-if="!subItem.subItems">
                      <a style="font-size:17px;" class="dropdown-item" :href="subItem.href">{{
                          subItem.text
                        }}</a>
                    </template>
                    <template v-else>
                      <a style="font-size:17px;" class="dropdown-item dropdown-toggle" href="#" data-bs-toggle="dropdown"
                         aria-expanded="false">{{ subItem.text }}</a>
                      <ul class="dropdown-menu" style=";">
                        <li v-for="(grandChild, grandIndex) in subItem.subItems" :key="grandIndex">
                          <a style="font-size:17px;" class="dropdown-item" :href="grandChild.href">{{
                              grandChild.text
                            }}</a>
>>>>>>> Stashed changes
                        </li>
                      </ul>
                    </template>
                  </li>
                </template>
              </ul>
            </template>
          </li>
        </ul>

        <!-- 로그인 버튼을 투자성향과 동일하게 변경 -->
        <ul class="navbar-nav">
          <li id="headerLoginBtn" class="nav-item dropdown" @mouseenter="hoverLoginDropdown(true)" @mouseleave="hoverLoginDropdown(false)">
            <router-link style="font-size:17px;" class="dropdown-item" to="/signin">
              로그인 <i :class="loginDropdownState ? 'ai-chevron-up' : 'ai-chevron-down'"></i>
            </router-link>
            <ul class="dropdown-menu" v-show="loginDropdownState">
              <li>
                <a style="font-size:17px;" class="dropdown-item" href="#">내 계정</a>
              </li>
              <li>
<<<<<<< Updated upstream
                <router-link class="dropdown-item" to="/signup"
                  >회원가입</router-link
                >
=======
                <router-link style="font-size:17px;" class="dropdown-item" to="/signup">회원가입</router-link>
>>>>>>> Stashed changes
              </li>
            </ul>
          </li>
        </ul>

        <div class="d-sm-none p-3 mt-n3">
<<<<<<< Updated upstream
          <a
            class="btn btn-primary w-100 mb-1"
            href="https://themes.getbootstrap.com/product/around-multipurpose-template-ui-kit/"
            target="_blank"
            rel="noopener"
          >
=======
          <a class="btn btn-primary w-100 mb-1"
             href="https://themes.getbootstrap.com/product/around-multipurpose-template-ui-kit/" target="_blank"
             rel="noopener">
>>>>>>> Stashed changes
            <i class="ai-cart fs-xl me-2 ms-n1"></i>Buy now
          </a>
        </div>
      </nav>
    </div>
  </header>
</template>

<script setup>
import { ref } from 'vue';

const menuItems = ref([
  {
    text: '투자성향',
    href: '#',
    active: true,
    megaMenu: true,
    subItems: [
      [
        { text: '테스트하기', href: 'test-start' },
        {
          text: '투자성향 보기',
          href: 'index.html',
        },
      ],
    ],
  },
  {
    text: '금융상품',
    href: '#',
    subItems: [
      {
        text: '예금',
        href: 'deposit',
        subItems: [
          { text: '예금', href: 'deposit' },
          { text: '예금', href: 'deposit' },
        ],
      },
      {
        text: '적금',
        href: 'savings',
        subItems: [
          { text: '적금', href: 'savings' },
          { text: '적금 상세페이지', href: 'savingsDetail' },
          { text: '~~~적금', href: 'shop-catalog.html' },
        ],
      },
      {
        text: '주식',
        href: 'stock',
        subItems: [{ text: '국내주식', href: 'stock' }],
      },
      {
        text: '펀드',
        href: 'fund',
        subItems: [
          { text: '펀드', href: 'fund' },
          { text: '펀드', href: 'fund' },
          { text: '펀드', href: 'fund' },
        ],
      },
      {
        text: 'ISA',
        href: 'isa',
        subItems: [
          { text: 'ISA', href: 'isa' },
          { text: 'ISA', href: 'isa' },
          { text: 'ISA', href: 'isa' },
        ],
      },
    ],
  },
  { text: '투자 커뮤니티', href: 'analysis' },
  { text: '용어사전', href: 'dictionary' },
  {
    text: '추가기능',
    href: '#',
    subItems: [
      {
        text: '금',
        href: 'gold',
        subItems: [
          { text: '금 시세', href: 'gold' },
          { text: '금투자', href: 'goldInvest' },
        ],
      },
      { text: '환전', href: 'account-overview.html' },
      { text: '뉴스', href: 'news' },
      { text: '마이페이지(임시)', href: 'myPage' },
    ],
  },
]);

const dropdownStates = ref(menuItems.value.map(() => false));
const loginDropdownState = ref(false);
const hoverDropdown = (index, isHovering) => {
  dropdownStates.value[index] = isHovering;
};

const hoverLoginDropdown = (state) => {
  loginDropdownState.value = state;
};
</script>

<style lang="scss">
// @import '@/assets/scss/_utilities.scss';
// @import '@/assets/scss/components/_dropdown.scss';
// @import '@/assets/scss/components/_navbar.scss';
// @import '@/assets/css/navbar.css';
// @import "@/assets/scss/_position.scss";
// @import "@/assets/scss/_containers.scss";
.row.justify-content-center.text-center.pt-md-2.pt-lg-0.pt-xl-2.pb-5.mb-md-2 {
  height: 500px;
}
// 부트스트랩 색상
.bg-primary.position-absolute.top-0.start-0.w-100.h-100 {
  background-color: #448c74 !important;
}

.text-primary.flex-shrink-0.me-2 {
  color: #448c74 !important;
}

.container-xxl {
  margin-top: 60px !important;
}
</style>

<style>
.header.navbar.navbar-expand-lg.fixed-top.bg-light {
  --ar-navbar-toggler-padding-y: 0.625rem;
  padding: 0.25rem;
}

a.nav-link {
  color: black;
}

/* 드롭다운 메뉴 애니메이션 및 화살표 회전 */
.appHeader a.nav-link.dropdown-toggle {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.appHeader a.nav-link i {
  transition: transform 0.3s ease;
}

/* 아이콘이 부드럽게 회전하도록 설정 */
.appHeader a.nav-link.dropdown-toggle[aria-expanded="true"] i {
  transform: rotate(180deg);
}

/* 기타 스타일 */
.header.navbar.navbar-expand-lg.fixed-top.bg-light {
  --ar-navbar-toggler-padding-y: 0.625rem;
}

#headerLoginBtn{
  width: 100px;
  height: 40px;
  border-radius: 10px;
  border: 1px solid lightgrey;
  background-color: rgba(67, 140, 116, 1);
  text-align: center;
  color:white;
  padding: 5px;
}

#headerLoginBtn:hover{
  color: black;
  background-color: lightgrey;
}
.invetiLogo{
  width: 100px;
}
</style>

<!-- <style lang="scss" scoped>
@import '~@/assets/css/navbar.css';

@import '~@/assets/scss/_utilities.scss';

</style> -->
