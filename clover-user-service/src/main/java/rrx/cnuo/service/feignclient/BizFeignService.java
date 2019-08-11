package rrx.cnuo.service.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import rrx.cnuo.cncommon.accessory.config.MFeignConfig;
import rrx.cnuo.service.feignclient.callback.BizHystrixFeignFallback;

/**
 * 通过内部访问biz-service中的资源<br>
 * @author xuhongyu
 * @date 2019年8月10日
 */
@FeignClient(name = "biz-service",decode404 = true,fallback = BizHystrixFeignFallback.class,configuration = MFeignConfig.class/*,path = "/api"*/)
public interface BizFeignService {

	@GetMapping("/test/insertStatisUser")
	void insertStatisUser();
}
