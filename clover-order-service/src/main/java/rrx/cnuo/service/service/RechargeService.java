package rrx.cnuo.service.service;

import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.PayBusinessVo;
import rrx.cnuo.cncommon.vo.ReturnPayBusinessVo;
import rrx.cnuo.service.po.Trade;
import rrx.cnuo.service.vo.RechargeVo;

public interface RechargeService {

	/**
	 * 充值报盘操作
	 * @author xuhongyu
	 * @param payVo
	 * @throws Exception
	 */
	JsonResult<ReturnPayBusinessVo> updateRechargeApply(RechargeVo payVo) throws Exception;

	/**
	 * 充值回盘操作
	 * @author xuhongyu
	 * @param trade
	 * @param tradeStatus
	 * @param msg
	 * @throws Exception
	 */
	void updateReceiveBank(Trade trade, byte tradeStatus, String msg) throws Exception;
	
	public void recharge(PayBusinessVo payVo) throws Exception;

}
