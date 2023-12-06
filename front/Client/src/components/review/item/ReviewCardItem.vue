<template>
  <div class="col-md-4 mb-5">
    <div class="card h-100">
      
      <div class="card-body">
        <h3 class="card-title">{{r.title}}</h3>
        <img :src="r.img" class="card-img-top card-img-bottom" alt="Image">
        <p class="card-text clamp-lines">
          {{r.content}}
        </p>
      </div>
      <div class="card-footer text-end">
        <!-- TODO: 글 id로 글 보러가는 거 링크 걸기 index.js 추가 -->
        <a class="btn btn-primary btn-sm" href="#!">글 보러가기 <v-icon icon="fa:fas fa-arrow-right"></v-icon></a>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({r:Object});
</script>

<style scoped>
.clamp-lines {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  overflow: hidden;
  -webkit-line-clamp: 5;
  white-space: normal !important;
}
</style>