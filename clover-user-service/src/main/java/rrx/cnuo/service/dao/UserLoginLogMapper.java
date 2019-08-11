package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.UserLoginLog;

public interface UserLoginLogMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UserLoginLog record);

    UserLoginLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserLoginLog record);

}