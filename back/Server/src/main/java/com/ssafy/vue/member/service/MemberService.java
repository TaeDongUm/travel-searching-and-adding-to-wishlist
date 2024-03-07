package com.ssafy.vue.member.service;

import com.ssafy.vue.member.model.KeyInfo;
import com.ssafy.vue.member.model.Member;
import java.sql.SQLException;

public interface MemberService {

    String loginMember(Member member) throws Exception;

    int idCheck(String user_id) throws Exception;

    int joinMember(Member member, KeyInfo keyInfo) throws Exception;

    int editMember(Member member) throws Exception;

    int deleteMember(String user_id) throws Exception;

    public String[] jwtlogin(Member m) throws Exception;

	int jwtlogout(String user_id) throws Exception;

    void uploadProfileImage(Member member) throws Exception;

    int passwordCheck(String user_id, String user_password) throws SQLException;

    int updatePwd(String id, String pPwd, String nPwd) throws Exception;

    Member getUserInfo(String user_id) throws SQLException;

}
