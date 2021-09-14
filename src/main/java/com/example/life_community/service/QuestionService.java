package com.example.life_community.service;

import com.alibaba.fastjson.JSON;
import com.example.life_community.dto.PaginationDTO;
import com.example.life_community.dto.QuestionDTO;
import com.example.life_community.mapper.QuestionMapper;
import com.example.life_community.mapper.UserMapper;
import com.example.life_community.model.Question;
import com.example.life_community.model.QuestionExample;
import com.example.life_community.model.User;
import org.apache.ibatis.session.RowBounds;
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
        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());// 查找数据库获取总数

        Integer totalPage;
        // 计算总页码
        if(totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        // 容错
        if(page < 1) page = 1;
        if(page > totalPage) page = totalPage;

        paginationDTO.setPagination(totalPage, page);

        Integer offSet = size * (page - 1);

//        List<Question> questionList = questionMapper.selectByExample(new QuestionExample());
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offSet, size));

        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();

        for (Question question : questionList) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();

            // Spring 内置 BenUtils
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestionDTOList(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
//        Integer totalCount = questionMapper.countByUserId(userId);// 查找数据库获取总数
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(questionExample);// 查找数据库获取总数

        Integer totalPage;
        // 计算总页码
        if(totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        // 容错
        if(page < 1) page = 1;
        if(page > totalPage) page = totalPage;

        paginationDTO.setPagination(totalPage, page);

        Integer offSet = size * (page - 1);

//        List<Question> questionList = questionMapper.listByUserId(userId, offSet, size);
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offSet, size));

        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();

        for (Question question : questionList) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();

            // Spring 内置 BenUtils
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestionDTOList(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question  = questionMapper.selectByPrimaryKey(id);
        if(question != null) {

            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            System.out.println("lzb questionDTO：" + JSON.toJSONString(questionDTO));

            // 获取 User
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            questionDTO.setUser(user);

            return questionDTO;
        } else {
            System.out.println("lzb question：" + JSON.toJSONString(question));
        }
        return null;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null) {
            // 新增
            System.out.println("新增");
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.insert(question);
        } else {
            // 更新
            System.out.println("修改");
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());

            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(updateQuestion, questionExample);
        }
    }
}
