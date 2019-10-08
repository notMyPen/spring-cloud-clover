package rrx.cnuo.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rrx.cnuo.service.dao.BoardStatisMapper;
import rrx.cnuo.service.po.BoardStatis;
import rrx.cnuo.service.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired private BoardStatisMapper boardStatisMapper;
	
	@Override
//	@LcnTransaction
	public void insertStatisUser() {
		BoardStatis record = new BoardStatis();
    	record.setUid(1212L);
    	boardStatisMapper.insertSelective(record);
    	
//    	throw new IllegalStateException("by exFlag");
	}

}
