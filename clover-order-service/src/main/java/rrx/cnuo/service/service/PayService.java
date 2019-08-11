package rrx.cnuo.service.service;

import java.util.List;

import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.PayBusinessVo;
import rrx.cnuo.cncommon.vo.ReturnPayBusinessVo;
import rrx.cnuo.service.po.Trade;

/**
 * 封装发起支付过程中需要对外暴露的方法
 * @author xuhongyu
 */
public interface PayService{

	/**
	 * 订单回盘
	 * @param orderid // 订单id
	 * @param orderStatus //支付中心的订单状态
	 * @param msg //支付结果信息
	 */
	@SuppressWarnings("rawtypes")
	public JsonResult updateReceiveTrade(Long orderId, int orderStatus, String msg) throws Exception;
	
	/**
	 * 调用支付中心进行代收或代付申请
	 * @author xuhongyu
	 * @param payBusinessVo
	 * @return
	 * @throws Exception
	 * JsonResult<ReturnPayBusinessVo>
	 */
	public JsonResult<ReturnPayBusinessVo> updateRemotePayApply(PayBusinessVo payBusinessVo) throws Exception;
	
	/**
	 * 调用支付中心进行代收或代付确认
	 * @author xuhongyu
	 * @param payBusinessVo
	 * @return
	 * @throws Exception
	 * JsonResult<ReturnPayBusinessVo>
	 */
	public JsonResult<ReturnPayBusinessVo> updateRemotePayConfirm(PayBusinessVo payBusinessVo) throws Exception;

	/**
	 * 获取一分钟内没有处理的订单号，包括：,
	 * 1，银联和联拓(微信第三方)支付已申请支付订单(trade_status是1)
	 * 2，其它通道已支付确认(已报盘未回盘trade_status是2)
	 * @author xuhongyu
	 * @return
	 * @throws Exception
	 */
	public List<Trade> getOrderToDealwith() throws Exception;

}
