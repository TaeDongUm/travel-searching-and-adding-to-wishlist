package com.ssafy.vue.member.aop;

import com.ssafy.vue.member.model.KeyInfo;
import com.ssafy.vue.member.model.Member;
import com.ssafy.vue.member.model.dao.InfoMapper;
import com.ssafy.vue.member.model.dao.MemberMapper;
import com.ssafy.vue.util.OpenCrypt;
import java.util.UUID;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class EncryptAspect {
    private Logger logger = LoggerFactory.getLogger(EncryptAspect.class);

    private MemberMapper memberMapper;
    private InfoMapper infoMapper;

    public EncryptAspect() { }

    @Autowired
    public EncryptAspect(MemberMapper memberMapper, InfoMapper infoMapper) {
        this.memberMapper = memberMapper;
        this.infoMapper = infoMapper;
    }

    @Before(value="execution(* com.ssafy.vue.member.service.MemberService.joinMember(..))")
    public void memberJoinEncrypt(JoinPoint joinPoint) throws Exception {

        Member member = (Member) joinPoint.getArgs()[0];
        KeyInfo keyInfo = (KeyInfo) joinPoint.getArgs()[1];


        // 비밀번호 AES 암호화를 위한 key 만들기
        String pw = member.getUser_password();
        byte[] key = OpenCrypt.generateKey("AES", 128);

        // 비밀번호 해싱을 위한 salt 만들기
        String pw_salt = UUID.randomUUID().toString();

        // 1. 비밀번호 AES 암호화
        String cPw = OpenCrypt.aesEncrypt(pw, key);

        // 2. AES 암호화한 비밀번호를 salt 이용해서 해싱
        String hashed_cPw = OpenCrypt.byteArrayToHex(OpenCrypt.getSHA256(cPw, pw_salt));

        // 암호화한 비밀번호로 memberDto 업데이트
        member.setUser_password(hashed_cPw);

        keyInfo.setUser_id(member.getUser_id());
        keyInfo.setPw_salt(pw_salt);
        keyInfo.setPw_key(OpenCrypt.byteArrayToHex(key));
    }

    // TODO : 로그인 시 비밀번호 암호화
    @Before(value="execution(* com.ssafy.vue.member.service.MemberService.loginMember(..))")
    public void memberLoginEncrypt(JoinPoint joinPoint) throws Exception {
        System.out.println("TODO: 로그인 시 비밀번호 암호화");
    }


}
