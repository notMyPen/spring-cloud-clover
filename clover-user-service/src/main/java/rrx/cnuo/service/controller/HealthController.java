package rrx.cnuo.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import rrx.cnuo.cncommon.vo.order.TradeVo;
import rrx.cnuo.service.accessory.config.UserConfigBean;
import rrx.cnuo.service.service.TestDistributedTxService;

@Api("用户相关资源操作(演示Ribbon和Feign的区别)")
@RestController
@RefreshScope//必须在需要刷新的配置类中加这个注解才会刷新
public class HealthController {

	@Autowired private UserConfigBean userConfigBean;
	@Autowired private TestDistributedTxService testDistributedTxService;
	
    @GetMapping("/config/test-string")
    public String testString(){
    	return userConfigBean.getTestString();
    }
	
    @GetMapping("/health")
    public String health() {
        return "user-service success";
    }
    
    @ApiOperation(value = "获取用户基本信息",httpMethod = "GET")
	@ApiResponse(code = 200, message = "success", response = String.class)
	@GetMapping("/basicInfo/{id}")
	public String basicInfo(@PathVariable String id){
		System.out.println("id是："+id);
		return "这是basicInfo";
	}
    
    @ApiOperation(value = "计算+", notes = "加法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "a",  value = "数字a", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "b",  value = "数字b", required = true, dataType = "Long")
    })
    @GetMapping("/{a}/{b}")
    public Integer get(@PathVariable Integer a, @PathVariable Integer b) {
        return a + b;
    }
    
    /**
     * 测试分布式事务
     * @author xuhongyu
     * @param rollBack 是否回滚
     */
    @GetMapping("/testDistributedTx")
    public void testDistributedTx(@RequestParam(required = false) Boolean rollBack,TradeVo tradeVo) {
    	testDistributedTxService.insertUserOrderStatisUser(rollBack,tradeVo);
    }
}
