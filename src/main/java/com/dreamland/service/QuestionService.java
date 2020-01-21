package com.dreamland.service;

import com.dreamland.mapper.QuestionMapper;
import com.dreamland.pojo.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    @Autowired
    QuestionMapper questionMapper;

    public void createOrUpdate(Question question, Integer id) {
        if(id == null){
            questionMapper.create(question);
        }else {
            question.setGmtModified(System.currentTimeMillis());
            question.setId(id);
            questionMapper.update(question);
        }
    }
}
