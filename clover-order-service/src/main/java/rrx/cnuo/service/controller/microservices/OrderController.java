package rrx.cnuo.service.controller.microservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.order.TradeVo;
import rrx.cnuo.service.service.TestService;
import rrx.cnuo.service.service.OrderInfoService;

@RestController
public class OrderController{

	@Autowired private OrderInfoService orderInfoService;
	@Autowired private TestService testService;

	@GetMapping("/test/insertTrade")
    public void testInsertTrade(TradeVo tradeVo) {
    	testService.insertTrade(tradeVo);
    }

	/**
	 * 支付中心开户
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/payCenter/account")
	public JsonResult<Boolean> openAccountBalance(@RequestParam("userId") Long userId) throws Exception{
		return JsonResult.ok(orderInfoService.addAccountBalanceInfo(userId));
	}
	
	/**
	 * 计算某用户一共购买的礼券个数
	 * @author xuhongyu
	 * @param uid
	 * @return
	 */
	@GetMapping("/buycardCnt/{uid}")
	int getBuycardCnt(@PathVariable Long uid) {
		return orderInfoService.getBuycardCnt(uid);
	}
}
