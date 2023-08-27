package com.grazy.expression;

import com.grazy.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: grazy
 * @Date: 2023/8/27 18:43
 * @Description: 自定义的权限校验方法
 */

@Component("ex")   //给bean取名字
public class oneselfExpressionRoot{

    public boolean hasAuthority(String authority){
        //通过ContextHolder获取当前Authentication对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取用户信息
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取权限集合
        List<String> permissions = loginUser.getPermissions();
        //判断用户权限集合中是否存在传入的参数权限
        return permissions.contains(authority);
    }

}
