package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.UserLoginRecord;

public interface UserLoginRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserLoginRecord record);

    int insertSelective(UserLoginRecord record);

    UserLoginRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserLoginRecord record);

    int updateByPrimaryKey(UserLoginRecord record);
}