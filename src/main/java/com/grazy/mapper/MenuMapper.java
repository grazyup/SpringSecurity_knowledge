package com.grazy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grazy.domain.Menu;
import com.grazy.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: grazy
 * @Date: 2023/8/25 14:20
 * @Description:
 */

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据id查询用户权限
     * @param user 用户对象
     * @return 权限集合
     */
    List<String> selectPermsByUserId(@Param("user") User user);

}
