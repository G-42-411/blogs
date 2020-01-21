package com.dreamland.mapper;

import com.dreamland.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modified,bio,avatar_url)values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(String token);

    @Select("select * from user where id = #{id}")
    User findById(Integer id);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update({"update user set name = #{name}, token = #{token}, gmt_modified = #{gmtModified}, bio = #{bio}, avatar_url = #{avatarUrl} where account_id = #{accountId}"})
    void update(User user);
}
