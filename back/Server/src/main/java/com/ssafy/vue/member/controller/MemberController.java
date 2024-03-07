package com.ssafy.vue.member.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.vue.member.model.KeyInfo;
import com.ssafy.vue.member.model.Member;
import com.ssafy.vue.member.service.EmailService;
import com.ssafy.vue.member.service.MemberService;
import com.ssafy.vue.redis.token.service.TokenGenerateService;
import com.ssafy.vue.util.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.member.model.KeyInfo;
import com.ssafy.vue.member.model.Member;
import com.ssafy.vue.member.service.MemberService;
import com.ssafy.vue.redis.token.service.TokenStoreServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("member")
public class MemberController {
	@Autowired
    private MemberService memberService;

	@Autowired
	private TokenStoreServiceImpl tokenStoreService;

    @Autowired
    private TokenGenerateService tokenGenerateService;

    private EmailService emailService;
////    @Autowired
////    public MemberController(MemberService memberService) {
////        this.memberService = memberService;
////    }
    
    @Autowired
    public MemberController(MemberService memberService, EmailService emailService) {
        this.memberService = memberService;
        this.emailService = emailService;
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
//        System.out.println("login");
//        System.out.println(member.getUser_id());
        Map<String, Object> map = new HashMap<>();

        try {
            String user_name = memberService.loginMember(member);
            member.setUser_name(user_name);
            System.out.println(user_name);
            map.put("user_name", user_name);
//             TODO : jwt login 추가
            if (user_name != null) {
            	
            	// redis에서 access_token과 refresh_token 가져ㅇ
                String[] tokens = memberService.jwtlogin(member);
//                System.out.println(tokenStoreService.getUserId(tokens[0]));
//                System.out.println(tokens[0]);
//                System.out.println(tokens[1]);
//                System.out.println(tokenStoreService.getTokenSalt(member.getUser_id()));
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
        System.out.println(cnt);
        Map<String, String> map = new HashMap<>();
        map.put("cnt", Integer.toString(cnt));
        return map;
    }

    @PostMapping("emailcheck")
    public Map emailcheck(@RequestBody String email) throws Exception {
        System.out.println(email);
        String authCode = emailService.sendEmail(email);
        System.out.println(authCode);
        Map<String, String> map = new HashMap<>();
        map.put("authCode", authCode);
        return map;
    }

	@PostMapping("logout")
	public void logout(@RequestBody Map<String, String> map) throws Exception {
        System.out.println("logout");
        String getUser_id = tokenStoreService.getUserId( map.get("access_token"));
        tokenStoreService.deleteTokens(getUser_id);
        tokenStoreService.deleteUserId(map.get("access_token"));
        tokenStoreService.deleteUserId(map.get("refresh_token"));
        // TODO: token 블랙리스트에 추가
        // TODO: 나중에 서버에서 요청이 올 때 토큰이 블랙리스트에 있는지 확인 후 거부 가능
    }

    @PostMapping("proimage")
    public Map uploadProfileImage(@RequestBody Member member) throws Exception {
        System.out.println("upload profile image");
        memberService.uploadProfileImage(member);
        Map<String, String> map = new HashMap<>();
        map.put("msg", "ok");
        return map;
    }

    @PostMapping("userinfo")
    public Map getUserInfo(@RequestBody String user_id) throws Exception {
        Member member = memberService.getUserInfo(user_id.substring(0, user_id.length() - 1));
        Map<String, Object> map = new HashMap<>();
        map.put("userinfo", member);
        return map;
    }

    @PostMapping("refresh")
    public Map ReAccessToken(@RequestBody String refreshedToken) throws Exception {
        System.out.println(refreshedToken);
        String jwtToken = refreshedToken.substring(7);

        // Payload에서 ID 추출
        String payload = tokenStoreService.extractAndDecodePayload(jwtToken);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(payload);
        System.out.println(jsonNode);
        JsonNode idNode = jsonNode.get("id");
        System.out.println(idNode);
//
        String idValue = idNode.textValue();
        System.out.println(idValue);
        String salt = tokenStoreService.getTokenSalt(idValue);

        Claims claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwtToken).getBody();

        Member m = memberService.getUserInfo(idValue);
        String access_token = "";
        if (!claims.getExpiration().before(new java.util.Date())) {
            salt = tokenGenerateService.generateTokenSalt();
            access_token = JwtTokenProvider.createAccessToken(m, salt);
            System.out.println("new Access Token: "+ access_token);
        }
        Map<String, String> map = new HashMap<>();
        map.put("access_token", access_token);
        return map;
    }
}
