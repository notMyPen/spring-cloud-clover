package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.CardUseRecord;

public interface CardUseRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CardUseRecord record);

    int insertSelective(CardUseRecord record);

    CardUseRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CardUseRecord record);

    int updateByPrimaryKey(CardUseRecord record);
}