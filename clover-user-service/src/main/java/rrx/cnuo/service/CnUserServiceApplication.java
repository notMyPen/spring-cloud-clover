package rrx.cnuo.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;

/**
 * Feign和Ribbon选其一即可也可以同时使用，一般选Feign，Feign使用更优雅且内部也是用Ribbon实现的负载均衡
 * @author xuhongyu
 * @date 2019年6月27日
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@EnableCircuitBreaker
@EnableDistributedTransaction
//@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
//@ServletComponentScan//Servlet、Filter、Listener可以直接通过@WebServlet、@WebFilter、@WebListener注解自动注册，无需其他代码
public class CnUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CnUserServiceApplication.class, args);
	}

}
