package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.SystemTask;

public interface SystemTaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(SystemTask record);

    SystemTask selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemTask record);

}