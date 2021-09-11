package com.example.life_community.mapper;

import com.example.life_community.dto.QuestionDTO;
import com.example.life_community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO QUESTION (title, description, gmt_create, gmt_modified, creator, tag) VALUES(#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    void create(Question question);

    @Select("SELECT * FROM QUESTION ORDER BY GMT_CREATE DESC LIMIT #{offSet}, #{size}")
    List<Question> list(@Param("offSet") Integer offSet, @Param("size") Integer size);

    @Select("SELECT * FROM QUESTION WHERE creator = #{userId} LIMIT #{offSet}, #{size}")
    List<Question> listByUserId(@Param("userId") Integer userId, @Param("offSet") Integer offSet, @Param("size") Integer size);

    @Select("SELECT count(1) FROM QUESTION")
    Integer count();

    @Select("SELECT count(1) FROM QUESTION WHERE creator = #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("SELECT * FROM QUESTION WHERE id = #{id}")
    Question getById(Integer id);

    @Update("UPDATE QUESTION SET title = #{title}, description = #{description}, gmt_modified = #{gmtModified}, tag = #{tag} WHERE id = #{id}")
    void update(Question question);
}
