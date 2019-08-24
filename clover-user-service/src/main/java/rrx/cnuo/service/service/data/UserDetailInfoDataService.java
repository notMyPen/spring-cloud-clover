package rrx.cnuo.service.service.data;

import rrx.cnuo.service.po.UserDetailInfo;

public interface UserDetailInfoDataService {

	void insertSelective(UserDetailInfo record) throws Exception;

    UserDetailInfo selectByPrimaryKey(Long uid) throws Exception;

    void updateByPrimaryKeySelective(UserDetailInfo record) throws Exception;
    
    void delUserDetailInfoFromRedis(long uid) throws Exception;
}
