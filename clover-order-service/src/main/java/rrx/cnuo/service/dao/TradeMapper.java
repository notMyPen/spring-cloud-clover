package rrx.cnuo.service.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import rrx.cnuo.service.po.Trade;

public interface TradeMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Trade record);

    Trade selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Trade record);

    Trade selectByPrimaryKeyForUpdate(Long id);
    
    /**
     * 获取某用户余额支付成功且尚未对账的金额之和
     * @author xuhongyu
     * @param userId
     * @return
     */
    Integer getBalancePaySuccUnReconciledAmt(@Param("userId") Long userId);

	/**
     * 获取一分钟内没有处理的订单号，包括：,
     * 1，银联和联拓(微信第三方)支付已申请支付订单(trade_status是1且reserve_data不为空)
     * 2，其它通道已支付确认(已报盘未回盘trade_status是2
     * @return
     * @author xuhongyu
     */
    List<Trade> getOrderToDealwith();
    
    /**
     * 根据条件查询交易信息
     * @param trade
     * @return
     * @author xuhongyu
     */
    List<Trade> getTradeByParam(Trade trade);
}