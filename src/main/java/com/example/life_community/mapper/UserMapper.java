package com.example.life_community.mapper;

import com.example.life_community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    // interface 默认 public

    @Insert("INSERT INTO USER(name, account_id, token, gmt_create, gmt_modified, avatar_url) values(#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl})")
    void insert(User user);

    @Select("SELECT * FROM USER WHERE token = #{token}")
    User findByToken(@Param("token") String token);
}
