package rrx.cnuo.service.dao;

import java.util.List;

import rrx.cnuo.service.po.BoardLike;

public interface BoardLikeMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(BoardLike record);

    BoardLike selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BoardLike record);

	int countByParam(BoardLike boardLikeParam);
	
	List<BoardLike> selectByParam(BoardLike boardLikeParam);
}