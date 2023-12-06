package com.ssafy.vue.member.model.dao;

import com.ssafy.vue.member.model.Member;
import java.sql.SQLException;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberMapper {
    int joinMember(Member member) throws SQLException;

    String loginMember(Member member) throws SQLException;

    int idCheck(String user_id) throws SQLException;

    int passwordCheck(String user_id, String user_password) throws SQLException;

    int editMember(Member member) throws SQLException;

    int updatePwd(String id, String pPwd, String nPwd) throws Exception;

    int deleteMember(String user_id) throws SQLException;
    
    void saveToken(Member member);

}
