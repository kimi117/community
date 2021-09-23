package com.example.life_community.service;

import com.alibaba.fastjson.JSON;
import com.example.life_community.dto.NotificationDTO;
import com.example.life_community.dto.PaginationDTO;
import com.example.life_community.enums.NotificationStatusEnum;
import com.example.life_community.enums.NotificationTypeEnum;
import com.example.life_community.exception.CustomizeException;
import com.example.life_community.exception.ECustomizeErrorCode;
import com.example.life_community.mapper.NotificationMapper;
import com.example.life_community.mapper.UserMapper;
import com.example.life_community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    UserMapper userMapper;

    public PaginationDTO list(Integer userId, Integer page, Integer size) {

        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<NotificationDTO>();
//        Integer totalCount = questionMapper.countByUserId(userId);// 查找数据库获取总数
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId);
        Integer totalCount = (int) notificationMapper.countByExample(example);// 查找数据库获取总数

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
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        List<Notification> notificationList = notificationMapper.selectByExampleWithRowbounds(notificationExample, new RowBounds(offSet, size));

        if(notificationList.size() == 0) {
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOList = new ArrayList<NotificationDTO>();
        for(Notification notification : notificationList) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOList.add(notificationDTO);
        }

        paginationDTO.setData(notificationDTOList);
        return paginationDTO;

    }

    public Long unreadCount(Integer userId) {
        NotificationExample notificationEample = new NotificationExample();
        notificationEample.createCriteria().andReceiverEqualTo(userId).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationEample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id.intValue());
        System.out.println("notification：" + JSON.toJSONString(notification));
        if(notification.getReceiver() == null) {
            throw new CustomizeException(ECustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        } else if(!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(ECustomizeErrorCode.READ_NOTIFICAtioN_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        System.out.println("notificationDTO：" + notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;

    }
}
