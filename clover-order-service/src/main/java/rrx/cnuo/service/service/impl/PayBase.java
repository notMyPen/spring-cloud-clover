package rrx.cnuo.service.service.impl;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.util.ClientToolUtil;
import rrx.cnuo.cncommon.util.DateUtil;
import rrx.cnuo.cncommon.util.http.HttpClient;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.PayBusinessVo;
import rrx.cnuo.cncommon.vo.ReturnPayBusinessVo;
import rrx.cnuo.cncommon.vo.config.BasicConfig;
import rrx.cnuo.service.accessory.config.PayCenterConfigBean;
import rrx.cnuo.service.dao.TradeMapper;
import rrx.cnuo.service.dao.UserAccountListMapper;
import rrx.cnuo.service.feignclient.UserFeignService;
import rrx.cnuo.service.po.Trade;
import rrx.cnuo.service.po.UserAccountList;
import rrx.cnuo.service.service.UserOrderService;
import rrx.cnuo.service.vo.MakeAccountListVo;
import rrx.cnuo.service.vo.paycenter.AccountZone;
import rrx.cnuo.service.vo.paycenter.AccountZoneTo;
import rrx.cnuo.service.vo.paycenter.PayServiceVo;
import rrx.cnuo.service.vo.paycenter.ReturnAccountBalance;

/**
 * 封装发起支付过程中无需对外暴露的方法
 * @author xuhongyu
 */
@Slf4j
public class PayBase{

	@Autowired protected RedisTool redis;
	@Autowired protected TradeMapper tradeMapper;
	@Autowired protected UserAccountListMapper userAccountListMapper;
	@Autowired protected UserOrderService userOrderService;
	@Autowired protected UserFeignService userFeignService;
	@Autowired protected BasicConfig basicConfig;
	@Autowired protected PayCenterConfigBean payCenterConfigBean;
	
	/**
	 * 本地订单申请支付操作(已申请未报盘状态)
	 * @author xuhongyu
	 * @param payBusinessVo
	 * @param user 用户校验用户是否在支付中心开户，传null不需要校验
	 * @return
	 * @throws Exception
	 */
	protected JsonResult<ReturnPayBusinessVo> localPayApply(PayBusinessVo payBusinessVo,JSONObject userAccount) throws Exception {
		JsonResult<ReturnPayBusinessVo> result = new JsonResult<ReturnPayBusinessVo>();
        result.setStatus(JsonResult.SUCCESS);
        
		boolean tempAccount = false;//微信支付和银联支付需要临时做账
		if(payBusinessVo.getPayMethod() == Const.PayMethod.YUE.getCode()){
			payBusinessVo.setPayChannelType(Const.TradeType.BALANCE_PAY.getCode());
		}else if(payBusinessVo.getPayMethod() == Const.PayMethod.WECHAT.getCode()){
			if(payBusinessVo.getCashFlowType() == Const.CashFlowType.PAYMENT.getCode()){
				payBusinessVo.setPayChannelType(Const.TradeType.WX_WITHDRAW.getCode());//提现
			}else{
				tempAccount = true;
				payBusinessVo.setPayChannelType(Const.TradeType.MINI_WX_PAY.getCode());//充值
			}
		}
		
		//如果用户在支付中心未开户，去支付中心开户（user传null不需要校验）
		if(userAccount != null && !userAccount.getBooleanValue("openAccount")){
			if(!userOrderService.addAccountBalanceInfo(payBusinessVo.getUserId())){
				result.setStatus(JsonResult.FAIL);
	            result.setMsg("支付开户失败，请联系客服");
	            return result;
			}
			//TODO 分布式事务
			JSONObject userAccountParam = new JSONObject();
			userAccountParam.put("uid", payBusinessVo.getUserId());
			userAccountParam.put("openAccount", true);
			userFeignService.updateUserAccountByUidSelective(userAccountParam.toJSONString());
		}
		
		// 申请支付时候插入trade表生成交易id（后续作为商户订单号）
		Long tradeId = storageTrade(payBusinessVo);
		payBusinessVo.setTradeId(tradeId);
		
		if(tempAccount){
			//往做账临时表做账，并组合账目字段传给支付中心
			payBusinessVo.setMakeTempAccount(true);
			makeAccountList(payBusinessVo);
		}
		return result;
	}
	
	/**
	 * 本地订单确认支付操作(状态置为已报盘待回盘，余额支付或提现，报盘时当即扣除支付人的余额和可提现余额)
	 * @author xuhongyu
	 * @param payVo
	 * @throws Exception
	 * void
	 */
	protected void localPayConfirm(PayBusinessVo payVo) throws Exception {
        if (((payVo.getPayMethod() == Const.PayMethod.YUE.getCode() && payVo.getCashFlowType() == Const.CashFlowType.COLLECTION.getCode()) 
        		|| payVo.getPayBusinessType() == Const.PayBusinessType.WITHDRAW.getCode()) 
        		&& payVo.getAmount() != 0) {
        	//如果是余额支付或提现，报盘时当即扣除支付人的余额和可提现余额,回盘时如果失败再加回来(受益人的钱回盘成功再加)
        	//TODO 分布式事务
			JSONObject userAccountParam = new JSONObject();
			userAccountParam.put("balance", payVo.getUserId());
			userAccountParam.put("openAccount", payVo.getAmount() * (-1));//兼容成交分钱业务：这里的amount存的是赏金全额(而totalAmount中已被置为实际赏金)
			userAccountParam.put("withdrawBalance", payVo.getAmount() * (-1));
			userFeignService.updateUserAccountByUidSelective(userAccountParam.toJSONString());
        }
        if(payVo.getTotalAmount() > 0){
        	Trade trade = new Trade();
        	trade.setId(payVo.getTradeId());
        	trade.setTradeStatus(Const.TradeStatus.COMFIRM.getCode());// 已确认未回盘
        	trade.setSendTime(DateUtil.getSecond(new Date()));
        	tradeMapper.updateByPrimaryKeySelective(trade);
        }
    }
	
	/**
	 * 获取账目区字符串
	 * @author xuhongyu
	 * @param payBusinessVo
	 * @return
	 */
	protected AccountZone assembleAccountZone(PayBusinessVo payBusinessVo) {
		List<JSONObject> uidAmountList = null;
		if(payBusinessVo.isMakeTempAccount()){
			uidAmountList = userAccountListMapper.assembleTempAccountZoneByTradeId(payBusinessVo.getTradeId());
		}else{
			uidAmountList = userAccountListMapper.assembleAccountZoneByTradeId(payBusinessVo.getTradeId());
		}
		AccountZone accountZone = new AccountZone();
		if(uidAmountList != null && uidAmountList.size() > 0){
			accountZone.setComments(getBusinessNameByBusinessTpe(payBusinessVo.getPayBusinessType()));
			List<AccountZoneTo> accountZoneTos = new ArrayList<AccountZoneTo>();
			for(JSONObject uidAmountMap : uidAmountList){
				Long uid = uidAmountMap.getLong("uid");
				Integer amount = uidAmountMap.getInteger("amount");
				if(uid.intValue() == Const.SYSTEM_BANK){//uid是银行
					accountZone.setAmount(Math.abs(amount));
					if(payBusinessVo.getPayBusinessType() == Const.PayBusinessType.WITHDRAW.getCode()){
						//提现
						AccountZoneTo accountZoneTo = new AccountZoneTo(payBusinessVo.getPayMethod());
						accountZoneTo.setUid(uid);
						accountZoneTo.setAmount(Math.abs(amount));
						accountZoneTos.add(accountZoneTo);
					}else{
						accountZone.setUid(payBusinessVo.getUserId());
						if(payBusinessVo.getPayMethod() == Const.PayMethod.WECHAT.getCode()){
							accountZone.setFuid(new Long(Const.WEIXIN));//支付中心定义的微信支付
						}else if(payBusinessVo.getPayMethod() == Const.PayMethod.ALIPAY.getCode()){
							accountZone.setFuid(new Long(Const.ALIPAY));//支付中心定义的支付宝支付
						}else{
							accountZone.setFuid(new Long(Const.YINHANGKA));//支付中心定义的银行
						}
					}
				}else if(uid.equals(payBusinessVo.getUserId()) && amount < 0){
					//余额支付(不含撤销)或提现
					accountZone.setAmount(Math.abs(amount));
					accountZone.setUid(new Long(Const.XINYA));//支付中心定义的信呀
					accountZone.setFuid(payBusinessVo.getUserId());
				}else if(!uid.equals(payBusinessVo.getUserId()) && amount != 0){
					/*if(payBusinessVo.getPayBusinessType() == Const.PayBusinessType.REVOKE_FAST_LOAN.getCode()){
						//撤销支付
						accountZone.setAmount(Math.abs(amount));
						accountZone.setUid(payBusinessVo.getUserId());
						accountZone.setFuid(new Long(Const.SYSTEM_USER));//支付中心的信多多
					}else{*/
						AccountZoneTo accountZoneTo = new AccountZoneTo(payBusinessVo.getPayMethod());
						accountZoneTo.setUid(uid);
						accountZoneTo.setAmount(Math.abs(amount));
						accountZoneTos.add(accountZoneTo);
//					}
				}
			}
			accountZone.setTo(accountZoneTos);
		}
		return accountZone;
	}
	
	protected String getBusinessNameByBusinessTpe(byte businessType){
		if(businessType == Const.PayBusinessType.RECHARGE.getCode()){
			return "信多多充值";
		}else if(businessType == Const.PayBusinessType.WITHDRAW.getCode()){
			return "信多多提现";
		}else if(businessType == Const.PayBusinessType.SHOW_TELEPHONE.getCode()){
			return "信多多显示手机号";
		}else if(businessType == Const.PayBusinessType.DEAL_DIVIDE_MONEY.getCode()){
			return "信多多成交分钱";
		}
		return null;
	}
	
	/**
	 * 获取本地ip地址
	 * @author xuhongyu
	 * @return
	 * @throws Exception
	 */
	protected static String getCurrentIp() throws Exception {
		InetAddress addr = InetAddress.getLocalHost();
		return addr.getHostAddress();
	}

	/**
	 * 封装各个业务类型的支付明细
	 * @author xuhongyu
	 */
	protected void makeAccountList(PayBusinessVo payVo) {
		MakeAccountListVo userAccountListVo = new MakeAccountListVo();
		if (payVo.getPayBusinessType() == Const.PayBusinessType.RECHARGE.getCode()) {// 充值
			rechargeMakeAccountList(payVo);
			if(payVo.getRechargeWithdrawFee() > 0){
				//充值费用  
				rechargeFeeMakeAccountList(payVo);
			}
		} else if (payVo.getPayBusinessType() == Const.PayBusinessType.WITHDRAW.getCode()) {//提现
			// 提现的明细科目
			userAccountListVo.setUserId(payVo.getUserId());// 支出方
			userAccountListVo.setTargetUserId(Const.SYSTEM_BANK);// 收款方
			userAccountListVo.setPayAcountType(Const.AccountType.SYS_WITHDRAWALS.getCode());
			userAccountListVo.setRecvAcountType(Const.AccountType.SYS_WITHDRAWALS.getCode());
			userAccountListVo.setAccountAmount(payVo.getTotalAmount());//提现金额
			makeUserPayTargetUserAccount(payVo, userAccountListVo);
			if(payVo.getRechargeWithdrawFee() > 0){
				//用户支出提现手续费、系统收到提现手续费
				userAccountListVo.setUserId(payVo.getUserId());// 支出方
				userAccountListVo.setTargetUserId(Const.SYSTEM_USER);// 收款方
				userAccountListVo.setPayAcountType(Const.AccountType.PAY_WITHDRAW_FEE.getCode());
				userAccountListVo.setRecvAcountType(Const.AccountType.RCV_WITHDRAW_FEE.getCode());
				userAccountListVo.setAccountAmount(payVo.getRechargeWithdrawFee());//提现手续费
				makeUserPayTargetUserAccount(payVo, userAccountListVo);
			}
			//提现已经没有垫付手续费了
		}
	}

	/**
	 * 2.5元充值费做账
	 * @author xuhongyu
	 * @param payVo
	 */
	private void rechargeFeeMakeAccountList(PayBusinessVo payVo) {
		MakeAccountListVo userAccountListVo = new MakeAccountListVo();
		userAccountListVo.setUserId(payVo.getUserId());// 支出方
		userAccountListVo.setTargetUserId(Const.SYSTEM_USER);// 收款方
		userAccountListVo.setPayAcountType(Const.AccountType.PAY_RECHARGE_FEE.getCode());
		userAccountListVo.setRecvAcountType(Const.AccountType.RCV_RECHARGE_FEE.getCode());
		userAccountListVo.setAccountAmount(payVo.getRechargeWithdrawFee());
		makeUserPayTargetUserAccount(payVo, userAccountListVo);
	}

	/**
	 * 充值做账
	 * @author xuhongyu
	 * @param payVo
	 */
	private void rechargeMakeAccountList(PayBusinessVo payVo) {
		MakeAccountListVo userAccountListVo = new MakeAccountListVo();
		userAccountListVo.setUserId(Const.SYSTEM_BANK);// 支出方
		userAccountListVo.setTargetUserId(payVo.getUserId());// 收款方
		userAccountListVo.setPayAcountType(Const.AccountType.SYS_RECHARGE.getCode());
		userAccountListVo.setRecvAcountType(Const.AccountType.SYS_RECHARGE.getCode());
		userAccountListVo.setAccountAmount(payVo.getTotalAmount());
		makeUserPayTargetUserAccount(payVo, userAccountListVo);
	}

	/**
	 * 做某用户支出给目标用户的支付明细(该方法只针对单条支付明细,不支持总分或分总)
	 * 
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

	/**
	 * 交易时插入交易表
	 * @author xuhongyu
	 */
	protected Long storageTrade(PayBusinessVo payVo) throws Exception {
		Trade trade = new Trade();
		trade.setTradeStatus(Const.TradeStatus.APPLY.getCode());// 已申请
		if(payVo.getPayMethod() == Const.PayMethod.WECHAT.getCode()){
			trade.setSendTime(DateUtil.getSecond(new Date()));
		}
		trade.setTradeType(payVo.getPayChannelType());
		trade.setAmount(payVo.getTotalAmount());
		trade.setBusinessType(payVo.getPayBusinessType());
		trade.setWithdrawType(payVo.getCashFlowType() == Const.CashFlowType.PAYMENT.getCode());
		trade.setUid(payVo.getUserId());
		trade.setReserveData(payVo.getReserveData());
		trade.setId(ClientToolUtil.getDistributedId(basicConfig.getSnowflakeNode()));
		tradeMapper.insertSelective(trade);
		return trade.getId();
	}

	/**
	 * 根据各个支付业务用户给的基础金额算出待交易总金额(服务费规则不同)
	 * @author xuhongyu
	 * @param payVo
	 */
	protected void calculateFee(PayBusinessVo payVo){
		if(payVo.getPayBusinessType() == Const.PayBusinessType.RECHARGE.getCode()){
			int rechargeFee = new BigDecimal(payVo.getAmount()).multiply(new BigDecimal(Const.PAY_INFO.RECHARGE_FEE_RATE)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
			payVo.setRechargeWithdrawFee(rechargeFee);
			payVo.setTotalAmount(payVo.getAmount() + payVo.getRechargeWithdrawFee());// 充值金额
		}/*else if(payVo.getPayBusinessType() == Const.PayBusinessType.WITHDRAW.getCode()){
			payVo.setRechargeWithdrawFee(Const.N_PAYMENT_RATE.N_WITHDRAW_FEE);
			payVo.setTotalAmount(payVo.getAmount() - payVo.getRechargeWithdrawFee());//实际给用户的钱（原来还要减去垫资手续费，现在没了）
		}*/else{
			payVo.setTotalAmount(payVo.getAmount());
		}
	}

	/**
	 * 用户余额支付或提现前余额相关数据校验
	 * @author xuhongyu
	 */
	protected JsonResult<ReturnPayBusinessVo> checkYueBeforeTrade(PayBusinessVo payVo,JSONObject userAccount) throws Exception {
		JsonResult<ReturnPayBusinessVo> result = new JsonResult<ReturnPayBusinessVo>();
		result.setStatus(JsonResult.SUCCESS);
		
		int balance = userAccount.getIntValue("balance");
		// 检查用户余额
		if (balance < payVo.getTotalAmount().intValue()) {
			if(payVo.getPayBusinessType() != Const.PayBusinessType.DEAL_DIVIDE_MONEY.getCode()){
				String amtLeft = new BigDecimal(payVo.getTotalAmount() - balance).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).toString();
				String arrearsMsg = "";
        		if(balance < 0){
        			arrearsMsg = "，其中包含之前成交分赏金时的欠费部分";
                }
				result.setStatus(JsonResult.FAIL);
				result.setMsg("余额不足，还差"+amtLeft+"元"+arrearsMsg);
				return result;
			}else{
				if(balance > Const.PAY_INFO.MIN_BOUNTY_AMT){
					//虽然有余额但是不够分，扣余额(赏金的全部)，有多少余额分多少钱
					payVo.setTotalAmount(balance);
				}else{
					//没有余额，只扣余额(赏金的全部)，完全不分钱(即不需要实际支付)
					payVo.setTotalAmount(0);
					return result;
				}
			}
		}

		if(basicConfig.isPayByService()){
			Long uid = userAccount.getLong("uid");
			int withdrawBalance = userAccount.getIntValue("withdrawBalance");
			// 检查用户账户金额和可提现余额是否正确
			Integer accountListPaidAmt = userOrderService.getBalanceByUid(uid);
			if(balance != accountListPaidAmt){
				result.setStatus(JsonResult.FAIL);
				result.setMsg("用户账户余额对不上，请联系客服010-53153859");
				return result;
			}
			Integer accountListReconciledAmt = userOrderService.getWithdrawAmtByUid(uid);
			if(withdrawBalance != accountListReconciledAmt){
				result.setStatus(JsonResult.FAIL);
				result.setMsg("用户账户可提现余额对不上，请联系客服010-53153859");
				return result;
			}
			// 再校验我们的余额和支付中心的余额是否一致
			PayServiceVo payServiceVo = new PayServiceVo(payCenterConfigBean.getApiKey(),payCenterConfigBean.getApiSecret(),payCenterConfigBean.getMerchantSign());
			payServiceVo.setUid(uid);
			JsonResult<ReturnAccountBalance> returnResult = HttpClient.doPostWrapResult(payCenterConfigBean.getPayBaseUrl() + payCenterConfigBean.getPayQueryAccountBalanceInfo(),
					payServiceVo.toCollectionApplyJSONString(),ReturnAccountBalance.class);
			if(returnResult.getStatus() == JsonResult.SUCCESS && returnResult.getData() != null && returnResult.getData().getRespCode() == JsonResult.SUCCESS){
				if(returnResult.getData().getAmount() != null && returnResult.getData().getAmount() != balance){
					result.setStatus(JsonResult.FAIL);
					result.setMsg("信多多用户和支付中心的账户余额对不上，请联系客服");
					log.info("local:"+balance+" remote:"+returnResult.getData().getAmount());
					return result;
				}
			}
		}
		return result;
	}

	/**
	* @Description 支付方式 
	* @param  tradeType 支付通道类型  
	* @return 微信 、余额 、银行卡
	 */
	protected String getTradeTypeToString(byte tradeType){
		String tradeTypeStr = "";
		if(tradeType == Const.TradeType.MINI_WX_PAY.getCode()){
			tradeTypeStr = "微信";
		}else if(tradeType == Const.TradeType.BALANCE_PAY.getCode()){
			tradeTypeStr = "账户余额";
		}else{
			tradeTypeStr = "银行卡";
		}
		return tradeTypeStr;
	}
}
