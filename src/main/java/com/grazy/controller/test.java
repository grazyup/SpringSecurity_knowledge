package com.grazy.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: grazy
 * @Date: 2023/8/22 22:57
 * @Description: 测试未携带token是否能够请求成功
 */

@RestController
public class test {

    @PostMapping("/hello")
    @PreAuthorize("hasAuthority('system:dept:list')")   //必须具有这个权限才能访问
    public String hello(){
        return "hello";
    }
}
