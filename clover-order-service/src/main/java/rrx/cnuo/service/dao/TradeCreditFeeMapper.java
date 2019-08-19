package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.TradeCreditFee;

public interface TradeCreditFeeMapper {
    int deleteByPrimaryKey(Long tradeId);

    int insert(TradeCreditFee record);

    int insertSelective(TradeCreditFee record);

    TradeCreditFee selectByPrimaryKey(Long tradeId);

    int updateByPrimaryKeySelective(TradeCreditFee record);

    int updateByPrimaryKey(TradeCreditFee record);
}