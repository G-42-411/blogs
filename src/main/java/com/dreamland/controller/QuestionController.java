package com.dreamland.controller;

import com.dreamland.dto.CommentDTO;
import com.dreamland.dto.QuestionDTO;
import com.dreamland.service.CommentService;
import com.dreamland.service.QuestionDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionDTOService questionDTOService;
    @Autowired
    CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id,
                           Model model){
        questionDTOService.addViewCount(id);
        QuestionDTO questionDTO = questionDTOService.getById(id);

        List<CommentDTO> comments = commentService.listByQuestionId(id);

        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        return "question";
    }
}
