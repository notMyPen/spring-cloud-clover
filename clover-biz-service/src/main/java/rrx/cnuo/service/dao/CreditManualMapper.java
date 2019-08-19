package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.CreditManual;

public interface CreditManualMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CreditManual record);

    int insertSelective(CreditManual record);

    CreditManual selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CreditManual record);

    int updateByPrimaryKey(CreditManual record);
}