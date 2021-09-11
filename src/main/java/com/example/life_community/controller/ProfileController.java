package com.example.life_community.controller;

import com.alibaba.fastjson.JSON;
import com.example.life_community.dto.PaginationDTO;
import com.example.life_community.mapper.UserMapper;
import com.example.life_community.model.User;
import com.example.life_community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

// 个人资料发布操作
@Controller
public class ProfileController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action, @RequestParam(name = "page", defaultValue = "1") Integer page, @RequestParam(name = "size", defaultValue = "3") Integer size, Model model, HttpServletRequest request) {

//        Cookie[] cookies = request.getCookies();
//        User user = null;
//        if(cookies!=null && cookies.length>0) {
//            for (Cookie cookie : cookies) {
//                if(cookie.getName().equals("loginToken")) {
//                    String token = cookie.getValue();
//                    user = userMapper.findByToken(token);
//                    System.out.println("user JSON：" + JSON.toJSONString(user));
//                    if(user != null){
//                        request.getSession().setAttribute("user", user);
//                    }
//                    break;
//                }
//            }
//        }

        User user = (User) request.getSession().getAttribute("user");

        if(user == null) return "redirect:/";

        System.out.println("action：" + action);
        if("question".equals(action)) {
            model.addAttribute("section", "question");
            model.addAttribute("sectionName", "我的提问");
        } else if("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }

            PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
        model.addAttribute("paginationDTO", paginationDTO);

        return "profile";
    }

}
