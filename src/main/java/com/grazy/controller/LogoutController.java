package com.grazy.controller;

import com.grazy.domain.ResponseResult;
import com.grazy.service.logoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: grazy
 * @Date: 2023/8/22 23:50
 * @Description:
 */

@RestController
public class LogoutController {

    @Autowired
    private logoutService logoutService;

    @RequestMapping("user/logout")
    public ResponseResult<String> logout(){
        return logoutService.logout();
    }
}
