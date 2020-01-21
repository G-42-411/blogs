package com.dreamland.mapper;

import com.dreamland.pojo.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset}, #{size}")
    List<Question> List(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select * from question where creator = #{userId} limit #{offset}, #{size}")
    List<Question> ListByUserId(@Param("offset") Integer offset, @Param("size") Integer size, @Param("userId") Integer userId);

    @Select("select count(1) from question")
    Integer count ();

    @Select("select count(creator) from question where creator = #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Integer id);

    @Select("select * from question where creator = #{creator}")
    Question getCreator(@Param("creator") Integer creator);

    @Update("update question set title = #{title}, description = #{description}, gmt_modified = #{gmtModified}, tag = #{tag} where id = #{id}")
    void update(Question question);
}
