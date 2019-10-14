package rrx.cnuo.service.service;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.po.Trade;
import rrx.cnuo.service.vo.request.PayBusinessVo;

/**
 * 封装支付过程中需要不同业务自己去实现的方法
 * @author xuhongyu
 * @date 2019年8月30日
 */
@SuppressWarnings("rawtypes")
public interface PayService{
	
	/**
	 * 根据不同业务的现金流动类型，将参数cashFlowType装配到参数PayBusinessVo中，需每个业务自己实现
	 * 现金流类型：1:代收/代扣, 2:代付
	 * @author xuhongyu
	 * @param payVo
	 */
	void setCashFlowType(PayBusinessVo payVo);
	
	/**
	 * 根据不同业务的手续费，将参数rechargeWithdrawFee和totalAmount装配到参数PayBusinessVo中，需每个业务自己实现
	 * @author xuhongyu
	 * @param payVo
	 */
	void setFeeAndTotalAmount(PayBusinessVo payVo);
	
	/**
	 * 在微信、支付宝、银联支付方式下，根据不同业务，将参数reserveData装配到参数PayBusinessVo中，需每个业务自己实现
	 * @author xuhongyu
	 * @param payVo
	 */
	void setReserveData(PayBusinessVo payVo);
	
	/**
	 * 支付发起之前，具体业务自己独有的校验，需每个业务自己实现
	 * @author xuhongyu
	 * @param payVo
	 * @return
	 * @throws Exception
	 */
	JsonResult checkBusinessBeforePay(PayBusinessVo payVo) throws Exception;
	
	/**
	 * 支付确认时，各个业务除了做账之外的业务表操作，需每个业务自己实现
	 * @author xuhongyu
	 * @param payBusinessVo
	 * @throws Exception
	 */
	void updateBusinessLocal(PayBusinessVo payBusinessVo) throws Exception;
	
	/**
	 * 不同业务实现自己的做账方法，需每个业务自己实现
	 * @author xuhongyu
	 * @param payVo
	 */
	void makeAccountList(PayBusinessVo payVo);
	
	/**
	 * 支付宝/微信、银联等不确定报盘动作的支付方式在申请支付时会把前端传来的参数暂存到trade表的reserveData中<br>
	 * 这类支付的报盘操作放在回盘中进行，回盘时将支付申请参数从reserveData中装配到PayBusinessVo中，需每个业务自己实现
	 * @author xuhongyu
	 * @param payVo
	 * @param reserve
	 */
	void setUserParamsFromReserveData(PayBusinessVo payVo,JSONObject reserve);
	
	/**
	 * 各个业务自己的订单回盘逻辑，需每个业务自己实现
	 * @param trade  订单信息
	 * @param result 成功与否
	 * @param msg 支付结果信息
	 */
	void updateReceiveTrade(Trade trade,boolean result,String msg) throws Exception;

	/**
	 * 对账完后的操作，需每个业务自己实现（如果没有不写就行）
	 * @author xuhongyu
	 * @param tradeId
	 * @param businessType
	 * @throws Exception
	 */
	void checkEnd(Long tradeId) throws Exception;
}
