import { ref, computed, inject } from 'vue'
import { localAxios, auth } from "@/util/http-commons";

const local = localAxios();
// TODO: 세션과 쿠키를 통한 로그인 유무 처리
// TODO: 로그인 유무에 따른 네비게이션 가드
const token = ref(window.localStorage.getItem("access_token") || null)

const currentUser = ref(null)

const onMountedCallBack = () => {
  if (token.value) {
    currentUser.value = {
      username: "주희",
      userid: "a"
    }
  }
}

const isLoggedIn = computed(() => {
  return !!currentUser.value
})

// const isAdmin = computed(() => {
//   return !!currentUser.value && currentUser.isAdmin
// })

// TODO: 더미데이터를 실제 데이터로 바꾸기
const login = async (login_id, login_password) => {
  try {
    const user_id = login_id;
    const user_password = login_password;
    const response = await local.post('/member/login', {
      user_id,
      user_password
    });

    const body = response.data;

    if (body.user_name) {
      alert("로그인이 완료되었습니다.");
      console.log(body.tokens);
      // 토큰을 localStorage에 저장
      localStorage.setItem('access_token', body.tokens[0]);
      localStorage.setItem('refresh_token', body.tokens[1]);

      currentUser.value = {
        username : body.user_name,
        userid : user_id
      }
    } else {
      alert("아이디/비밀번호를 확인해주세요.");
    }
  } catch (error) {
    console.error('Error during login:', error);
  }
}

const logout = async() => {
  const authLocal = auth(local);
  try { 
    // console.log('Current Token:', localStorage.getItem('access_token'));
	await authLocal.get('/member/logout')
    currentUser.value = null
    localStorage.removeItem("access_token");
    localStorage.removeItem("refresh_token");
    alert("로그아웃이 완료되었습니다.")
  } catch (error) {
      console.error('Error during login:', error);
  }
}

const join = async(user_name, user_id, user_password, user_email, location, isIdValid, isIdUnique) => {
  if (!isIdUnique || !isIdValid) {
    alert("아이디를 확인해주세요");
    return;
  }
  
  try {
    const user_domain = location.value;
    const response = await local.post("/member/join", {
      user_name: user_name.value,
      user_id: user_id.value,
      user_password: user_password.value,
      email_id: user_email.value,
      email_domain: user_domain,
    });

    if (response) {
      alert("회원가입이 완료되었습니다.");
    } else {
      alert("회원가입에 실패했습니다.");
    }
    
  } catch (error) {
    console.error("Error during login:", error);
  }
}

export const useAuthProvider = [
  'useAuth',
  {
    currentUser,
    isLoggedIn,
    // isAdmin,
    login,
    logout,
    onMountedCallBack,
    join
  },
]

export const useAuth = () => inject('useAuth')