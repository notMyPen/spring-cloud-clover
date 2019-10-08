package rrx.cnuo.service.dao;

import java.util.List;

import rrx.cnuo.service.po.CardAwardRecord;

public interface CardAwardRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(CardAwardRecord record);

    CardAwardRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CardAwardRecord record);

	List<CardAwardRecord> selectByParam(CardAwardRecord record);

	int getAwardCardCnt(Long uid);

}