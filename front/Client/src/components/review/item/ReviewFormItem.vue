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
      <label for="subject" class="form-label">제목 : </label>
      <input type="text" class="form-control" v-model="article.subject" placeholder="제목..." />
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용 : </label>
      <textarea class="form-control" v-model="article.content" rows="10"></textarea>
    </div>
    <div class="mb-3">
      <v-file-input
    label="File input"
    variant="filled"
    prepend-icon="camera"
    @change="handleFileChange"
  ></v-file-input>
  <p v-if="fileError">{{ fileError }}</p>
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

<script setup>
import { ref, watch } from "vue";
import { useRouter } from "vue-router"
import { registArticle } from "@/api/board.js";

const router = useRouter();
const props = defineProps({ type: String });

const article = ref({
  articleNo: 0,
  subject: "",
  content: "",
  userId: "",
  userName: "",
  hit: 0,
  registerTime: "",
});

if (props.type === "modify") {
  let { articleno } = route.params;
  console.log(articleno + "번글 얻어와서 수정할거야");
  // API 호출
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

// 글 등록
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
    registArticle(article.value,
      (res) => { 
        console.log(res);
        router.push({ name: "review-collection" });
      },
      (error) => { console.log(error); alert(error) });
}

function updateArticle() {
  console.log(article.value.articleNo + "번글 수정하자!!", article.value);
   // API 호출
}

function moveList() {
  router.push({ name: "article-list" });
}
</script>