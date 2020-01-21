package com.dreamland.controller;

import com.dreamland.dto.PaginationDTO;
import com.dreamland.service.QuestionDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {

    @Autowired
    private QuestionDTOService questionDTOService;

    @RequestMapping("/")
    public String hello(Model model,
                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "size", defaultValue = "2") Integer size) {

        PaginationDTO pagination = questionDTOService.list(page, size);
        model.addAttribute("pagination", pagination);

        return "index";
    }
}
