package com.grazy.filter;

import com.grazy.domain.LoginUser;
import com.grazy.utils.JwtUtil;
import com.grazy.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author: grazy
 * @Date: 2023/8/22 21:53
 * @Description: 这个过滤器作用是检查接口请求中是否带有token，是否登录账号
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = httpServletRequest.getHeader("token");
        //判断token是否存在
        if(!StringUtils.hasText(token)){
            //没有token存在，放行  --> 此处放行后后面还会有过滤器继续认证
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        //解析token
        String userId = null;
        try {
            //解析获取userId
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            //出现异常代表解析的token是错误的非法的
            throw new RuntimeException("非法token");
        }

        //根据userId在Redis中获取用户信息
        LoginUser loginUser = redisCache.getCacheObject("login:" + userId);
        if(Objects.isNull(loginUser)){
            throw new RuntimeException("用户未登录");
        }

        //将用户信息存入SecurityContextHolder中（因为每个请求都是一个线程，每次请求都是新的ContextHolder，所以每次请求都要存入authentication对象到ContextHolder中）
        UsernamePasswordAuthenticationToken authenticationToken =
                //使用三个参数的方法是为了最后一个参数能够设置为已认证状态，第三个参数是将用户的权限信息封装到Authentication中
                new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //成功解析token并放行
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
