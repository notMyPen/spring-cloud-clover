package rrx.cnuo.service.service;

public interface UserAccountService {

	/**
	 * 更新用户是否注册信用中心状态为已注册
	 * @author xuhongyu
	 * @param uid
	 */
	void updateUserRegistCredit(Long uid) throws Exception;

}
