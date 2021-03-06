package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.BoardTurn;

public interface BoardTurnMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(BoardTurn record);

    BoardTurn selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BoardTurn record);

    int countByParam(BoardTurn record);

	int getCardUserCnt(Long uid);
}