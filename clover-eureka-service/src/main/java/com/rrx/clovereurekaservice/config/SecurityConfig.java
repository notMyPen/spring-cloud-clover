package com.rrx.clovereurekaservice.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 由于spring-boot-starter-security开启了csrf校验，对于client这种非界面应用来说不合适，但是又没有配置文件可以禁用，需要通过Java配置方法禁用端点的安全校验
 * @author xuhongyu
 * @date 2019年8月9日
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf().disable();
    }
}
