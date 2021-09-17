package com.example.life_community.mapper;

import com.example.life_community.model.Question;
import org.apache.ibatis.annotations.Param;

public interface QuestionExtMapper {

    int incView(@Param("question") Question question);

    int intCommentCount(@Param("question") Question question);

}
