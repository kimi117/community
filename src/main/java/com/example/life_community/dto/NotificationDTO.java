package com.example.life_community.dto;

import com.example.life_community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {


    private Integer id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Integer outerId;
    private String typeName;
    private Integer type;

}
