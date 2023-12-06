package com.ssafy.vue.member.service;

import com.ssafy.vue.member.model.KeyInfo;
import com.ssafy.vue.member.model.Member;

public interface MemberService {

    String loginMember(Member member) throws Exception;

    int idCheck(String user_id) throws Exception;

    int joinMember(Member member, KeyInfo keyInfo) throws Exception;

    int editMember(Member member) throws Exception;

    int deleteMember(String user_id) throws Exception;

    public String[] jwtlogin(Member m) throws Exception;

}
