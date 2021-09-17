package com.example.life_community.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {

    private Long parentId;
    private String content;
    private Integer type;

}
