package com.dreamland.controller;

import com.dreamland.mapper.QuestionMapper;
import com.dreamland.mapper.UserMapper;
import com.dreamland.pojo.Question;
import com.dreamland.pojo.User;
import com.dreamland.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class publishController {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;

    @GetMapping("/toEdit/{id}")
    public String toEdit(@PathVariable("id") Long id,
                         Model model){
        Question question = questionMapper.selectByPrimaryKey(id);
//        Question question = questionMapper.getById(id);
        model.addAttribute("id",question.getId());
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam(name = "id",required = false) Long id,
            HttpServletRequest request,
            Model model) {
        Question question = new Question();
//        User user = null;

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title == null || title == ""){
            model.addAttribute("msg","标题不能为空");
            return "/publish";
        }
        if(description == null || description == ""){
            model.addAttribute("msg","内容不能为空");
            return "/publish";
        }
        if(tag == null || tag == ""){
            model.addAttribute("msg","标签不能为空");
            return "/publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setCommentCount(0);
        question.setViewCount(0);
        question.setLikeCount(0);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionService.createOrUpdate(question,id);
//        questionMapper.create(question);
        return "redirect:/";
    }
}
