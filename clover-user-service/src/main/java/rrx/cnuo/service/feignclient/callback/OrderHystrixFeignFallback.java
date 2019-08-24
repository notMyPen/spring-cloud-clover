package rrx.cnuo.service.feignclient.callback;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import rrx.cnuo.cncommon.accessory.CLoverException;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.order.TradeVo;
import rrx.cnuo.service.feignclient.OrderFeignService;

/**
 * 断路器回调方法<br>
 * 断路器要实现之前定义的UserFeignService接口，请求失败，进入断路器时，会回调这里的方法
 * @author xuhongyu
 * @date 2019年6月27日
 */
@Component
@Slf4j
public class OrderHystrixFeignFallback implements OrderFeignService{

	@Override
	public JsonResult<Boolean> openAccountBalance(@RequestParam("userId") Long userId) {
		// TODO 这里是进入断路后的降级方法，返回一个降级数据
		return JsonResult.ok(false);
	}

	@Override
	public void insertTrade(TradeVo tradeVo) {
		log.info("增删改操作的服务降级必须抛异常，不然分布式事务不会滚！");
//		DTXUserControls.rollbackGroup(UserContextHolder.currentUser().getUserId()+"");
		throw new CLoverException("insertTrade失败");
	}

}
