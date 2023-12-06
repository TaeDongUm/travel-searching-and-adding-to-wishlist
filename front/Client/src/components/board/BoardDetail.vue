<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle, registComment, getComments,deleteComment,updateComment } from "@/api/board.js";

const route = useRoute();
const router = useRouter();

// const articleno = ref(route.params.articleno);
const { articleno } = route.params;
console.log(articleno);
const article = ref({});
const { comment } = defineProps(['comment']);
const comments = ref({});

onMounted(() => {
  getArticle();
});

const getArticle = () => {
  // const { articleno } = route.params;
  console.log(articleno + "번글 얻으러 가자!!!");
  // API 호출
  detailArticle(articleno, ({ data }) => { article.value = data; console.log(data); getCommentsForArticle(article.value.articleno); }, (error) => { console.log(error) });
  
};

const getCommentsForArticle = () => {

  console.log(articleno + "번 댓글들");

  getComments(articleno, ({ data }) => { comments.value = data; console.log(data); }, (error) => { console.log(error) });

  // comments.value =getComments(articleno) 
  
}

const replyContent = ref("");


function registCommentsHandle() {
  const param = {
    article_no: articleno,
    comment: replyContent.value,
    user_id: "user_id",
   
    // subject: replySubject.value,
    // groupLayer:article.value.groupLayer,
  };
  registComment(param,
    ({ data }) => {
      console.log(data);
      // comments.value.push(data);
      comments.value =data;
    }, (error) => {
    console.log(error);
  });

  replyContent.value = '';
  
}

function moveList() {
  router.push({ name: "article-list" });
}

function moveModify() {
  
  router.push({ name: "article-modify", params: { articleno } });
}

function onDeleteArticle() {
  // const { articleno } = route.params;
  console.log(articleno + "번글 삭제하러 가자!!!");
  // API 호출
  deleteArticle(articleno, ({ data }) => { article.value=data,router.push({ name: "article-list" });}, (error) => {console.log(error) });
}

function onDeleteComment(comment) {
  console.log(comment.comment_no + "번 답글 삭제하자!!");
  deleteComment(comment, ({ data }) => { comments.value = data;console.log(comment.comment_no); getCommentsForArticle(comment.article_no)}, (error) => {console.log(error) });
}

function onEditComment(comment) {
  // Set editing state to true for the clicked comment
  comment.editing = true;
  // Initialize editedContent with the current comment content
  comment.editedContent = comment.comment;
}

function onSubmitEdit(comment) {
  // Submit the edited content
  console.log(comment.comment_no + "번 댓글 수정 완료: " + comment.editedContent);

  comment.comment = comment.editedContent;
  // Reset editing state
  comment.editing = false;
  updateComment(comment, ({ data }) => { comments.value = data; console.log(comment.comment_no); getCommentsForArticle(comment.article_no)}, (error) => {console.log(error) });
}

</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글보기</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <div class="row my-2">
          <h2 class="text-secondary px-5">{{ article.article_no }}. {{ article.subject }}</h2>
        </div>
        <div class="row">
          <div class="col-md-8">
            <div class="clearfix align-content-center">
              <img
                class="avatar me-2 float-md-start bg-light p-2"
                src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
              />
              <p>
                <span class="fw-bold">안효인</span> <br />
                <span class="text-secondary fw-light">
                  {{ article.register_time }}1 조회 : {{ article.hit }}
                </span>
              </p>
            </div>
          </div>
          <!-- <div class="col-md-4 align-self-center text-end">댓글 : 17</div> -->
          <div class="divider mb-3"></div>
          <div class="text-secondary">
            {{ article.content }}
          </div>

          <div id="app"></div>
          <div class="divider mt-3 mb-3"></div>
          <!-- <div class="border" style="text-align: center;"> -->
            <div style="display: inline-block; width: 80%; height: 100%;">
              <!-- <v-textarea label="Label" variant="solo-filled" style="width: 100%; height: 100%;" v-model="replyContent"> -->
              <v-textarea label="댓글을 달아주세요" rows="1" row-height="15" auto-grow variant="outlined" style="width: 100%; height: 100%;" v-model="replyContent">
              </v-textarea>
              <!-- <textarea style="width: 100%; height: 100%;" v-model="replyContent">
              </textarea> -->
            </div>
            <div style="display: inline-block;">
              <v-btn @click="registCommentsHandle">  Button</v-btn>
              <!-- <button type="button" class="btn btn-outline-info mb-3" @click="registCommentsHandle">
                답글 등록
              </button> -->
            </div>
          <!-- </div> -->
          <div class="d-flex justify-content-end">
            
            <button type="button" class="btn btn-outline-primary mb-3" @click="moveList">
              글목록
            </button>
            <button type="button" class="btn btn-outline-success mb-3 ms-1" @click="moveModify">
              글수정
            </button>
            <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="onDeleteArticle">
              글삭제
            </button>
          </div>
          <div>             
              <div v-for="comment in comments" :key="comment.id" :comment="comment" >
              <tr class="text-center">
                  <th scope="row">{{ comment.user_id }}</th>
                  <td class="text-start">
                    <!-- <router-link
                    :to="{ name: 'article-view', params: { comment: comment.article_no } }"
                    class="article-title link-dark"
                        >
                      {{ comment.subject }}
                    </router-link> -->
                  </td>

                  <td>
        <template v-if="comment.editing">
          <!-- Display textarea when editing is true -->
          <v-textarea v-model="comment.editedContent"></v-textarea>
          <button type="button" class="btn btn-outline-success mb-3 ms-1" @click="onSubmitEdit(comment)">
            완료
          </button>
        </template>
        <template v-else>
          <!-- Display comment text when not editing -->
          <td>{{ comment.comment }}</td>
          <td>{{ comment.register_time }}</td>
          <td><button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="onEditComment(comment)">
            수정
          </button></td>
          <td><button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="onDeleteComment(comment)">
            삭제
          </button></td>
        </template>
      </td>
                  
                

              
                </tr>
              </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>
