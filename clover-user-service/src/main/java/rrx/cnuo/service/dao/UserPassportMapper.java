package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.UserPassport;

public interface UserPassportMapper {
    int deleteByPrimaryKey(Long uid);

    int insertSelective(UserPassport record);

    UserPassport selectByPrimaryKey(Long uid);
    
    UserPassport selectByWxOpenId(String openId);
    
    UserPassport selectByWxMiniOpenId(String miniOpenId);

    int updateByPrimaryKeySelective(UserPassport record);

}