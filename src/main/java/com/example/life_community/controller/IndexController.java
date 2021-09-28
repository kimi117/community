package com.example.life_community.controller;

import com.example.life_community.dto.PaginationDTO;
import com.example.life_community.mapper.UserMapper;
import com.example.life_community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

// 首页
@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @GetMapping("/")
    public String index(@RequestParam(name = "page", defaultValue = "1") Integer page, @RequestParam(name = "size", defaultValue = "5") Integer size, @RequestParam(name = "search", required = false) String search, HttpServletRequest request, Model model) {

        PaginationDTO paginationDTO = questionService.list(search, page, size);
        model.addAttribute("paginationDTO", paginationDTO);
        model.addAttribute("search", search);

        return "index";
    }

}
