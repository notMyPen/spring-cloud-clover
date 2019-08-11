package rrx.cnuo.service.service;

import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.PayBusinessVo;
import rrx.cnuo.cncommon.vo.ReturnPayBusinessVo;
import rrx.cnuo.service.po.Trade;

public interface WithdrawService {

	/**
	 * 提现回盘
	 * @author xuhongyu
	 * @param trade
	 * @param tradeStatus
	 * @param msg
	 * @throws Exception
	 */
	void updateReceiveBank(Trade trade, byte tradeStatus, String msg) throws Exception;

//	public void withdraw(PayBusinessVo withdrawVo, Integer capital) throws Exception;
	
	/**
	 * 提现
	 * @author xuhongyu
	 * @param payVo
	 * @return
	 */
	JsonResult<ReturnPayBusinessVo> updateWithdraw(PayBusinessVo payVo) throws Exception;

	/**
	 * 对账完后
	 * @param tradeId
	 * @throws Exception 
	 */
	public void checkEnd(Long tradeId) throws Exception;
}
