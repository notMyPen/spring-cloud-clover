package rrx.cnuo.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rrx.cnuo.cncommon.vo.order.TradeVo;
import rrx.cnuo.service.dao.TradeMapper;
import rrx.cnuo.service.po.Trade;
import rrx.cnuo.service.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired private TradeMapper tradeMapper;
	
	@Override
//	@LcnTransaction
	public void insertTrade(TradeVo tradeVo) {
		Trade record = new Trade();
		record.setId(tradeVo.getId());
		record.setUid(tradeVo.getUid());
		tradeMapper.insertSelective(record);
	}

}
