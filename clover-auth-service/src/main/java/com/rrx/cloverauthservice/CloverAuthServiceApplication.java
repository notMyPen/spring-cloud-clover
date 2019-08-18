package com.rrx.cloverauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
public class CloverAuthServiceApplication extends WebSecurityConfigurerAdapter{

	public static void main(String[] args) {
		SpringApplication.run(CloverAuthServiceApplication.class, args);
	}

	/**
	 * 手动注入AauthenticationManager
	 */
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

	/**
	 * 声明用户admin具有读写权限，用户guest具有只读权限
	 */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
        .inMemoryAuthentication()
        .withUser("guest").password("guest").authorities("WRIGTH_READ")
        .and()
        .withUser("admin").password("admin").authorities("WRIGTH_READ", "WRIGTH_WRITE");
    }
    
    /**
     * 用于声明用户名和密码的加密方式(这个功能在Spring Security5之前是没有的)
     * @return
     */
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
      return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}
