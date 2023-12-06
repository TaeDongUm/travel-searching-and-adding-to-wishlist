package com.ssafy.vue.member.controller;

import com.ssafy.vue.member.model.KeyInfo;
import com.ssafy.vue.member.model.Member;
import com.ssafy.vue.member.service.MemberService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("member")
public class MemberController {
    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("join")
    public Map join(@RequestBody Member member) throws Exception {

        System.out.println(member.toString());
        KeyInfo keyInfo = new KeyInfo();

        int cnt = memberService.joinMember(member, keyInfo);

        Map<String, String> map = new HashMap<>();
        if (cnt == 1) {
            map.put("msg", "회원가입 성공했습니다.");
        }

        return map;
    }

    @PostMapping("login")

    public Map login(@RequestBody Member member) throws Exception { // id, pw만 채워져서 옴
        System.out.println("login");
        System.out.println(member.getUser_name());
        Map<String, Object> map = new HashMap<>();

        try {
            String user_name = memberService.loginMember(member);
            member.setUser_name(user_name);

            // TODO : jwt login 추가
            if (user_name != null) {
                String[] tokens = memberService.jwtlogin(member);

                if (tokens.length > 0) {
                    map.put("tokens", tokens);
                    map.put("user_name", user_name);
                } else {
                    map.put("msg", "토큰 발급 실패했습니다.");
                }
            } else {
                map.put("msg", "로그인에 실패했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "서버에서 예외 발생");
        }

        return map;
    }

    @PostMapping("idcheck")
    public Map idcheck(@RequestBody Member member) throws Exception {
        int cnt = memberService.idCheck(member.getUser_id());

        Map<String, String> map = new HashMap<>();
        map.put("cnt", Integer.toString(cnt));
        return map;
    }

    @GetMapping("logout")
    public void logout(HttpServletRequest request) {
        System.out.println("logout");

        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
    }
}
