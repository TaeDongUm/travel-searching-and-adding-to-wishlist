package com.ssafy.vue.member.service;

import com.fasterxml.jackson.databind.ser.Serializers.Base;
import com.ssafy.vue.member.model.KeyInfo;
import com.ssafy.vue.member.model.Member;
import com.ssafy.vue.member.model.dao.InfoMapper;
import com.ssafy.vue.member.model.dao.MemberMapper;
import com.ssafy.vue.redis.token.service.TokenGenerateService;
import com.ssafy.vue.redis.token.service.TokenStoreServiceImpl;
import com.ssafy.vue.util.JwtTokenProvider;
import com.ssafy.vue.util.OpenCrypt;
import java.sql.SQLException;
import java.util.Base64;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
	
//	private final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
	@Autowired
	private TokenGenerateService tokenGenerateService;
	@Autowired
	private TokenStoreServiceImpl tokenStoreService;
	
	
    private MemberMapper memberMapper;
    private InfoMapper infoMapper;
    
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

        // 로그인 다시 할 때
//        if(JwtTokenProvider.validateToken(m.getAccess_token(), m.getSalt())) {

        String salt = tokenGenerateService.generateTokenSalt();
//        System.out.println(salt);
        String access_token= tokenGenerateService.generateAccessToken(m, salt);
        String refresh_token=tokenGenerateService.generateRefreshToken(m, salt);

        m.setSalt(salt);
        m.setAccess_token(access_token);
        m.setRefresh_token(refresh_token);
//        System.out.println("jwt로그인" + m);
        
        // redis에 user_id, salt, access_token, refresh_token 저장
        tokenStoreService.saveTokens(m.getUser_id(), access_token, refresh_token, salt );
        tokenStoreService.saveUserIdAccessToken(access_token, m.getUser_id());
        tokenStoreService.saveUserIdRefreshToken(refresh_token, m.getUser_id());
//        memberMapper.saveToken(m);

//        System.out.println(tokenStoreService.getAccessToken(m.getUser_id()));
//        System.out.println(tokenStoreService.getRefreshToken(m.getUser_id()));
//        System.out.println(tokenStoreService.getTokenSalt(m.getUser_id()));
        
        return new String[] {tokenStoreService.getAccessToken(m.getUser_id()), tokenStoreService.getRefreshToken(m.getUser_id())};
//        }

    }

    @Override
    public int editMember(Member member) throws Exception {
        return memberMapper.editMember(member);
    }

    @Override
    public int deleteMember(String user_id) throws Exception {
        return memberMapper.deleteMember(user_id);
    }


	@Override
	public int jwtlogout(String user_id) throws Exception {
		return tokenStoreService.deleteTokens(user_id);
	}


    @Override
    public void uploadProfileImage(Member member) throws Exception {
        String profile_image = member.getProfile_image();
        profile_image = profile_image.replaceFirst("data:image/png;base64,", "");
        byte[] imageBytes = Base64.getDecoder().decode(profile_image);
        member.setProfile_image(profile_image);
        member.setBlob_image(imageBytes);
        memberMapper.uploadProfileImage(member);
    }

    @Override
    public int passwordCheck(String user_id, String user_password) throws SQLException {
        return memberMapper.passwordCheck(user_id, user_password);
    };

    @Override
    public int updatePwd(String id, String pPwd, String nPwd) throws Exception {
        return memberMapper.updatePwd(id, pPwd, nPwd);
    };

    @Override
   public Member getUserInfo(String user_id) throws SQLException {
        Member m = memberMapper.getUserInfo(user_id);
        String blobImage = Base64.getEncoder().encodeToString(m.getBlob_image());
        m.setProfile_image("data:image/png;base64,"+blobImage);
        return m;
    };
}
