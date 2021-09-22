package com.example.life_community.controller;

import com.alibaba.fastjson.JSON;
import com.example.life_community.dto.CommentCreateDTO;
import com.example.life_community.dto.CommentDTO;
import com.example.life_community.dto.ResultDTO;
import com.example.life_community.enums.CommentTypeEnum;
import com.example.life_community.exception.ECustomizeErrorCode;
import com.example.life_community.model.Comment;
import com.example.life_community.model.User;
import com.example.life_community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 评论问题
     * @param commentDTO
     *          {
     *             "parentId":16,
     *             "content":"这事一个回复内容",
     *             "type":1
     *          }
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/comment")
    // 通过 @RequestBody 接收 JSON 格式数据变成对象
    public Object post(@RequestBody CommentCreateDTO commentDTO, HttpServletRequest request) {

        System.out.println("commentDTO：" + JSON.toJSONString(commentDTO));

        User user = (User) request.getSession().getAttribute("user");
        if(user == null) {
            return ResultDTO.errorOf(ECustomizeErrorCode.NO_LOGIN);
        }

        // StringUtils 引用 apache commons lang
        if(commentDTO==null || StringUtils.isEmpty(commentDTO.getContent())) {
            return ResultDTO.errorOf(ECustomizeErrorCode.COMTENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0l);

        commentService.insert(comment);

        System.out.println("SUCCESS");

        return ResultDTO.okOf();// @ResponseBody 将对象自动序列化成 JSON 返回
    }

    /**
     * 查找评论-评论
     * @param id 评论 Id
     * @return
     */
    @ResponseBody
    @GetMapping("/comment/{id}")
    public ResultDTO comments(@PathVariable("id") Long id) {
        List<CommentDTO> commentDTOList = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOList);
    }

}
