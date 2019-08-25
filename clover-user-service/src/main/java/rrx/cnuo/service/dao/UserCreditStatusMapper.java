package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.UserCreditStatus;

public interface UserCreditStatusMapper {
    int deleteByPrimaryKey(Long uid);

    int insertSelective(UserCreditStatus record);

    UserCreditStatus selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(UserCreditStatus record);
}