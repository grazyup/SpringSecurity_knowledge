package com.grazy;

import com.grazy.domain.User;
import com.grazy.mapper.MenuMapper;
import com.grazy.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class TokenDemoApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        User user = userMapper.selectById(1);
        System.out.println(user);
        System.out.println(passwordEncoder.encode("123456"));
    }

    @Autowired
    private MenuMapper mapper;

    @Test
    void testmybatis(){
        User user = new User();
        user.setId(2L);
        List<String> list = mapper.selectPermsByUserId(user);
        for(String el: list){
            System.out.println(el);
        }
    }

}
