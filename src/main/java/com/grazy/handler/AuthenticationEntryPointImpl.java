package com.grazy.handler;

import com.alibaba.fastjson.JSON;
import com.grazy.domain.ResponseResult;
import com.grazy.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: grazy
 * @Date: 2023/8/26 16:10
 * @Description: 认证失败处理器实现类
 */

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //创建响应类
        ResponseResult<Object> result = new ResponseResult<>(HttpStatus.UNAUTHORIZED.value(),"认证失败，请重新登录");
        //将JSON对象转换为String
        String json = JSON.toJSONString(result);
        //调用web工具类响应字符串信息到前端
        WebUtils.renderString(httpServletResponse,json);
    }
}
