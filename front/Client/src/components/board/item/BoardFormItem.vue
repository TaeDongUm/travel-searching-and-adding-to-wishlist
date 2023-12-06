<script setup>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router"
// import axios from "axios";
import { registArticle, modifyArticle } from "@/api/board.js";


const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });

const isUseId = ref(false);

const selectedCategory = ref("1");

const article = ref({
  article_no: 0,
  subject: "",
  content: "",
  category:selectedCategory.value,
  user_id: sessionStorage.getItem("user_id"),
  user_name: "",
  hit: 0,
  register_time: "",
  
});

if (props.type === "modify") {
  let { articleno } = route.params;
  console.log(articleno + "번글 얻어와서 수정할거야");
  article.value.article_no = articleno;
  isUseId.value = true;
}

const subjectErrMsg = ref("");
const contentErrMsg = ref("");
watch(
  () => article.value.subject,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      subjectErrMsg.value = "제목을 확인해 주세요!!!";
    } else subjectErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => article.value.content,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      contentErrMsg.value = "내용을 확인해 주세요!!!";
    } else contentErrMsg.value = "";
  },
  { immediate: true }
);

function onSubmit() {
  // event.preventDefault();

  if (subjectErrMsg.value) {
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === "regist" ? writeArticle() : updateArticle();
  }
}

function writeArticle() {
  console.log("글등록하자!!", article.value);
  // API 호출
 
    console.log(registArticle)
   
    registArticle(article.value,
      (res) => { console.log(res);router.push({ name: "article-list" });},
      (error) => { console.log(error); alert(error) });
}

function updateArticle() {
  console.log(article.value.article_no + "번글 수정하자!!", article.value);
  // API 호출
  modifyArticle(article.value,
      (res) => { console.log(res);router.push({ name: "article-list" });},
      (error) => { console.log(error); alert(error) });
}

function moveList() {
  router.push({ name: "article-list" });
}

</script>

<template>
  <form @submit.prevent="onSubmit" method="post">
    <div class="mb-3">
      <label for="user_id" class="form-label">작성자 ID : </label>
      <input
        type="text"
        class="form-control"
        v-model="article.user_id"
        :disabled="isUseId"
        placeholder="작성자ID..."
      />
    </div>
    <div class="mb-3">
      <label for="user_id" class="form-label">카테고리 : </label>
    <select name="count" class="form-select" data-title="선택" v-model="selectedCategory">
      <option value="1">여행공유</option>
      <option value="2">여행메이트</option>
      <option value="3">관광지후기</option>
    </select>
    </div>
    <div class="mb-3">
      <label for="subject" class="form-label">제목 : </label>
      <input type="text" class="form-control" v-model="article.subject" placeholder="제목..." />
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용 : </label>
      <textarea class="form-control" v-model="article.content" rows="10"></textarea>
    </div>
    <div class="col-auto text-center">
      <button type="submit" class="btn btn-outline-primary mb-3" v-if="type === 'regist'">
        글작성
      </button>
      <button type="submit" class="btn btn-outline-success mb-3" v-else>글수정</button>
      <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="moveList">
        목록으로이동...
      </button>
    </div>
  </form>
</template>

<style scoped></style>
