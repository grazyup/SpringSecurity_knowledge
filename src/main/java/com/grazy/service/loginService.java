package com.grazy.service;

import com.grazy.domain.ResponseResult;
import com.grazy.domain.User;

import java.util.Map;

/**
 * @Author: grazy
 * @Date: 2023/8/21 23:46
 * @Description:
 */
public interface loginService {

    ResponseResult<Map<String,String>> login(User user);
}
