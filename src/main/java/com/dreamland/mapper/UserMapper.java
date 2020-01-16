package com.dreamland.mapper;

import com.dreamland.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modified)values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    public void insert(User user);
}
