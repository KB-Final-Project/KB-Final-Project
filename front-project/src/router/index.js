import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/views/Home.vue';
import Signin from '@/views/Signin.vue';
import Signup from '@/views/Signup.vue';
import NewsPage from '@/views/NewsPage.vue';
import TestStartPage from '@/views/TestStartPage.vue';
import TestProcessPage from '@/views/TestProcessPage.vue';
import TestResultPage from '@/views/TestResultPage.vue';
import StockPage from '@/views/StockPage.vue';
import Dictionary from '@/views/menu/dictionary/Dictionary.vue';
import Savings from '@/views/menu/savings/Savings.vue';
import SavingsDetail from '@/views/menu/savings/SavingsDetail.vue';
import Gold from '@/views/menu/gold/Gold.vue';
import GoldInvest from '@/views/menu/gold/GoldInvest.vue';
import Calculator from '@/views/modal/Calculator.vue';
import Deposit from '@/views/menu/desposit/Deposit.vue';
import Fund from '@/views/menu/fund/Fund.vue';
import ISA from '@/views/ISA.vue';
import FundDetail from "@/views/menu/fund/FundDetail.vue";
import MyPage from "@/views/myPage/MyPage.vue";
import MyPageContent from "@/views/myPage/MyPageContent.vue";
import MyPagePanel from "@/views/myPage/MyPagePanel.vue";
import MyPageSetting from "@/views/myPage/MyPageSetting.vue";
import MyPageWithdraw from "@/views/myPage/MyPageWithdraw.vue";
import MyPagePosts from "@/views/myPage/MyPagePosts.vue";
import MyPageWarning from "@/views/myPage/MyPageWarning.vue";
import DepositDetail from "@/views/menu/desposit/DepositDetail.vue";
import SignCoverImage from "@/views/SignCoverImage.vue";
import ActiveInvestment from "@/views/menu/community/ActiveInvestment.vue";
import StabilitySeeking from "@/views/menu/community/StabilitySeeking.vue";
import AggressiveInvestment from "@/views/menu/community/AggressiveInvestment.vue";
import Stability from "@/views/menu/community/Stability.vue";
import RiskNeutral from "@/views/menu/community/RiskNeutral.vue";
import Community from "@/views/menu/community/Community.vue";


const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/signin',
    name: 'Signin',
    component: Signin,
    meta: { hideHeaderFooter: true },
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup,
    meta: { hideHeaderFooter: true },
  },
  {
    path: '/news',
    name: 'NewsPage',
    component: NewsPage,
  },
  {
    path: '/test-start',
    name: 'TestStartPage',
    component: TestStartPage,
  },
  {
    path: '/test-process',
    name: 'TestProcessPage',
    component: TestProcessPage,
  },
  {
    path: '/test-result',
    name: 'TestResultPage',
    component: TestResultPage,
  },
  {
    path: '/stock',
    name: 'StockPage',
    component: StockPage,
  },
  {
    path: '/dictionary',
    name: 'dictionary',
    component: Dictionary,
  },
  {
    path: '/savings',
    name: 'savings',
    component: Savings,
  },
  {
    path: '/saving/:savingId',
    name: 'SavingDetail',
    component: SavingsDetail,
  },
  {
    path: '/gold',
    name: 'gold',
    component: Gold,
  },
  {
    path: '/goldInvest',
    name: 'goldInvest',
    component: GoldInvest,
  },
  {
    path: '/calculator',
    name: 'calculator',
    component: Calculator,
  },
  {
    path: '/deposit', // Deposit 페이지 경로 추가
    name: 'deposit',
    component: Deposit,
  },
  {
    path: '/deposit/:savingId', // Deposit 페이지 경로 추가
    name: 'depositDetail',
    component: DepositDetail,
  },
  {
    path: '/fund', // Fund 페이지 경로 추가
    name: 'fund',
    component: Fund,
  },
  {
    path: '/fundDetail', // Fund 페이지 경로 추가
    name: 'fundDetail',
    component: FundDetail,
  },
  {
    path: '/isa', // ISA 페이지 경로 추가
    name: 'ISA',
    component: ISA,
  },
  {
    path: '/myPage',
    name: 'myPage',
    component: MyPage,
  },
  {
    path: '/myPageContent',
    name: 'myPageContent',
    component: MyPageContent,
  },
  {
    path: '/myPagePanel',
    name: 'myPagePanel',
    component: MyPagePanel,
  },
  {
    path: '/myPageSetting',
    name: 'myPageSetting',
    component: MyPageSetting,
  },
  {
    path: '/myPageWithdraw',
    name: 'myPageWithdraw',
    component: MyPageWithdraw,
  },
  {
    path: '/myPagePosts',
    name: 'myPagePosts',
    component: MyPagePosts,
  },
  {
    path: '/myPageWarning',
    name: 'myPageWarning',
    component: MyPageWarning,
  },
  {
    path: '/signCoverImage',
    name: 'signCoverImage',
    component: SignCoverImage,
    meta: { hideHeaderFooter: true },

  },

  // 필요한 다른 라우트들을 여기에 추가할 수 있습니다.
  {
    path: '/community',
    component: Community, // community.vue를 레이아웃 컴포넌트로 사용
    children: [
      {
        path: 'stabilitySeeking',
        name: 'stabilitySeeking',
        component: StabilitySeeking,
      },
      {
        path: 'aggressiveInvestment',
        name: 'aggressiveInvestment',
        component: AggressiveInvestment,
      },
      {
        path: 'activeInvestment',
        name: 'activeInvestment',
        component: ActiveInvestment,
      },
      {
        path: 'stability',
        name: 'stability',
        component: Stability,
      },
      {
        path: 'riskNeutral',
        name: 'riskNeutral',
        component: RiskNeutral,
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
