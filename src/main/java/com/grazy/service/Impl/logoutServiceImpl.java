package com.grazy.service.Impl;

import com.grazy.domain.LoginUser;
import com.grazy.domain.ResponseResult;
import com.grazy.service.logoutService;
import com.grazy.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @Author: grazy
 * @Date: 2023/8/22 23:55
 * @Description:
 */

@Service
public class logoutServiceImpl implements logoutService {

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult<String> logout() {

        //从SecurityContextHolder中获取用户id（因为当前的请求会执行token校验过滤器，ContextHolder也会存入值，所以可以获取用户信息）
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long id = loginUser.getUser().getId();
        //删除Redis中对应的值
        redisCache.deleteObject("login:" + id);  //此处不需要删除ContextHolder是因为，新的请求就是新的的线程，所以ContextHolder是新的的，不需要删除；
        return new ResponseResult<>(200,"注销成功");
    }
}
