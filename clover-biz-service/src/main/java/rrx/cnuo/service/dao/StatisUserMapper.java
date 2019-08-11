package rrx.cnuo.service.dao;

import java.util.List;

import rrx.cnuo.service.po.StatisUser;
import rrx.cnuo.service.po.StatisUserKey;

public interface StatisUserMapper {
    int deleteByPrimaryKey(StatisUserKey key);

    int insertSelective(StatisUser record);

    StatisUser selectByPrimaryKey(StatisUserKey key);

    int updateByPrimaryKeySelective(StatisUser record);

    int insertOrUpdate(StatisUser record);
    
	void insertOrUpdateBatch(List<StatisUser> list);
}