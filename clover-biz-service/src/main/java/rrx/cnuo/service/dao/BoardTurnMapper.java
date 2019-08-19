package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.BoardTurn;

public interface BoardTurnMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BoardTurn record);

    int insertSelective(BoardTurn record);

    BoardTurn selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BoardTurn record);

    int updateByPrimaryKey(BoardTurn record);
}