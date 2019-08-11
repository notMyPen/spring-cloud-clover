package rrx.cnuo.service.dao;

import rrx.cnuo.service.po.UserAccount;

public interface UserAccountMapper {
    int deleteByPrimaryKey(Long uid);

    int insertSelective(UserAccount record);

    UserAccount selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(UserAccount record);

    /**
	 * 以累加的方式更新账户金额
	 * @author xuhongyu
	 * @param useraccount
	 */
	void updateUserAccountAccumulate(UserAccount record);
}