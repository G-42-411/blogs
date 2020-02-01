package com.dreamland.mapper;

import com.dreamland.dto.QuestionQueryDTO;
import com.dreamland.pojo.Question;

import java.util.List;

public interface QuestionEXTMapper {

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}
