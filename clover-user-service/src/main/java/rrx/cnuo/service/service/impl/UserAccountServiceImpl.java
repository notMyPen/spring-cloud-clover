package rrx.cnuo.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rrx.cnuo.service.po.UserAccount;
import rrx.cnuo.service.service.UserAccountService;
import rrx.cnuo.service.service.data.UserAccountDataService;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired private UserAccountDataService userAccountDataService;
	
	@Override
	public void updateUserRegistCredit(Long uid) throws Exception{
		UserAccount param = new UserAccount();
		param.setUid(uid);
		param.setRegistCredit(true);
		userAccountDataService.updateByPrimaryKeySelective(param);
	}
}
