package com.grazy.config;

import com.grazy.filter.JwtAuthenticationTokenFilter;
import com.grazy.handler.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author: grazy
 * @Date: 2023/8/21 16:26
 * @Description:  该部分的过滤配置只有对Security框架内部的过滤器链才起作用
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)   //开启权限相关配置
public class securityConfig extends WebSecurityConfigurerAdapter {

    //创建BCryptPasswordEncoder注入容器 会往  Spring 容器中添加一个名为 passwordEncoder【方法名】 的 Bean，该 Bean 即为方法的返回值
    @Bean
    public PasswordEncoder passwordEncoder(){
        //密码对比
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口，允许匿名访问, 但是在已登录状态（携带token）这个路径就访问不了
                .antMatchers("/user/login").anonymous()
                //基于配置的实现权限校验
                .antMatchers("/testCors").hasAuthority("system:dept:list")
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        //将自定义的token校验过滤器添加到springSecurity内部的过滤器链的某个过滤器之前
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //将框架中异常处理的实现类（自定义）配置到springSecurity中
        http.exceptionHandling()
                //认证失败处理器
                .authenticationEntryPoint(authenticationEntryPoint)
                //授权失败处理器
                .accessDeniedHandler(accessDeniedHandler);

        //配置springSecurity允许跨域
        http.cors();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
