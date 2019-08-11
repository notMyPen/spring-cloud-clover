package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.MsgWechat;

public interface MsgWechatMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(MsgWechat record);

    MsgWechat selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MsgWechat record);

}