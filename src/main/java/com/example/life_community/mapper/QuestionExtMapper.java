package com.example.life_community.mapper;

import com.example.life_community.dto.QuestionQueryDTO;
import com.example.life_community.model.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionExtMapper {

    int incView(@Param("question") Question question);

    int intCommentCount(@Param("question") Question question);

    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}
