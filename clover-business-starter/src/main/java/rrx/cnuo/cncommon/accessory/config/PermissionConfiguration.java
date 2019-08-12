package rrx.cnuo.cncommon.accessory.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import rrx.cnuo.cncommon.accessory.intercepter.RestTemplateUserContextInterceptor;
import rrx.cnuo.cncommon.accessory.intercepter.UserContextInterceptor;

/**
 * 将鉴权拦截器注入SpringIOC容器中
 * @author xuhongyu
 * @date 2019年8月12日
 */
@Configuration
@EnableWebMvc
public class PermissionConfiguration implements WebMvcConfigurer{
	
	/**
	 * 请求拦截器
	 */
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UserContextInterceptor());	
    }
	
	/**
	 * RestTemplate 拦截器，在发送请求前设置鉴权的用户上下文信息
	 */
	@LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new RestTemplateUserContextInterceptor());
        return restTemplate;
    }
   
}
