package com.example.life_community.service;

import com.example.life_community.dto.CommentDTO;
import com.example.life_community.enums.CommentTypeEnum;
import com.example.life_community.enums.NotificationStatusEnum;
import com.example.life_community.enums.NotificationTypeEnum;
import com.example.life_community.exception.CustomizeException;
import com.example.life_community.exception.ECustomizeErrorCode;
import com.example.life_community.mapper.*;
import com.example.life_community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CommentExtMapper commentExtmapper;

    @Autowired
    NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment, User user) {
        if(comment.getParentId()==null || comment.getParentId()==0) {
            throw new CustomizeException(ECustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if(comment.getType()==null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(ECustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if(comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            // 回复评论
            System.out.println("回复评论");
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null) throw new CustomizeException(ECustomizeErrorCode.COMMENT_NOT_FOUND);
            System.out.println("insert comment");
            commentMapper.insert(comment);

            // 回复
            Question dbQuestion = questionMapper.selectByPrimaryKey(dbComment.getParentId().intValue());
            if(dbQuestion == null) throw new CustomizeException(ECustomizeErrorCode.QUESTION_NOT_FOUND);

            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtmapper.incCommentCount(parentComment);

            // 通知
            createNotify(comment, dbComment.getCommentator(), user.getName(), dbQuestion.getTitle(), NotificationTypeEnum.REPLY_COMMENT.getType(), dbQuestion.getId());

        } else {
            // 回复问题
            System.out.println("回复问题");
            Question dbQuestion = questionMapper.selectByPrimaryKey(comment.getParentId().intValue());
            if(dbQuestion == null) throw new CustomizeException(ECustomizeErrorCode.QUESTION_NOT_FOUND);
            System.out.println("insert question");
            commentMapper.insert(comment);

            dbQuestion.setCommentCount(1);
            questionExtMapper.intCommentCount(dbQuestion);

            // 通知
            createNotify(comment, dbQuestion.getCreator(), user.getName(), dbQuestion.getTitle(), NotificationTypeEnum.REPLY_QUESTION.getType(), dbQuestion.getId());

        }
    }

    // 回复评论通知
    private void createNotify(Comment comment, Integer receiver, String notifierName, String outerTitle, Integer type, int outerId) {
        Notification notafication = new Notification();
        notafication.setGmtCreate(System.currentTimeMillis());
        notafication.setType(type);
        notafication.setOuterId(outerId);
        notafication.setNotifier(comment.getCommentator());
        notafication.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notafication.setReceiver(receiver);
        notafication.setNotifierName(notifierName);
        notafication.setOuterTitle(outerTitle);
        notificationMapper.insert(notafication);
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        System.out.println("type：" + type.getType());
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());
        commentExample.setOrderByClause(" GMT_CREATE DESC");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if(comments.size() == 0) {
            return new ArrayList<>();
        }

        // 获取去重的评论人
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        // 获取评论人并转换为 Map
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);

        List<User> users = userMapper.selectByExample(userExample);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        // 转换 comment 为 commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;

    }
}
