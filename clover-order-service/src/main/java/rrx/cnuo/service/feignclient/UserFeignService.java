package rrx.cnuo.service.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.service.feignclient.callback.UserHystrixFeignFallback;

/**
 * 通过内部访问user-service中的资源<br>
 * name是指要请求的服务名称<br>
 * fallback 是指请求失败，进入断路器的类，和使用ribbon是一样的<br>
 * configuration 是feign的一些配置，例如编码器等<br>
 * path表示对方的ContextPath(如果有)
 * @author xuhongyu
 * @date 2019年6月27日
 */
@FeignClient(name = "user-service",decode404 = true,fallback = UserHystrixFeignFallback.class/*,path = "/api"*/)
public interface UserFeignService {

	@GetMapping("/userAccount/{uid}")
	JSONObject getUserAccountByUid(@PathVariable("uid") Long uid) throws Exception;
	
	@GetMapping("/userPassport/{uid}")
	JSONObject getUserPassportByUid(@PathVariable("uid") Long uid) throws Exception;

	@PutMapping("/userAccount")
	void updateUserAccountByUidSelective(@RequestParam("userAccountJsonStr") String userAccountJsonStr) throws Exception;
	
	/**
	 * 以累加的方式，更新在某订单中某些相关用户的余额和可提现余额
	 * @author xuhongyu
	 * @param userAccountListStatis 某订单下某些相关用户的账单统计情况(List<JSONObject>转换成的字符串)
	 * @param updateType 更新类型：0-同时更新余额和可提现余额；1-只更新余额；2-只更新可提现余额
	 * @param rollBack 是否是金额变动回滚
	 */
	@PutMapping("/updateUserAccountAccumulateAboutOrder")
	void updateUserAccountAccumulateAboutOrder(@RequestParam("userAccountListStatis") String userAccountListStatis,
			@RequestParam("updateType") Byte updateType,@RequestParam("rollBack") Boolean rollBack) throws Exception;
	
}
