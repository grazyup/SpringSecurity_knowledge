package com.grazy.handler;

import com.alibaba.fastjson.JSON;
import com.grazy.domain.ResponseResult;
import com.grazy.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: grazy
 * @Date: 2023/8/26 16:06
 * @Description: 权限失败处理器实现类
 */

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        //创建响应类
        ResponseResult<Object> result = new ResponseResult<>(HttpStatus.FORBIDDEN.value(),"您当前权限不足");
        //将JSON对象转换为String
        String json = JSON.toJSONString(result);
        //调用web工具类响应字符串信息到前端
        WebUtils.renderString(httpServletResponse,json);
    }
}
