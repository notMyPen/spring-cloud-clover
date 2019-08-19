package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.CreditDtLendDetail;

public interface CreditDtLendDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CreditDtLendDetail record);

    int insertSelective(CreditDtLendDetail record);

    CreditDtLendDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CreditDtLendDetail record);

    int updateByPrimaryKey(CreditDtLendDetail record);
}