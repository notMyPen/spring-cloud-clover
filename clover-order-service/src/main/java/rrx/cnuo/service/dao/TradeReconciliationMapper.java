package rrx.cnuo.service.dao;

import java.util.List;

import rrx.cnuo.service.po.TradeReconciliation;
import rrx.cnuo.service.vo.paycenter.Record;

public interface TradeReconciliationMapper {
    int deleteByPrimaryKey(Long tradeId);

    int insertSelective(TradeReconciliation record);

    TradeReconciliation selectByPrimaryKey(Long tradeId);

    int updateByPrimaryKeySelective(TradeReconciliation record);

    /**
     * 将数据批量插入到TradeReconciliation表中
     * @author xuhongyu
     * @param records
     */
	void insertBatch(List<Record> list);
	
	/**
	 * 根据条件查询账单记录
	 * @author xuhongyu
	 * @param radeReconciliationParam
	 * @return
	 */
	List<TradeReconciliation> getByParam(TradeReconciliation radeReconciliationParam);
}