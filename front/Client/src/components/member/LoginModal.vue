<template>
  <div class="text-center">
    <v-btn color="primary">
      Login
      <v-dialog v-model="dialog" activator="parent" width="400">
        <v-card>
          <div style="margin: 0 auto; padding-top: 10px">
            <img
              class="img-fluid rounded mb-4 mb-lg-0"
              src="@/assets/enjoytrip-logo.png"
              alt="..."
              style="width: 200px"
            />
          </div>
          <!-- 로그인 모달 -->

          <v-card v-if="!showSignUp" style="max-height: 450px; overflow-y: auto;">
            <v-card-title class="text-center">로그인</v-card-title>

            <v-card-text>
              <!-- <v-form @submit.prevent="login"> -->

              <v-form @submit.prevent="login">
                <v-text-field
                  id="login-id"
                  v-model="login_id"
                  label="아이디"
                  required
                ></v-text-field>
                <v-text-field
                  id="login-password"
                  v-model="login_password"
                  label="비밀번호"
                  type="password"
                  required
                ></v-text-field>

                <div class="custom-primary-text" id="join-text-div">
                  <span>계정이 없으신가요?</span>
                  <span
                    style="color: #1867c0; cursor: pointer"
                    @click="showSignUp = true"
                    >회원가입</span
                  >
                </div>

                <v-btn id="login-button" type="submit" color="primary" block
                  >로그인</v-btn
                >
              </v-form>
            </v-card-text>
          </v-card>

          <!-- 회원가입 모달 -->
          <v-card v-if="showSignUp" style="max-height: 450px; overflow-y: auto;">
            <v-card-title class="text-center">회원가입</v-card-title>
            <v-card-text>
              <v-form @submit.prevent="join">
                <v-text-field
                  v-model="user_name"
                  label="이름"
                  required
                ></v-text-field>
                <div style="display: flex;">
                  <v-text-field
                  v-model="user_id"
                  label="아이디"
                  @input="checkUserId"
                  required
                ></v-text-field>
                <v-btn color="primary" style="margin-left: 5%;" @click="idcheck">중복체크</v-btn>
                </div>
                <p style="color: green;" v-if="isIdValid == true && isIdUnique==true">사용할 수 있는 아이디입니다.</p>
                <p style="color: red;" v-if="isIdValid == true && isIdUnique == false">중복된 아이디입니다.</p>
                <p style="color: red;" v-if="isIdValid == false">사용할 수 없는 아이디입니다.</p>
                <v-text-field
                  v-model="user_password"
                  label="비밀번호"
                  type="password"
                  required
                ></v-text-field>
                <v-text-field
                  v-model="user_repassword"
                  label="비밀번호확인"
                  type="password"
                  required
                ></v-text-field>
                <p style="color: green;" v-if="passwordcheck==true">비밀번호가 일치합니다.</p>

                <div
                  id="email_field"
                  style="display: flex; align-items: center"
                >
                  <v-text-field v-model="user_email" label="이메일" required>
                  </v-text-field>
                  <p
                    style="margin-left: 3%; margin-right: 3%; font-size: 1.5em"
                  >
                    @
                  </p>
                  <email-sort-menu-item
                    @message="handleChildMessage"
                    style="width: 30%"
                  ></email-sort-menu-item>
                  <v-btn color="primary" variant="flat" style="margin-left: 1%; margin-bottom: 5%; width: 50px;" @click="sendEmail">본인확인</v-btn>
                </div>

                <div style="display: flex; align-items: center;">
                  <v-text-field label="메일로 발송된 인증번호를 입력하세요." v-model="inputCode"></v-text-field>
                  <v-btn color="#4f545c" variant="flat" style="margin-left: 1%; margin-bottom: 5%;" @click="emailValidation">입력</v-btn>
                </div>

                <p v-show="isEmailValid">인증 완료 되었습니다.</p>
                
                <div style="display: flex">
                  <input type="checkbox" />
                  <span style="margin-left: 1%;"> 약관동의</span>
                </div>
                

                <div id="join-text-div" v-if="!showSignUp">
                  <span>계정이 없으신가요? &nbsp;</span>
                  <span
                    style="color: #1867c0; cursor: pointer"
                    @click="showSignUp = true"
                    >회원가입</span
                  >
                </div>

                <div id="join-text-div" v-if="showSignUp">
                  <span>이미 가입하셨나요? &nbsp;</span>
                  <span
                    style="color: #1867c0; cursor: pointer"
                    @click="showSignUp = false"
                    >로그인</span
                  >
                </div>

                <v-btn id="join-button" type="submit" color="primary" block
                  >회원가입</v-btn
                >
              </v-form>
            </v-card-text>
          </v-card>

          <!-- 모달 닫기 버튼 -->
          <v-card-actions>
            <v-btn color="primary" block @click="dialog = false; isIdValid.value = null"
              >Close Dialog</v-btn
            >
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-btn>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import { localAxios } from "@/util/http-commons";
import EmailSortMenuItem from "@/components/member/EmailSortMenuItem.vue";
import { useAuth } from "@/hooks/useAuth";

const { login: login2, join: join2 } = useAuth();

const local = localAxios();
const dialog = ref(false);
const showSignUp = ref(false);
const location = ref("");
const isIdValid = ref(null);
const isIdUnique = ref(null);
const isEmailValid = ref(false);

const login_id = ref("");
const login_password = ref("");

const user_name = ref("");
const user_id = ref("");
const user_password = ref("");
const user_repassword = ref("");
const user_email = ref("");

const inputCode = ref("");
const authCode = ref(null);

// 자식에서 발생한 이벤트를 처리하는 메소드
const handleChildMessage = (message) => {
  location.value = message;
};

async function login() {
  if (!document.querySelector("#login-id").value) {
    alert("아이디 입력!!");
    return;
  } else if (!document.querySelector("#login-password").value) {
    alert("비밀번호 입력!!");
    return;
  } else {
    login2(login_id.value, login_password.value);
  }
}

async function join() {
  if (!user_name.value) {
    alert("이름을 입력해주세요.");
    return;
  } else if (!user_id.value) {
    alert("아이디를 입력해주세요.");
    return;
  } else if (!user_password.value) {
    alert("비밀번호를 입력해주세요.");
    return;
  } else if (user_password.value != user_repassword.value) {
    alert("비밀번호가 맞는지 확인해주세요.");
    return;
  } else if (!user_email.value) {
    alert("이메일을 입력해주세요.");
    return;
  } else if (!location.value || location.value === "선택") {
    console.log(location.value);
    alert("도메인을 선택해주세요.");
    return;
  } else {
    join2(user_name, user_id, user_password, user_email, location, isIdUnique, isIdValid);
  }
}

async function idcheck() {
  const response = await local.post("/member/idcheck", {
    user_id: user_id.value
  });

  console.log(response.data.cnt);

  if(parseInt(response.data.cnt) > 0) {
    isIdUnique.value = false;
    console.log("사용불가능");
  } else {
    isIdUnique.value = true;
    console.log("사용가능");
  }

  checkUserId()
}


function checkUserId() {
  if (user_id.value.length < 5) {
    isIdValid.value = false;
  } else { 
    isIdValid.value = true;
  }
}

const passwordcheck = computed(() =>
  user_password.value === user_repassword.value && user_repassword.value ? true : false
);

async function sendEmail() {
  alert("이메일이 전송되었습니다.");
  const email = user_email.value + "@" + location.value;
  const code = await local.post("/member/emailcheck",
      email
  );
  console.log(code.data.authCode);
  authCode.value = code.data.authCode;
}

function emailValidation() {
  if(inputCode.value === authCode.value) {
    isEmailValid.value = true;
  } else {
    alert("이메일 인증에 실패했습니다.");
  }
}
</script>

<style>
#join-text-div {
  margin: 0 auto;
  text-align: center;
  padding-bottom: 20px;
}
</style>
