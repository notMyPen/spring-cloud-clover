package rrx.cnuo.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rrx.cnuo.service.dao.StatisUserMapper;
import rrx.cnuo.service.po.StatisUser;
import rrx.cnuo.service.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired private StatisUserMapper statisUserMapper;
	
	@Override
//	@LcnTransaction
	public void insertStatisUser() {
		StatisUser record = new StatisUser();
    	record.setUid(1212L);
    	record.setName((short) 121);
    	record.setValue(6000L);
    	statisUserMapper.insertSelective(record);
    	
//    	throw new IllegalStateException("by exFlag");
	}

}
