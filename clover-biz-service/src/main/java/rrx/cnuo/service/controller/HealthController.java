package rrx.cnuo.service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope//必须在需要刷新的配置类中加这个注解才会刷新
public class HealthController{

//	@Autowired private StatisUserMapper statisUserMapper;
	
	@Value("${test-string}")
	private String testString;
	
    @GetMapping("/config/test-string")
    public String testString(){
    	/*StatisUser record = new StatisUser();
    	record.setUid(1212L);
    	record.setName((short) 121);
    	record.setValue(6000L);
    	statisUserMapper.insertSelective(record);*/
    	return testString;
    }
    
    @GetMapping("/health")
    public String health() {
        return " statis-service success";
    }
}
