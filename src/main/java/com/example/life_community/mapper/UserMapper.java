package com.example.life_community.mapper;

import com.example.life_community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    // interface 默认 public

    @Insert("INSERT INTO USER(name, account_id, token, gmt_create, gmt_modified, avatar_url) values(#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl})")
    void insert(User user);

    @Select("SELECT * FROM USER WHERE token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("SELECT * FROM USER WHERE id = #{id}")
    User findById(@Param("id") Integer id);

    @Select("SELECT * FROM USER WHERE account_id = #{accountId}")
    User findByAccountId(String accountId);

    @Update("UPDATE USER SET name = #{name}, token = #{token}, gmt_modified = #{gmtModified}, avatar_url = #{avatarUrl} WHERE id = #{id}")
    void update(User dbUser);
}
