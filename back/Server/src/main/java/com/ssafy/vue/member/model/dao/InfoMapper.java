package com.ssafy.vue.member.model.dao;

import com.ssafy.vue.member.model.KeyInfo;
import java.sql.SQLException;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface InfoMapper {

    public void setPwKey(KeyInfo keyInfo) throws SQLException;

    public KeyInfo getKeyInfo(String hashed_id) throws SQLException;

    public void deleteKeyInfo(String hashed_id) throws Exception;
}
