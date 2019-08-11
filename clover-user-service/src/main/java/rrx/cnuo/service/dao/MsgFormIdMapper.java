package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.MsgFormId;

public interface MsgFormIdMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(MsgFormId record);

    MsgFormId selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MsgFormId record);

    void removeExpiredFormid(long expireTime);

	int countByUid(Long uid);

	/**
	 * 获取某用户最早的formid
	 * @author xuhongyu
	 * @param uid
	 * @return
	 */
	MsgFormId getFurthestFormId(Long uid);
}