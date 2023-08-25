package com.grazy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grazy.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: grazy
 * @Date: 2023/8/21 15:33
 * @Description:
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
