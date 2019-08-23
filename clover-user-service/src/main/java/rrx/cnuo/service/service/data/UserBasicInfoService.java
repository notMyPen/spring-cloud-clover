package rrx.cnuo.service.service.data;

import rrx.cnuo.service.po.UserBasicInfo;

public interface UserBasicInfoService {

	void insertSelective(UserBasicInfo record) throws Exception;

    UserBasicInfo selectByPrimaryKey(Long uid) throws Exception;

    void updateByPrimaryKeySelective(UserBasicInfo record) throws Exception;
    
    void delUserBasicInfoFromRedis(long uid) throws Exception;
}
