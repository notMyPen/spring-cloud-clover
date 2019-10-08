package rrx.cnuo.service.feignclient.callback;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import rrx.cnuo.cncommon.accessory.CLoverException;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.biz.BoardStatisVo;
import rrx.cnuo.service.feignclient.BizFeignService;

@Component
@Slf4j
@SuppressWarnings("unchecked")
public class BizHystrixFeignFallback implements BizFeignService{

	@Override
	public void insertStatisUser() {
		log.info("增删改操作的服务降级必须抛异常，不然分布式事务不会滚！");
		throw new CLoverException("insertStatisUser失败");
	}

	@Override
	public JsonResult<BoardStatisVo> getBoardStatis(Long uid) {
		log.info("服务降级，返回一个错误");
		return JsonResult.fail(JsonResult.FAIL, "获取失败，请重新尝试");
	}

	@Override
	public JsonResult<List<String>> getProvinceListByIdList(String idListJsonStr) {
		log.info("服务降级，返回一个错误");
		return JsonResult.fail(JsonResult.FAIL, "获取失败，请重新尝试");
	}

	@Override
	public Integer getAwardCardCnt(Long uid) {
		return 0;
	}

	@Override
	public Integer getCardUserCnt(Long uid) {
		return 0;
	}

}
