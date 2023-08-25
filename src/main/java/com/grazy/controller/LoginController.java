package com.grazy.controller;

import com.grazy.domain.ResponseResult;
import com.grazy.domain.User;
import com.grazy.service.loginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: grazy
 * @Date: 2023/8/21 22:44
 * @Description:
 */

@RestController
public class LoginController {

    @Autowired
    private loginService loginService;

    @PostMapping(value = "/user/login")
    public ResponseResult<Map<String,String>> login(@RequestBody User user){
        return loginService.login(user);
    }
}
