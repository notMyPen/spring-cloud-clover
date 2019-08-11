package rrx.cnuo.service.service;

import rrx.cnuo.cncommon.vo.order.TradeVo;

public interface TestDistributedTxService {

	void insertUserOrderStatisUser(Boolean rollBack,TradeVo tradeVo);

}
