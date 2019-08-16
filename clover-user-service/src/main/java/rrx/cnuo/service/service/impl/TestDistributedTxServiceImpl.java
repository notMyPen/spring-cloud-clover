package rrx.cnuo.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.cncommon.utils.StarterToolUtil;
import rrx.cnuo.cncommon.vo.order.TradeVo;
import rrx.cnuo.service.dao.UserAccountMapper;
import rrx.cnuo.service.feignclient.BizFeignService;
import rrx.cnuo.service.feignclient.OrderFeignService;
import rrx.cnuo.service.po.UserAccount;
import rrx.cnuo.service.service.TestDistributedTxService;

@Service
public class TestDistributedTxServiceImpl implements TestDistributedTxService {

	@Autowired private RedisTool instance;
	@Autowired private UserAccountMapper userAccountMapper;
	@Autowired private OrderFeignService orderFeignService;
	@Autowired private BizFeignService bizFeignService;
	
	@Override
//	@LcnTransaction
	public void insertUserOrderStatisUser(Boolean rollBack,TradeVo tradeVo) {
		if(rollBack == null){
			rollBack = false;
		}
		orderFeignService.insertTrade(tradeVo);
		
		bizFeignService.insertStatisUser();
		
		//本地事务
		UserAccount record = new UserAccount();
		record.setUid(StarterToolUtil.generatorLongId(instance));
		userAccountMapper.insertSelective(record);
		
		if(rollBack != null && rollBack){
			throw new IllegalStateException("by exFlag");
		}
	}

}
