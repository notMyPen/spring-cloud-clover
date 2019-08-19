package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.BoardView;

public interface BoardViewMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BoardView record);

    int insertSelective(BoardView record);

    BoardView selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BoardView record);

    int updateByPrimaryKey(BoardView record);
}