package rrx.cnuo.service.dao;

import java.util.List;

import rrx.cnuo.service.po.UserPic;

public interface UserPicMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UserPic record);

    UserPic selectByPrimaryKey(Long id);
    
    List<UserPic> selectByParam(UserPic userPic);

    int updateByPrimaryKeySelective(UserPic record);
}