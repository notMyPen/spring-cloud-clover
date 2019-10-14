package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.ShareToFriend;

public interface ShareToFriendMapper {
    int deleteByPrimaryKey(Long uid);

    int insertSelective(ShareToFriend record);

    ShareToFriend selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(ShareToFriend record);

    /**
     * 获取用户分享给好友获得的奖励次数
     * @author xuhongyu
     * @param uid
     * @return
     */
	int getShareToFriendAwardCnt(Long uid);

}