package rrx.cnuo.service.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.feignclient.callback.BizHystrixFeignFallback;

/**
 * 通过内部访问biz-service中的资源<br>
 * @author xuhongyu
 * @date 2019年8月10日
 */
@FeignClient(name = "biz-service",decode404 = true,fallback = BizHystrixFeignFallback.class/*,path = "/api"*/)
@SuppressWarnings("rawtypes")
public interface BizFeignService {

	@PutMapping("/cardAward")
	public JsonResult addCardAward(@RequestParam Byte awardType);
}
