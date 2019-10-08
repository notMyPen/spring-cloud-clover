package rrx.cnuo.service.service;

import java.util.List;

import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.po.Trade;
import rrx.cnuo.service.vo.request.PayBusinessVo;
import rrx.cnuo.service.vo.response.OrderStatusVo;
import rrx.cnuo.service.vo.response.ReturnPayBusinessVo;

/**
 * 支付过程中一些公共不变的操作已经实现（本类中的所有方法理论上不允许更改）：<br>
 * 不同业务需要自己实现接口PayService
 * @author xuhongyu
 * @date 2019年8月30日
 */
public interface PayProcessService {

	/**
	 * 支付申请时本地数据库操作
	 * @author xuhongyu
	 * @param payBusinessVo
	 * @return
	 * @throws Exception
	 */
	public JsonResult<ReturnPayBusinessVo> updateLocalPayApply(PayBusinessVo payVo) throws Exception;
	
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
	 * 回盘
	 * @author xuhongyu
	 * @param orderId
	 * @param orderStatus
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public JsonResult updateReceiveBank(Long orderId, int orderStatus, String msg) throws Exception;
	
	/**
	 * 获取一分钟内没有处理的订单号，包括：,
	 * 1，银联和联拓(微信第三方)支付已申请支付订单(trade_status是1)
	 * 2，其它通道已支付确认(已报盘未回盘trade_status是2)
	 * @author xuhongyu
	 * @return
	 * @throws Exception
	 */
	List<Trade> getOrderToDealwith() throws Exception;
	
	/**
     * 代收或代付确认的本地操作
     * @author xuhongyu
     * @param payVo
     * @return
     * @throws Exception
     */
//	public JsonResult<ReturnPayBusinessVo> updateLocalPayConfirm(PayBusinessVo payVo) throws Exception;
	
	/**
	 * 调用支付中心进行代收或代付确认
	 * @author xuhongyu
	 * @param payBusinessVo
	 * @return
	 * @throws Exception
	 * JsonResult<ReturnPayBusinessVo>
	 */
//	public JsonResult<ReturnPayBusinessVo> updateRemotePayConfirm(PayBusinessVo payBusinessVo) throws Exception;
	
	/**
	 * 对账完后的操作
	 * @author xuhongyu
	 * @param tradeId
	 * @throws Exception
	 */
	void checkEnd(Long tradeId,Byte businessType) throws Exception;

	/**
	 * 获取订单支付状态
	 * @author xuhongyu
	 * @param tradeId
	 * @param fuid 被翻牌子用户id（翻牌子支付时才传）
	 * @return
	 */
	public JsonResult<OrderStatusVo> getOrderStatus(long tradeId,Long fuid);
}
