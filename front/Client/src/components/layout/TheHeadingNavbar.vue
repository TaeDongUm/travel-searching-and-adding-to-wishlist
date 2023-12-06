<template>
  <!-- Responsive navbar-->
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container px-5">
      <a class="navbar-brand" href="#!">
		<img 
		src="@/assets/white-logo.png" 
		style="height: 40px;"
		/>
	  </a>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'main' }">Home</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'kakaoMap' }">관광지도</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'board' }">게시판</router-link>
          </li>
          <li class="nav-item"><a class="nav-link" href="#!">여행메이트</a></li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'review' }">관광지후기</router-link>
          </li>
          <li class="nav-item" v-if="isLoggedIn">
            <router-link class="nav-link" :to="{ name: 'mypage' }" >👤{{currentUser.username}}님 안녕하세요.</router-link>
          </li>
          <li class="nav-item">
            <LoginModalVue
              v-if="!isLoggedIn"
            />
          </li>
          <li>
            <v-btn
              class="ml-2"
              color="#bfd4f5"
              v-if="isLoggedIn"
              @click="logout"
              >LOGOUT</v-btn
            >
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script setup>
//import { ref } from "vue";
import LoginModalVue from "@/components/member/LoginModal.vue";
import { useAuth } from "../../hooks/useAuth";

const { isLoggedIn, logout, currentUser } = useAuth();

</script>

<style></style>
