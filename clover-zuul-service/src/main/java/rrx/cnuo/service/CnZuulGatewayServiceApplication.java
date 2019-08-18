package rrx.cnuo.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.stereotype.Component;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import com.netflix.zuul.monitoring.MonitoringHelper;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@EnableCircuitBreaker
//@EnableOAuth2Sso
public class CnZuulGatewayServiceApplication /* extends WebSecurityConfigurerAdapter */{

	public static void main(String[] args) {
		SpringApplication.run(CnZuulGatewayServiceApplication.class, args);
	}

	/**
	 * Groovy加载方法配置：用于注册GroovyFilter.groovy，20秒自动刷新
	 * @author xuhongyu
	 * @date 2019年8月17日
	 */
    @Component
    public static class GroovyRunner implements CommandLineRunner {
        @Override
        public void run(String... args) throws Exception {
            MonitoringHelper.initMocks();
            FilterLoader.getInstance().setCompiler(new GroovyCompiler());
            try {
                FilterFileManager.setFilenameFilter(new GroovyFileFilter());
                FilterFileManager.init(20, "/WorkFile/spring-cloud-clover/clover-zuul-service/src/main/java/rrx/cnuo/service/groovy");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    /**
     * 重写WebSecurityConfigurerAdapter适配器的configure方法，声明需要鉴权的url：<br>
     * 当请求接口时判断是否登录鉴权，如果未登录则跳转到auth-server的登录界面(这里使用Spring security OAuth的默认登录页面，也可以重写相关代码重新定制页面)，<br>
     * 登录成功后auth-server颁发jwt token，zuul在访问下游服务时将jwt token返给header里
     */
//    @Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//		.authorizeRequests()
//		.antMatchers("/login", "/client/**")
//		.permitAll()
//		.anyRequest()
//		.authenticated()
//		.and()
//		.csrf()
//		.disable();
//	}
}
