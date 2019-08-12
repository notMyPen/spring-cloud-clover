package rrx.cnuo.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CnGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CnGatewayServiceApplication.class, args);
	}

}
