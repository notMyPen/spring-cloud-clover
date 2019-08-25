package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.UserSpouseSelection;

public interface UserSpouseSelectionMapper {
    int deleteByPrimaryKey(Long uid);

    int insertSelective(UserSpouseSelection record);

    UserSpouseSelection selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(UserSpouseSelection record);

}