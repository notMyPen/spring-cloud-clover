package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.UserDetailInfo;

public interface UserDetailInfoMapper {
    int deleteByPrimaryKey(Long uid);

    int insertSelective(UserDetailInfo record);

    UserDetailInfo selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(UserDetailInfo record);

}