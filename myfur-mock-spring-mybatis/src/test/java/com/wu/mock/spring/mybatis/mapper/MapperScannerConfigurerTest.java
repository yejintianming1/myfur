package com.wu.mock.spring.mybatis.mapper;

import com.wu.mock.spring.mybatis.test.User;
import com.wu.mock.spring.mybatis.test.UserMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MapperScannerConfigurerTest {

    @Test
    public void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("mapper/spring-mybatis-config.xml");
//        MapperScannerConfigurer bean = context.getBean(MapperScannerConfigurer.class);
//        System.out.println("----------end-----------");
        UserMapper userMapper = (UserMapper) context.getBean("userMapper");
        User user = userMapper.getUser(1L);
        System.out.println(user.getId()+"|"+user.getName()+"|"+user.getAge());

    }
}
