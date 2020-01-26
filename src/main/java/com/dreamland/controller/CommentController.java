package com.dreamland.controller;

import com.dreamland.dto.CommentDTO;
import com.dreamland.dto.ResultDTO;
import com.dreamland.enums.CommentTypeEnum;
import com.dreamland.exception.CustomizeErrorCode;
import com.dreamland.mapper.CommentMapper;
import com.dreamland.pojo.Comment;
import com.dreamland.pojo.User;
import com.dreamland.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    CommentService commentService;

    @RequestMapping("/comment")
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){

        User user = (User) request.getAttribute("user");
        if (user == null){
            return ResultDTO.errorof(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okof();
    }
}
