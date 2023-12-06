import { localAxios } from "@/util/http-commons";
import { auth } from "../util/http-commons";


const local = localAxios();
const request = auth(local)

const url = "/board"

function listArticle(param, success, fail) {
  local.get(`${ url}`, { params: param }).then(success).catch(fail);
}

function detailArticle(articleno, success, fail) {
  local.get(`${ url}/${articleno}`).then(success).catch(fail);
}

function registArticle(article, success, fail) {
  console.log("boardjs article", article);
  local.post(`${url}`, article).then(success).catch(fail);
}

function registComment(comment, success, fail) { 
  console.log("comments regist", comment);
  local.post(`${url}/comment`, comment).then(success).catch(fail);
}

function getComments(articleno, success, fail) {
  console.log("getComments", articleno);
  local.post(`${url}/getComments?articleno=${articleno}`).then(success).catch(fail);
}

function deleteComment(comment, success, fail) {
  console.log("deleteComment", comment);
  local.post(`${url}/deleteComment`, comment).then(success).catch(fail);

}

function updateComment(comment, success, fail) {
  console.log("updateComment", comment);
  local.post(`${url}/updateComment`, comment).then(success).catch(fail);

}

// async function getComments(articleno, success, fail) {
//   await axios.post(`${url}/getComments?articleno=${articleno}`).then(success).catch(fail);

// }


function modifyArticle(article, success, fail) {
  local.put(`${ url}`, article).then(success).catch(fail);
}

function deleteArticle(articleno, success, fail) {
  local.delete(`${ url}/${articleno}`).then(success).catch(fail);
}

export {
  listArticle,
  detailArticle,
  registArticle,
  registComment,
  modifyArticle,
  deleteArticle,
  getComments,
  deleteComment,
  updateComment,

};
