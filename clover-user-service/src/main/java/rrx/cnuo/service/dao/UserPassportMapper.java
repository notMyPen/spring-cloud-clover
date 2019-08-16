package rrx.cnuo.service.dao;

import java.util.List;

import rrx.cnuo.service.po.UserPassport;

public interface UserPassportMapper {
    int deleteByPrimaryKey(Long uid);

    int insert(UserPassport record);

    int insertSelective(UserPassport record);

    UserPassport selectByPrimaryKey(Long uid);
    
    List<UserPassport> selectByParam(UserPassport param);

    int updateByPrimaryKeySelective(UserPassport record);

    int updateByPrimaryKey(UserPassport record);
}