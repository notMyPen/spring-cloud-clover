package rrx.cnuo.service.service.data;

import rrx.cnuo.service.po.BoardStatis;

public interface BoardStatisDataService {

	void insertSelective(BoardStatis record) throws Exception;

    BoardStatis selectByPrimaryKey(Long uid) throws Exception;

    void updateByPrimaryKeySelective(BoardStatis record) throws Exception;
    
    void delBoardStatisFromRedis(long uid) throws Exception;
}
