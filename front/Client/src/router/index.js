import { createRouter, createWebHistory } from 'vue-router'
import TheMainView from '../views/TheMainView.vue'
import TheReviewView from '../views/TheReviewView.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: TheMainView
    }, 
    {
      path: '/todo',
      name: 'todo',
      component: () => import("@/views/TheTodoView.vue"),
    },
    {      
      path: "/kakaoMap",
      name: "kakaoMap",
      component: () => import("@/components/map/MapView.vue"),
    },
    {
      path: '/board',
      name: 'board',
      component: () => import("../views/TheBoardView.vue"),
      redirect: { name: "article-list" },
      children: [
        {
          path: "list",
          name: "article-list",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: "view/:articleno",
          name: "article-view",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "article-write",
          component: () => import("@/components/board/BoardWrite.vue"),
        },
        {
          path: "regist",
          name: "article-regist",
          component: () => import("@/components/board/BoardList.vue"),
          redirect: { name: "article-list" }
        },
        {
          path: "modify/:articleno",
          name: "article-modify",
          component: () => import("@/components/board/BoardModify.vue"),
        },
        
      ]
    },
    {
      path: '/review',
      name: 'review',
      component: TheReviewView,
      redirect: { name: "review-collection" },
      children: [
        {
          path: "review-collection",
          name: "review-collection",
          component: () => import("@/components/review/ReviewCollection.vue"),
        },
        {
          path: "review-write",
          name: "review-write",
          component: () => import("@/components/review/ReviewWrite.vue")
        },
      ]
    },
    {
      path: '/mypage',
      name: 'mypage',
      component: () => import("@/views/MyPageView.vue"),
    },
  ],
});

export default router
