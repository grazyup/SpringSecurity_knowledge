package com.grazy.service.Impl;

import com.grazy.domain.LoginUser;
import com.grazy.domain.ResponseResult;
import com.grazy.domain.User;
import com.grazy.service.loginService;
import com.grazy.utils.JwtUtil;
import com.grazy.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: grazy
 * @Date: 2023/8/21 23:46
 * @Description:
 */

@Service
public class loginServiceImpl implements loginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult<Map<String,String>> login(User user) {

        //AuthenticationManager 的 authenticate方法进行认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);  //之后会按照流程自己调用下面的类的方法，最后调用到重写的UserDetailsServiceImpl的loadUserByUsername()方法，然后开始往回走，封装成一个Authentication对象

        //如果认证没通过，给出对应的提示
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }

        //认证通过，视同useID生成一个jwt jwt存入ResponseResult中返回、
        LoginUser loginUser = (LoginUser)authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);  //把token响应前端
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);
        //把完整的用户信息存入Redis中，userID作为key
        redisCache.setCacheObject("login:" + userId,loginUser);
        return new ResponseResult<>(200,"登陆成功",map);
    }
}
