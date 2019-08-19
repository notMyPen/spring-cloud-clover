package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.CreditDishonestCases;

public interface CreditDishonestCasesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CreditDishonestCases record);

    int insertSelective(CreditDishonestCases record);

    CreditDishonestCases selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CreditDishonestCases record);

    int updateByPrimaryKey(CreditDishonestCases record);
}