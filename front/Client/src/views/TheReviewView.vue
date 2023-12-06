<template>
  <div class="container px-4 px-lg-5" id="reviewDiv">
    <router-view></router-view>
  </div>
  
</template>

<script setup>
// 컴포넌트로 쪼개고 v-for
import { ref } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();

</script>

<style>
</style>
