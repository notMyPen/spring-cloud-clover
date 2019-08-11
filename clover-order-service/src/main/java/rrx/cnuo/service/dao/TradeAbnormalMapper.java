package rrx.cnuo.service.dao;

import java.util.Date;
import java.util.List;

import rrx.cnuo.service.po.TradeAbnormal;
import rrx.cnuo.service.po.TradeReconciliation;
import rrx.cnuo.service.vo.AbnormalTradeGroupVo;

public interface TradeAbnormalMapper {
    int deleteByPrimaryKey(Long tradeId);

    int insertSelective(TradeAbnormal record);

    TradeAbnormal selectByPrimaryKey(Long tradeId);

    int updateByPrimaryKeySelective(TradeAbnormal record);
    
    /**
	 * 将数据批量插入到TradeAbnormal表中
	 * @author xuhongyu
	 * @param records
	 * @return
	 */
	int insertTradeAbnormalBatch(List<TradeReconciliation> list);

	/**
	 * 获取对账日内，每种支付通道类型下未发出预警信息的每种异常类型的个数
	 * @author xuhongyu
	 * @param curDate
	 * @return
	 */
	List<AbnormalTradeGroupVo> getTradeAbnormalList(String curDate);

	/**
	 * 对已经发出预警信息的异常订单信息的状态置为已经发送消息
	 * @author xuhongyu
	 * @param curDateStr
	 */
	void updateTradeAbnormalSendMsgStatus(String curDate);

	/**
	 * 根据订单号删除对账异常记录
	 * @author xuhongyu
	 * @param reconciliationIdList
	 */
	void daleteTradeAbnormalBatch(List<Long> list);

	/**
	 * 获取对账异常表中的所有对账日
	 * @author xuhongyu
	 * @return
	 * List<Date>
	 */
	List<Date> getTradeAbnormalDates();

}