package com.example.life_community.controller;

import com.alibaba.fastjson.JSON;
import com.example.life_community.mapper.UserMapper;
import com.example.life_community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length>0) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("loginToken")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    System.out.println("user JSON：" + JSON.toJSONString(user));
                    if(user != null){
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        return "index";
    }

}
