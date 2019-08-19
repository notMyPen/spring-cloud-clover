package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.CardAwardRecord;

public interface CardAwardRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CardAwardRecord record);

    int insertSelective(CardAwardRecord record);

    CardAwardRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CardAwardRecord record);

    int updateByPrimaryKey(CardAwardRecord record);
}