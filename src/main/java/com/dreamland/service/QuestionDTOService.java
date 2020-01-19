package com.dreamland.service;

import com.dreamland.dto.PaginationDTO;
import com.dreamland.dto.QuestionDTO;
import com.dreamland.mapper.QuestionMapper;
import com.dreamland.mapper.UserMapper;
import com.dreamland.pojo.Question;
import com.dreamland.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionDTOService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        Integer totalCount = questionMapper.count();
        Integer questionCount = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;

        if (page < 1)
            page = 1;
        if (page > questionCount)
            page = questionCount;
        Integer offset = 0;
        if(page != 0){
            offset = size * (page - 1);
        }
        List<Question> questions = questionMapper.List(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        paginationDTO.setPagination(totalCount, page, size);
        return paginationDTO;
    }
    public PaginationDTO listByUserId(Integer page, Integer size, Integer userId) {

        Integer totalCount = questionMapper.countByUserId(userId);
        Integer questionCount = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
        if (page < 1)
            page = 1;
        if (page > questionCount)
            page = questionCount;
        Integer offset = 0;
        if(page != 0) {
            offset = size * (page - 1);
        }
        List<Question> questions = questionMapper.ListByUserId(offset, size,userId);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        paginationDTO.setPagination(totalCount, page, size);
        return paginationDTO;
    }
}
