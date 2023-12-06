package com.ssafy.vue.member.service;

import com.ssafy.vue.member.model.KeyInfo;
import com.ssafy.vue.member.model.Member;
import com.ssafy.vue.member.model.dao.InfoMapper;
import com.ssafy.vue.member.model.dao.MemberMapper;
import com.ssafy.vue.util.JwtTokenProvider;
import com.ssafy.vue.util.OpenCrypt;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private MemberMapper memberMapper;
    private InfoMapper infoMapper;

    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper, InfoMapper infoMapper) {
        this.memberMapper = memberMapper;
        this.infoMapper = infoMapper;
    }

    @Override
    public int idCheck(String user_id) throws Exception {
        return memberMapper.idCheck(user_id);
    }

    // join 실행하기 전에 암호화 aop 실행
    @Override
    public int joinMember(Member member, KeyInfo keyInfo) throws Exception {
        infoMapper.setPwKey(keyInfo);
        return memberMapper.joinMember(member);
    }

    @Override
    public String loginMember(Member member) throws Exception {

        // 등록된 id 아니면 null 리턴
        if (memberMapper.idCheck(member.getUser_id()) != 1) return null;

        KeyInfo keyInfo = infoMapper.getKeyInfo(member.getUser_id());
        if (keyInfo.getPw_key() == null || keyInfo.getPw_salt() == null) return null;

        byte[] key = OpenCrypt.hexToByteArray(keyInfo.getPw_key());

        String cUserPwd = OpenCrypt.aesEncrypt(member.getUser_password(), key);
        String hashed_cUserPwd = OpenCrypt.byteArrayToHex(OpenCrypt.getSHA256(cUserPwd, keyInfo.getPw_salt()));
        member.setUser_password(hashed_cUserPwd);


        return memberMapper.loginMember(member);
    }

    public String[] jwtlogin(Member m) throws Exception {

//        // 로그인 다시 할 때
//        if(JwtTokenProvider.validateToken(m.getAccess_token(), m.getSalt()))

        // jwt 발급한 뒤 리턴
        String salt = UUID.randomUUID().toString();
        String access_token= JwtTokenProvider.createAccessToken(m, salt);
        String refresh_token=JwtTokenProvider.createRefreshToken(m.getUser_id(), salt);

        m.setSalt(salt);
        m.setAccess_token(access_token);
        m.setRefresh_token(refresh_token);
        System.out.println("jwt로그인" + m);
//
        memberMapper.saveToken(m);


//
        return new String[] {access_token, refresh_token};

    }

    @Override
    public int editMember(Member member) throws Exception {
        return memberMapper.editMember(member);
    }

    @Override
    public int deleteMember(String user_id) throws Exception {
        return memberMapper.deleteMember(user_id);
    }
}
