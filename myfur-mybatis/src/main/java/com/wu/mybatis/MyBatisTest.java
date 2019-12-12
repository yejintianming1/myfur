package com.wu.mybatis;


import com.wu.mybatis.mapper.UserMapper;
import com.wu.mybatis.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MyBatisTest {

    public static void main(String[] args) throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatis/mybatis-config.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = build.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getUser(1L);
        System.out.println("name: "+user.getName()+"|age: "+user.getAge());
    }
}
