package rrx.cnuo.service.feignclient.callback;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import rrx.cnuo.cncommon.accessory.CLoverException;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.feignclient.BizFeignService;

@Component
@Slf4j
@SuppressWarnings("rawtypes")
public class BizHystrixFeignFallback implements BizFeignService{
	
	@Override
	public JsonResult addCardAward(Byte awardType) {
		log.info("增删改操作的服务降级必须抛异常，不然分布式事务不会滚！");
		throw new CLoverException("addCardAward失败");
	}

}
