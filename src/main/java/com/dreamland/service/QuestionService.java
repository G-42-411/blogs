package com.dreamland.service;

import com.dreamland.exception.CustomizeErrorCode;
import com.dreamland.exception.CustomizeException;
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
            questionMapper.insert(question);
        }else {
            question.setGmtModified(System.currentTimeMillis());
            question.setId(id);
            int status = questionMapper.updateByPrimaryKey(question);

            if(status != 0){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }
}
