package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.BoardView;

public interface BoardViewMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(BoardView record);

    BoardView selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BoardView record);

    int countByParam(BoardView record);
    
    /**
     * 获取某用户浏览过多少人
     * @author xuhongyu
     * @param record
     * @return
     */
    int getViewUserCnt(Long uid);
    
    /**
     * 获取用户被多少人浏览过
     * @author xuhongyu
     * @param record
     * @return
     */
    int getViewedUserCnt(Long fuid);
}