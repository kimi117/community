package com.example.life_community.mapper;

import com.example.life_community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO QUESTION (title, description, gmt_create, gmt_modified, creator, tag) VALUES(#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    void create(Question question);

    @Select("SELECT * FROM QUESTION LIMIT #{offSet}, #{size}")
    List<Question> list(@Param("offSet") Integer offSet, @Param("size") Integer size);

    @Select("SELECT count(1) FROM QUESTION")
    Integer count();

}
