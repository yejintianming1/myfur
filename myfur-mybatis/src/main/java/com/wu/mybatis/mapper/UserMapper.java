package com.wu.mybatis.mapper;

import com.wu.mybatis.model.User;

public interface UserMapper {

    void insertUser(User user);
    User getUser(Long id);
}
