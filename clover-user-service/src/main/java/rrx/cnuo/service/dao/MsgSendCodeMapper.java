package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.MsgSendCode;

public interface MsgSendCodeMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(MsgSendCode record);

    MsgSendCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MsgSendCode record);

}