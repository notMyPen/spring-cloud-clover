package rrx.cnuo.service.service;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.service.po.Trade;
import rrx.cnuo.service.po.TradeReconciliation;

/**
 * 对账逻辑处理
 * @author xuhongyu
 * @version 创建时间：2018年7月30日 下午8:17:34
 */
public interface ReconciliationService {

	/**
	 * 对账异常表预警
	 * @author xuhongyu
	 * @throws Exception 
	 */
	public void updateSendMsgByTradeAbnormal(String curDate) throws Exception;

	/**
	 * 根据对账日下载所有通道下的账单并保存(支持重复保存)
	 * @author xuhongyu
	 * @param reconDateStr
	 * @throws Exception
	 * void
	 */
	public void updateDownloadAndSaveBill(String reconDateStr) throws Exception;
	
	/**
	 * 获取对账日和对账日前一个工作日之间未对账的订单
	 * @author xuhongyu
	 * @param reconDate 对账日
	 * @param dayCnt 上一个工作日和对账日之间的天数
	 */
	public List<Trade> getUnReconciliationTrades(String reconDate,int dayCnt)throws Exception;

	/**
	 * 获取真实对账逻辑下(某一天的订单和同一账单日对比)的对比结果信息
	 * @author xuhongyu
	 * @param reconDate 对账日当天
	 * @param compareDate 对比日当天
	 * @param compareDateBefore 对比日前一天
	 * void
	 * @throws Exception 
	 */
	public JSONObject getRrealReconciliationInfo(Date reconDate,Date compareDate,Date compareDateBefore) throws Exception;
	
	/**
	 * 根据订单id进行对账业务逻辑处理
	 * @author xuhongyu
	 * @param tradeId 订单号
	 * @param result 支付结果:true-成功；false-失败
	 * @param isRecvBank 是否做回盘操作
	 * @throws Exception 
	 */
	public void updateReconBusinessProcess(Long tradeId, Boolean result,Boolean isRecvBank) throws Exception;

	/**
	 * 获取对账异常表中的所有对账日，并再次去支付中心下载它们的对账单
	 * @author xuhongyu
	 * @return
	 * List<Date>
	 */
	public List<Date> insertAbnormalDatesBillAgain() throws Exception;

	public void updateHandlerAbnormalBatch(List<Long> tradeReconciliationIdList,
			List<TradeReconciliation> tradeAbnormalList,Date reconDate) throws Exception;
}
