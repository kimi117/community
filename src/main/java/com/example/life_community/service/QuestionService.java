package com.example.life_community.service;

import com.example.life_community.dto.PaginationDTO;
import com.example.life_community.dto.QuestionDTO;
import com.example.life_community.mapper.QuestionMapper;
import com.example.life_community.mapper.UserMapper;
import com.example.life_community.model.Question;
import com.example.life_community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();// 查找数据库获取总数
        paginationDTO.setPagination(totalCount, page, size);

        // 容错
        if(page < 1) page = 1;
        if(page > paginationDTO.getTotalPage()) page = paginationDTO.getTotalPage();

        Integer offSet = size * (page - 1);

        List<Question> questionList = questionMapper.list(offSet, size);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();

        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();

            // Spring 内置 BenUtils
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestionDTOList(questionDTOList);
        return paginationDTO;
    }
}
