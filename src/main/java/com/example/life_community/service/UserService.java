package com.example.life_community.service;

import com.alibaba.fastjson.JSON;
import com.example.life_community.mapper.UserMapper;
import com.example.life_community.model.User;
import com.example.life_community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {

        // 查询是否存在用户
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> dbUserList = userMapper.selectByExample(userExample);
        System.out.println("查询数据库 dbUserList：" + JSON.toJSONString(dbUserList));
        if(dbUserList.size() == 0) {
            // 新增
            System.out.println("新增");
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            // 更新
            System.out.println("更新");
            User dbUser = dbUserList.get(0);
            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
        }

    }

}
