package com.example.life_community.mapper;

import com.example.life_community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO QUESTION (title, description, gmt_create, gmt_modified, creator, tag) VALUES(#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    void create(Question question);

}
