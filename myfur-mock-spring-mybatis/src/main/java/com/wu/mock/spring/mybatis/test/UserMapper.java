package com.wu.mock.spring.mybatis.test;

public interface UserMapper {

    void insertUser(User user);
    User getUser(Long id);
}
