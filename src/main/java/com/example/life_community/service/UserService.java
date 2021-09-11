package com.example.life_community.service;

import com.example.life_community.mapper.UserMapper;
import com.example.life_community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {

        // 查询是否存在用户
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        System.out.println("查询数据库 dbUser：" + dbUser);
        if(dbUser == null) {
            // 新增
            System.out.println("新增");
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            // 更新
            System.out.println("更新");
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            userMapper.update(dbUser);
        }

    }

}
