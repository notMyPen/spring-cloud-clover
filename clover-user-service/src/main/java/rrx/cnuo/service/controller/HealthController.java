package rrx.cnuo.service.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.utils.MqSendTool;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.cncommon.vo.config.WeChatAppConfig;
import rrx.cnuo.cncommon.vo.config.WeChatMiniConfig;
import rrx.cnuo.cncommon.vo.order.TradeVo;
import rrx.cnuo.service.accessory.config.UserConfigBean;
import rrx.cnuo.service.service.TestDistributedTxService;

@Api("用户相关资源操作(演示Ribbon和Feign的区别)")
@RestController
@RefreshScope//必须在需要刷新的配置类中加这个注解才会刷新
public class HealthController {

	@Autowired private UserConfigBean userConfigBean;
	@Autowired private WeChatAppConfig weChatAppConfig;
	@Autowired private WeChatMiniConfig weChatMiniConfig;
	@Autowired private RedisTool redis;
	@Autowired private MqSendTool mqSendTool;
	@Autowired private TestDistributedTxService testDistributedTxService;
	
    @GetMapping("/config/test-string")
    public String testString(){
    	return userConfigBean.getTestString();
    }
	
    @GetMapping("/health")
    public String health() {
        return "user-service success";
    }
    
    @GetMapping("/test")
    public String test() {
    	String a = weChatMiniConfig.getMiniAppId();
    	String b = weChatAppConfig.getWechatAppid();
    	
    	//测试redis
    	redis.set("testKey", "12313223g", 120);
    	
    	//测试mq和mysql
        JSONObject msgObj = new JSONObject();
		msgObj.put(Const.MQ.MQ_HANDLER_TYPE_KEY, Const.MqHandleType.SEND_WX_MSG.getCode());
		msgObj.put("uid", 121210);//短信类型：语音短信
		msgObj.put("msgType", Const.WeChatMsgEnum.CREDIT_NOTIFY.getCode());
		msgObj.put("msgVariableVal", "我是短信内容111");//短信验证码
		mqSendTool.normalMqSender(UUID.randomUUID().toString(),msgObj);
		
		//order配置
		msgObj.put(Const.MQ.MQ_HANDLER_TYPE_KEY, Const.MqHandleType.TEST.getCode());
		msgObj.put("test", "测试字符串");//短信类型：语音短信
		mqSendTool.normalMqSender(UUID.randomUUID().toString(),msgObj);
    	
    	return a+"；"+b;
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
