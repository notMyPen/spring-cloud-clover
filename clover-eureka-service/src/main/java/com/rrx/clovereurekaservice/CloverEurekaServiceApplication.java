package com.rrx.clovereurekaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CloverEurekaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloverEurekaServiceApplication.class, args);
	}

}
