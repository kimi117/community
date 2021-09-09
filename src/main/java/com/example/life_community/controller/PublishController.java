package com.example.life_community.controller;

import com.alibaba.fastjson.JSON;
import com.example.life_community.mapper.QuestionMapper;
import com.example.life_community.mapper.UserMapper;
import com.example.life_community.model.Question;
import com.example.life_community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;

    // 跳转指定页面
    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    // 表单提交请求
    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title, @RequestParam("description") String description, @RequestParam("tag") String tag, HttpServletRequest request, Model model) {

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        if(title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if(description == null || description == "") {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if(tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("loginToken")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if(user != null && user.getId() != null){
                        System.out.println("user JSON：" + JSON.toJSONString(user));
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        if(user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(System.currentTimeMillis());

        questionMapper.create(question);

        return "redirect:/";
    }

}
