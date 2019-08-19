package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.CreditStatus;

public interface CreditStatusMapper {
    int deleteByPrimaryKey(Long uid);

    int insert(CreditStatus record);

    int insertSelective(CreditStatus record);

    CreditStatus selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(CreditStatus record);

    int updateByPrimaryKey(CreditStatus record);
}