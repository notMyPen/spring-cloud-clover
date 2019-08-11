package rrx.cnuo.service.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import rrx.cnuo.service.accessory.config.PayCenterConfigBean;

@Api("订单相关资源操作(演示Ribbon和Feign的区别)")
@RestController
//@RefreshScope//必须在需要刷新的配置类中加这个注解才会刷新
public class HealthController {

	/*@Autowired
	private UserFeignService userFeignService;*/
	
	@Autowired private PayCenterConfigBean payCenterConfigBean;
	 
	/**
	 * 注意：当返回值为List<Map<String,Object>的时候，页面会提示error信息，这是因为swagger2不能识别当前返回值，swagger对泛型的支持很弱
	 * @author xuhongyu
	 * @return
	 */
	@ApiOperation(value = "获取订单列表",httpMethod = "GET")
	@ApiResponse(code = 200,message = "success",response = List.class)
	@GetMapping("/list")
	public List<String> list(){
		return Arrays.asList(new String[]{"订单1","订单2"});
	}
	
	/*@ApiOperation(value = "获取用户基本信息(用户测试feign)",httpMethod = "GET")
	@GetMapping("/userBasicInfo")
	public String userBasicInfo(){
		return userFeignService.getBasicInfo("12132");
	}*/
	
    @GetMapping("/config/test-string")
    public String testString(){
    	return payCenterConfigBean.getTestString();
    }
    
    @GetMapping("/health")
    public String health() {
        return "order-service success";
    }
    
}
