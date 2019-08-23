package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.UserDetailInfo;

public interface UserDetailInfoMapper {
    int deleteByPrimaryKey(Long uid);

    int insert(UserDetailInfo record);

    int insertSelective(UserDetailInfo record);

    UserDetailInfo selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(UserDetailInfo record);

    int updateByPrimaryKey(UserDetailInfo record);
}