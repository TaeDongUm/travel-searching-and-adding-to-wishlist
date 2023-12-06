<script setup>
import { ref } from "vue";
import { localAxios, auth } from "@/util/http-commons";
import { useAuth } from "../hooks/useAuth";

const { currentUser } = useAuth();
const local = localAxios();
const authLocal = auth(local);
const imgSrc = ref("");

const handleFileChange = (event) => {
  const file = event.target.files[0];
  // 파일이 이미지인지 확인
  if (file && file.type.startsWith("image/")) {
    // 이미지 파일이면 미리보기로 표시
    const reader = new FileReader();

    reader.onload = () => {
      imgSrc.value = reader.result;
      console.log(imgSrc.value);
    };
    reader.readAsDataURL(file);
  } else {
    // 이미지 파일이 아니면 기본값 또는 이전 이미지를 유지
    imgSrc.value = ""; // 여기에 다른 기본값을 설정할 수 있습니다.
    alert("이미지 파일이 아닙니다!");
  }
};

const items = [
  { title: "Click Me" },
  { title: "Click Me" },
  { title: "Click Me" },
];

const locations = ["ssafy.com", "naver.com", "gmail.com"];
const location = ref("ssafy.com");

async function handleSubmit() {
  try {
	console.log(currentUser.value.userid)
    // axios를 사용하여 서버에 POST 요청 전송
    const response = await authLocal.post("/member/proimage", {
		user_id: currentUser.value.userid,
		profile_image: imgSrc.value
	});
    console.log(response.data);
    // 서버 응답 처리
    if (response.data.msg === "ok") {
      // 파일 업로드 성공
      alert("프로필 이미지가 성공적으로 업데이트되었습니다.");
    } else {
      // 파일 업로드 실패
      alert("프로필 이미지 업데이트 실패");
    }
  } catch (error) {
    console.error("Error during file upload:", error);
    alert("프로필 이미지 업데이트 중 오류가 발생했습니다.");
  }
}
</script>

<template>
  <div id="joinDiv" class="container px-3 px-lg-5">
    <form id="form-edit" method="POST" action="">
      <input type="hidden" name="action" value="edit" />
      <input type="hidden" name="userid" value="" />
      <div class="container-fluid">
        <div class="row mb-4 text-center">
          <h3>회원정보 수정</h3>
        </div>

        <div class="row mb-2 text-center">
          <div class="col-3">프로필사진</div>
          <div class="col-3">
            <img :src="imgSrc" width="100" height="100" />
          </div>
        </div>

        <div class="row text-center">
          <div class="col-3"></div>
          <div class="col">
            <v-file-input
              label="File input"
              variant="filled"
              prepend-icon="camera"
              @change="handleFileChange"
            ></v-file-input>
          </div>
        </div>

        <div class="row mb-2 text-center">
          <div class="col-3">이름</div>
          <div class="col">
            <input
              type="text"
              class="form-control"
              id="userinfo-name"
              name="name"
              value=""
            />
          </div>
        </div>
        <div class="row mb-2 text-center">
          <div class="col-3">아이디</div>
          <div class="col">
            <input type="text" class="form-control" value="" disabled />
          </div>
        </div>
        <div class="row mb-2 text-center">
          <div class="col-3">비밀번호</div>
          <div class="col">
            <input
              type="password"
              placeholder="비밀번호"
              class="form-control"
              id=""
              name="password"
            />
          </div>
        </div>
        <div class="row mb-2 text-center">
          <div class="col-3">신규비밀번호</div>
          <div class="col">
            <input
              type="password"
              placeholder="신규비밀번호"
              class="form-control"
              id="new-password"
              name="newPassword"
            />
          </div>
        </div>
        <div class="row mb-2 text-center">
          <div class="col-3">비밀번호확인</div>
          <div class="col">
            <input
              type="password"
              placeholder="비밀번호확인"
              class="form-control"
              id="new-repassword"
            />
          </div>
        </div>
        <div class="row mb-2 text-center">
          <div class="col-3">이메일</div>
          <div class="col-4">
            <input
              type="text"
              class="form-control"
              id="userinfo-email"
              name="email"
              value=""
            />
          </div>
          <div class="col-2">
            <span class="input-group-text">@</span>
          </div>
          <div class="col-3">
            <!-- <select class="custom-select" id="userinfo-selectdomain"
							name="domain">
							<option value="">도메인 선택</option>
							<option value="ssafy.com">SSAFY</option>
							<option value="google.com">GOOGLE</option>
							<option value="kakao.com">KAKAO</option>
							<option value="naver.com">NAVER</option>
						</select> -->
            <div class="text-center">
              <v-select
                v-model="location"
                :items="locations"
                label="선택"
              ></v-select>
              <v-menu :location="location">
                <v-list>
                  <v-list-item v-for="(item, index) in items" :key="index">
                    <v-list-item-title>{{ item.title }}</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </div>
          </div>
        </div>

        <div
          id="edit-footer"
          class="container-fluid d-flex justify-content-end"
        >
          <button
            type="button"
            class="btn mr-2 btn-primary btn-sm"
            id="edit-button"
            @click="handleSubmit"
          >
            수정
          </button>
          <button
            type="button"
            class="btn mr-2 btn-secondary btn-sm"
            id="reset-button"
          >
            초기화
          </button>
          <button
            type="button"
            class="btn btn-danger btn-sm"
            id="withdraw-button"
          >
            회원탈퇴
          </button>
        </div>
      </div>
    </form>
  </div>
</template>

<style>
#joinDiv {
  max-width: 500px;
  margin-top: 5%;
  margin-bottom: 5%;
}
</style>
