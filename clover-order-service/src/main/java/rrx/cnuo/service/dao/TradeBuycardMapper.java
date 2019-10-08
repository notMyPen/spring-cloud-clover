package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.TradeBuycard;

public interface TradeBuycardMapper {
    int deleteByPrimaryKey(Long tradeId);

    int insertSelective(TradeBuycard record);

    TradeBuycard selectByPrimaryKey(Long tradeId);

    int updateByPrimaryKeySelective(TradeBuycard record);

	int getBuycardCnt(Long uid);

}