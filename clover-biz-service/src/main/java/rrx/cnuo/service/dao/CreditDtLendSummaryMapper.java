package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.CreditDtLendSummary;

public interface CreditDtLendSummaryMapper {
    int deleteByPrimaryKey(Long uid);

    int insert(CreditDtLendSummary record);

    int insertSelective(CreditDtLendSummary record);

    CreditDtLendSummary selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(CreditDtLendSummary record);

    int updateByPrimaryKey(CreditDtLendSummary record);
}