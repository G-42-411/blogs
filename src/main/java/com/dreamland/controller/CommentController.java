package com.dreamland.controller;

import com.dreamland.dto.CommentCreateDTO;
import com.dreamland.dto.CommentDTO;
import com.dreamland.dto.ResultDTO;
import com.dreamland.enums.CommentTypeEnum;
import com.dreamland.enums.NotificationTypeEnum;
import com.dreamland.exception.CustomizeErrorCode;
import com.dreamland.mapper.CommentMapper;
import com.dreamland.mapper.NotificationMapper;
import com.dreamland.pojo.Comment;
import com.dreamland.pojo.Notification;
import com.dreamland.pojo.User;
import com.dreamland.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {

        System.out.println(commentCreateDTO);
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorof(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return ResultDTO.errorof(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment,user);
        return ResultDTO.okof();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List> comments(@PathVariable("id") Long id) {
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okof(commentDTOS);
    }
}
