package com.example.life_community.mapper;

import com.example.life_community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    // interface 默认 public

    @Insert("INSERT INTO USER(name, account_id, token, gmt_create, gmt_modified) values(#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified})")
    void insert(User user);

}
