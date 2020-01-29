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

    public void createOrUpdate(Question question, Long id) {
        Question dbQuestion = questionMapper.selectByPrimaryKey(id);
        if(dbQuestion == null){
            questionMapper.insert(question);
        }else{
            dbQuestion.setTitle(question.getTitle());
            dbQuestion.setTag(question.getTag());
            dbQuestion.setDescription(question.getDescription());
            dbQuestion.setGmtModified(System.currentTimeMillis());
            int status = questionMapper.updateByPrimaryKey(dbQuestion);

            if(status == 0){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }
}
