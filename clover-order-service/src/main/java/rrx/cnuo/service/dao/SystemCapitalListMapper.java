package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.SystemCapitalList;

public interface SystemCapitalListMapper {
    int deleteByPrimaryKey(Long tradeId);

    int insertSelective(SystemCapitalList record);

    SystemCapitalList selectByPrimaryKey(Long tradeId);

    int updateByPrimaryKeySelective(SystemCapitalList record);

}