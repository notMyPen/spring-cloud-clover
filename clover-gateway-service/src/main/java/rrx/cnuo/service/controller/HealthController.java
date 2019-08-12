package rrx.cnuo.service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import rrx.cnuo.service.utils.JwtUtil;

@RestController
@RefreshScope
public class HealthController {

	@Value("${test-string}")
	private String testString;
	
    @GetMapping("/config/test-string")
    public String testString(){
    	return testString;
    }
    
    @GetMapping("/health")
    public String health() {
        return " statis-service success";
    }

    @GetMapping("/getToken/{name}")
    public String get(@PathVariable("name") String name)  {
        return JwtUtil.generateToken(name);
    }
}
