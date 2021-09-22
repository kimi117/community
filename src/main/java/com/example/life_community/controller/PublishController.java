package com.example.life_community.controller;

import com.example.life_community.cache.TagCache;
import com.example.life_community.dto.QuestionDTO;
import com.example.life_community.mapper.QuestionMapper;
import com.example.life_community.mapper.UserMapper;
import com.example.life_community.model.Question;
import com.example.life_community.model.User;
import com.example.life_community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

// 发布
@Controller
public class PublishController {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;

    // 跳转指定页面
    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tagList", TagCache.get());
        return "publish";
    }

    // 表单提交请求
    @PostMapping("/publish")
    public String doPublish(@RequestParam(name = "id", required = false) Integer id, @RequestParam(name = "title", required = false) String title, @RequestParam(name = "description", required = false) String description, @RequestParam(name = "tag", required = false) String tag, HttpServletRequest request, Model model) {

        model.addAttribute("id", id);
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

        String invalid = TagCache.filterInvalid(tag);
        if(StringUtils.isNotEmpty(invalid)) {
            model.addAttribute("error", "输入非法标签：" + invalid);
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");

        if(user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setId(id);// 更新时候才有
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());

//        questionMapper.create(question);
        questionService.createOrUpdate(question);

        return "redirect:/";
    }

    // 跳转编辑页面，回显问题数据
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id, Model model) {

        QuestionDTO questionDTOInfo = questionService.getById(id);
        model.addAttribute("questionDTOInfo", questionDTOInfo);
        model.addAttribute("tagList", TagCache.get());
        return "publish";
    }

}
