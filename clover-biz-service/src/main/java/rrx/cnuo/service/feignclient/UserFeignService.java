package rrx.cnuo.service.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.feign.UserPassportVo;
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
@SuppressWarnings("rawtypes")
@FeignClient(name = "user-service",decode404 = true,fallback = UserHystrixFeignFallback.class/*,path = "/api"*/)
public interface UserFeignService {
	
	/**
	 * 更新用户是否注册信用中心状态为已注册
	 * @author xuhongyu
	 * @param uid
	 */
	@PostMapping("/userAccount/registCredit")
	void updateUserRegistCredit(@RequestParam Long uid);
	
	@GetMapping("/info/faceVerifyCheck")
	public JsonResult getFaceVerifyCheck(@RequestParam("idCardNo") String idCardNo);
	
	
	
	@GetMapping("/userPassport/{uid}")
	UserPassportVo getUserPassportByUid(@PathVariable("uid") Long uid) throws Exception;
	
	/**
	 * 更新用户剩余礼券个数
	 * @author xuhongyu
	 * @param uid
	 * @param leftCnt
	 */
	@PostMapping("/possport/userCardNum")
	void updateUserCardNum(@RequestParam Long uid);
	
	/**
	 * 获取用户的微信号
	 * @author xuhongyu
	 * @param uid
	 * @return
	 */
	@GetMapping("/info/wxAccount/{uid}")
	String getUserWxAccount(@PathVariable Long uid);
}
