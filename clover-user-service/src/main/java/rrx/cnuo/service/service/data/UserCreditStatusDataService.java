package rrx.cnuo.service.service.data;

import rrx.cnuo.service.po.UserCreditStatus;

public interface UserCreditStatusDataService {

	void insertSelective(UserCreditStatus record) throws Exception;

    UserCreditStatus selectByPrimaryKey(Long uid) throws Exception;

    void updateByPrimaryKeySelective(UserCreditStatus record) throws Exception;
    
    void delUserCreditStatusFromRedis(long uid) throws Exception;
}
