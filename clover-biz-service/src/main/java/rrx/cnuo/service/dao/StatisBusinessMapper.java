package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.StatisBusiness;
import rrx.cnuo.service.po.StatisBusinessKey;

public interface StatisBusinessMapper {
    int deleteByPrimaryKey(StatisBusinessKey key);

    int insertSelective(StatisBusiness record);

    StatisBusiness selectByPrimaryKey(StatisBusinessKey key);

    int updateByPrimaryKeySelective(StatisBusiness record);

    void insertOrUpdate(StatisBusiness record);
}