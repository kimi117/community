package com.example.life_community.controller;

import com.alibaba.fastjson.JSON;
import com.example.life_community.dto.NotificationDTO;
import com.example.life_community.dto.PaginationDTO;
import com.example.life_community.enums.NotificationTypeEnum;
import com.example.life_community.model.User;
import com.example.life_community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Long id , Model model, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");

        if(user == null) return "redirect:/";

        NotificationDTO notificationDTO = notificationService.read(id, user);
        if(NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType() || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
            System.out.println("notificationDTO：" + JSON.toJSONString(notificationDTO));
            return "redirect:/question/" + notificationDTO.getOuterId();
        } else {
            return "redirect:/";
        }

    }

}
