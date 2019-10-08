package rrx.cnuo.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import rrx.cnuo.cncommon.util.ClientToolUtil;
import rrx.cnuo.cncommon.vo.config.BasicConfig;
import rrx.cnuo.service.accessory.consts.TradeConst;
import rrx.cnuo.service.dao.UserAccountListMapper;
import rrx.cnuo.service.po.UserAccountList;
import rrx.cnuo.service.vo.MakeAccountListVo;
import rrx.cnuo.service.vo.request.PayBusinessVo;

/**
 * 封装支付过程中无需对外暴露的通用方法
 * @author xuhongyu
 */
public class PayBase{

//	@Autowired protected TradeMapper tradeMapper;
	@Autowired protected UserAccountListMapper userAccountListMapper;
	@Autowired protected BasicConfig basicConfig;
	
	/**
	 * 封装各个业务类型的支付明细
	 * @author xuhongyu
	 */
	/*protected void makeAccountList(PayBusinessVo payVo) {
		MakeAccountListVo userAccountListVo = new MakeAccountListVo();
		if (payVo.getPayBusinessType() == TradeConst.PayBusinessType.RECHARGE.getCode()) {// 充值
			rechargeMakeAccountList(payVo);
			if(payVo.getRechargeWithdrawFee() > 0){
				//充值费用  
				rechargeFeeMakeAccountList(payVo);
			}
		} else if (payVo.getPayBusinessType() == TradeConst.PayBusinessType.WITHDRAW.getCode()) {//提现
			// 提现的明细科目
			userAccountListVo.setUserId(payVo.getUserId());// 支出方
			userAccountListVo.setTargetUserId(TradeConst.SYSTEM_BANK);// 收款方
			userAccountListVo.setPayAcountType(TradeConst.AccountType.PAY_WITHDRAWALS.getCode());
			userAccountListVo.setRecvAcountType(TradeConst.AccountType.RCV_WITHDRAWALS.getCode());
			userAccountListVo.setAccountAmount(payVo.getTotalAmount());//提现金额
			makeUserPayTargetUserAccount(payVo, userAccountListVo);
			if(payVo.getRechargeWithdrawFee() > 0){
				//用户支出提现手续费、系统收到提现手续费
				userAccountListVo.setUserId(payVo.getUserId());// 支出方
				userAccountListVo.setTargetUserId(TradeConst.SYSTEM_USER);// 收款方
				userAccountListVo.setPayAcountType(TradeConst.AccountType.PAY_WITHDRAW_FEE.getCode());
				userAccountListVo.setRecvAcountType(TradeConst.AccountType.RCV_WITHDRAW_FEE.getCode());
				userAccountListVo.setAccountAmount(payVo.getRechargeWithdrawFee());//提现手续费
				makeUserPayTargetUserAccount(payVo, userAccountListVo);
			}
		}
	}*/

	/**
	 * 充值手续费做账
	 * @author xuhongyu
	 * @param payVo
	 */
	protected void rechargeFeeMakeAccountList(PayBusinessVo payVo) {
		MakeAccountListVo userAccountListVo = new MakeAccountListVo();
		userAccountListVo.setUserId(payVo.getUserId());// 支出方
		userAccountListVo.setTargetUserId(TradeConst.SYSTEM_USER);// 收款方
		userAccountListVo.setPayAcountType(TradeConst.AccountType.PAY_RECHARGE_FEE.getCode());
		userAccountListVo.setRecvAcountType(TradeConst.AccountType.RCV_RECHARGE_FEE.getCode());
		userAccountListVo.setAccountAmount(payVo.getRechargeWithdrawFee());
		makeUserPayTargetUserAccount(payVo, userAccountListVo);
	}

	/**
	 * 充值做账
	 * @author xuhongyu
	 * @param payVo
	 */
	protected void rechargeMakeAccountList(PayBusinessVo payVo) {
		MakeAccountListVo userAccountListVo = new MakeAccountListVo();
		userAccountListVo.setUserId(TradeConst.SYSTEM_BANK);// 支出方
		userAccountListVo.setTargetUserId(payVo.getUserId());// 收款方
		userAccountListVo.setPayAcountType(TradeConst.AccountType.PAY_RECHARGE.getCode());
		userAccountListVo.setRecvAcountType(TradeConst.AccountType.RCV_RECHARGE.getCode());
		userAccountListVo.setAccountAmount(payVo.getTotalAmount());//充值总金额(包括手续费)
		makeUserPayTargetUserAccount(payVo, userAccountListVo);
	}

	/**
	 * 做某用户支出给目标用户的支付明细(该方法只针对单条支付明细,不支持总分或分总)
	 * @author xuhongyu
	 */
	protected void makeUserPayTargetUserAccount(PayBusinessVo payVo,MakeAccountListVo userAccountListVo) {
		// 用户userId支出的明细科目
		UserAccountList userAccountList = new UserAccountList();
		userAccountList.setUid(userAccountListVo.getUserId());
		userAccountList.setFuid(userAccountListVo.getTargetUserId());
		userAccountList.setTradeType(payVo.getPayChannelType());
		userAccountList.setAcountType(userAccountListVo.getPayAcountType());// 支出资金变动类型
		userAccountList.setBusinessType(payVo.getPayBusinessType());
		userAccountList.setTradeId(payVo.getTradeId());
		userAccountList.setAmount(userAccountListVo.getAccountAmount() * (-1));
		if(payVo.isMakeTempAccount()){
			userAccountListMapper.insertTemp(userAccountList);
		}else{
			userAccountList.setId(ClientToolUtil.getDistributedId(basicConfig.getSnowflakeNode()));
			userAccountListMapper.insertSelective(userAccountList);
		}

		// 目标用户targetUserId收到用户支出的明细科目
		userAccountList.setUid(userAccountListVo.getTargetUserId());
		userAccountList.setFuid(userAccountListVo.getUserId());
		// userAccountList.setTradeType(payVo.getPayChannelType());
		userAccountList.setAcountType(userAccountListVo.getRecvAcountType());// 收款资金变动类型
		// userAccountList.setRelationType(payVo.getRelationType());
		// userAccountList.setRelationId(payVo.getRelationId());
		// userAccountList.setTradeId(payVo.getTradeId());
		userAccountList.setAmount(userAccountListVo.getAccountAmount());
		if(payVo.isMakeTempAccount()){
			userAccountListMapper.insertTemp(userAccountList);
		}else{
			userAccountList.setId(ClientToolUtil.getDistributedId(basicConfig.getSnowflakeNode()));
			userAccountListMapper.insertSelective(userAccountList);
		}
	}

}
