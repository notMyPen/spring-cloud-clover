package rrx.cnuo.service.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.biz.BoardStatisVo;
import rrx.cnuo.service.feignclient.callback.BizHystrixFeignFallback;

/**
 * 通过内部访问biz-service中的资源<br>
 * @author xuhongyu
 * @date 2019年8月10日
 */
@FeignClient(name = "biz-service",decode404 = true,fallback = BizHystrixFeignFallback.class/*,path = "/api"*/)
public interface BizFeignService {

	@GetMapping("/test/insertStatisUser")
	void insertStatisUser();
	
	@GetMapping("/boardStatis")
	JsonResult<BoardStatisVo> getBoardStatis(@RequestParam("uid") Long uid);
	
	@GetMapping("/provinceNameList")
	JsonResult<List<String>> getProvinceListByIdList(@RequestParam String idListJsonStr);
	
	@GetMapping("/awardCardCnt")
	public Integer getAwardCardCnt(@RequestParam Long uid);
	
	@GetMapping("/cardUserCnt")
	public Integer getCardUserCnt(@RequestParam Long uid);
}
