package rrx.cnuo.service.dao;

import java.util.List;

import rrx.cnuo.service.po.TradeCreditFee;

public interface TradeCreditFeeMapper {
    int deleteByPrimaryKey(Long tradeId);

    int insertSelective(TradeCreditFee record);

    TradeCreditFee selectByPrimaryKey(Long tradeId);

    int updateByPrimaryKeySelective(TradeCreditFee record);

	List<TradeCreditFee> selectByParam(TradeCreditFee record);

}