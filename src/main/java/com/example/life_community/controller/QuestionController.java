package com.example.life_community.controller;

import com.example.life_community.dto.CommentDTO;
import com.example.life_community.dto.QuestionDTO;
import com.example.life_community.enums.CommentTypeEnum;
import com.example.life_community.service.CommentService;
import com.example.life_community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Long id, Model model) {
        // 问题信息
        QuestionDTO questionDTO = questionService.getById(id.intValue());

        List<QuestionDTO> relatedQuestionList = questionService.selectReleated(questionDTO);

        List<CommentDTO> commentDTOList = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        // 累加阅读数
        questionService.incView(id.intValue());

        model.addAttribute("questionDTO", questionDTO);
        model.addAttribute("commentDTOList", commentDTOList);
        model.addAttribute("relatedQuestionList", relatedQuestionList);
        System.out.println("relatedQuestionList：" + relatedQuestionList);
        return "question";
    }

}
