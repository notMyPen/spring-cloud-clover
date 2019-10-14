package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.BoardStatis;

public interface BoardStatisMapper {
    int deleteByPrimaryKey(Long uid);

    int insertSelective(BoardStatis record);

    BoardStatis selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(BoardStatis record);
}