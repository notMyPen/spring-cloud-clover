package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.UserPic;

public interface UserPicMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserPic record);

    int insertSelective(UserPic record);

    UserPic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserPic record);

    int updateByPrimaryKey(UserPic record);
}