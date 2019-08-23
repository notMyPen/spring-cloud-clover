package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.UserBasicInfo;

public interface UserBasicInfoMapper {
    int deleteByPrimaryKey(Long uid);

    int insertSelective(UserBasicInfo record);

    UserBasicInfo selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(UserBasicInfo record);

}