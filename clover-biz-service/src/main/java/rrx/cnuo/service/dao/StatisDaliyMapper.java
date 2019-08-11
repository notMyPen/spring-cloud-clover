package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.StatisDaliy;
import rrx.cnuo.service.po.StatisDaliyKey;

public interface StatisDaliyMapper {
    int deleteByPrimaryKey(StatisDaliyKey key);

    int insertSelective(StatisDaliy record);

    StatisDaliy selectByPrimaryKey(StatisDaliyKey key);

    int updateByPrimaryKeySelective(StatisDaliy record);

}