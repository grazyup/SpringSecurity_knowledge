package com.grazy.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.grazy.domain.LoginUser;
import com.grazy.domain.User;
import com.grazy.mapper.MenuMapper;
import com.grazy.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Author: grazy
 * @Date: 2023/8/21 15:53
 * @Description: 重写UserDetailsService的实现类，原先实现类会根据用户名在内存中查询；重写后，在数据库中查询用户信息
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        //查询用户信息
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUserName,s);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        //用户不存在抛异常
        if(Objects.isNull(user)){
            throw new RuntimeException("用户不存在或密码错误");
        }

        //根据用户查询权限信息 添加到LoginUser中
        List<String> list = menuMapper.selectPermsByUserId(user);

        //返回封装成UserDetails对象
        return new LoginUser(user,list);
    }
}
