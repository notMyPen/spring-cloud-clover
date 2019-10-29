package rrx.cnuo.service.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.cncommon.accessory.context.UserContextHolder;
import rrx.cnuo.cncommon.util.ClientToolUtil;
import rrx.cnuo.cncommon.util.DateUtil;
import rrx.cnuo.cncommon.util.http.HttpClient;
import rrx.cnuo.cncommon.utils.SpringUtil;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.LoginUser;
import rrx.cnuo.cncommon.vo.config.BasicConfig;
import rrx.cnuo.service.accessory.config.PayCenterConfigBean;
import rrx.cnuo.service.accessory.consts.TradeConst;
import rrx.cnuo.service.dao.TradeMapper;
import rrx.cnuo.service.dao.UserAccountListMapper;
import rrx.cnuo.service.feignclient.UserFeignService;
import rrx.cnuo.service.po.Trade;
import rrx.cnuo.service.po.UserAccountList;
import rrx.cnuo.service.service.OrderInfoService;
import rrx.cnuo.service.service.PayProcessService;
import rrx.cnuo.service.service.PayService;
import rrx.cnuo.service.vo.paycenter.AccountZone;
import rrx.cnuo.service.vo.paycenter.AccountZoneTo;
import rrx.cnuo.service.vo.paycenter.PayServiceVo;
import rrx.cnuo.service.vo.paycenter.ReturnPayVo;
import rrx.cnuo.service.vo.request.PayBusinessVo;
import rrx.cnuo.service.vo.response.OrderStatusVo;
import rrx.cnuo.service.vo.response.ReturnPayBusinessVo;

@Service
@SuppressWarnings({"unchecked","rawtypes"})
public class PayProcessServiceImpl implements PayProcessService{

	@Autowired private TradeMapper tradeMapper;
	@Autowired private UserAccountListMapper userAccountListMapper;
	@Autowired private BasicConfig basicConfig;
	@Autowired private PayCenterConfigBean payCenterConfigBean;
	@Autowired private OrderInfoService userOrderService;
	@Autowired private UserFeignService userFeignService;
	@Autowired private SpringUtil springUtil;
	
	@Override
	public JsonResult<ReturnPayBusinessVo> updateLocalPayApply(PayBusinessVo payVo) throws Exception{
		PayService payService = (PayService) springUtil.getBean(TradeConst.PayBusinessType.getPayBusinessBeanName(payVo.getPayBusinessType()));
		
		JsonResult result = checkBeforePay(payVo);
		if(!result.isOk()){
        	return result;
        }
		LoginUser loginUser = UserContextHolder.currentUser();
		
		Long uid = loginUser.getUserId();
		payVo.setUserId(uid);
		payService.setCashFlowType(payVo);
		payService.setFeeAndTotalAmount(payVo);
		
		result = payService.checkBusinessBeforePay(payVo);
		if(!result.isOk()){
        	return result;
        }
		
		boolean tempAccount = false;//微信支付和银联支付需要临时做账
		if(payVo.getPayMethod() == TradeConst.PayMethod.WECHAT.getCode() || payVo.getPayMethod() == TradeConst.PayMethod.APP_WECHAT.getCode()) {
			if(payVo.getCashFlowType() == TradeConst.CashFlowType.COLLECTION.getCode()){
				tempAccount = true;
				payVo.setPayChannelType(TradeConst.TradeType.MINI_WX_PAY.getCode());
			}else{
				payVo.setPayChannelType(TradeConst.TradeType.WX_WITHDRAW.getCode());
			}
			if(StringUtils.isBlank(loginUser.getMiniOpenId())) {
				return JsonResult.fail(JsonResult.FAIL, "openId为空，当前登录不支持微信支付，请重新登录后重试");
			}
			payVo.setOpenId(loginUser.getMiniOpenId());
			payService.setReserveData(payVo);
		}/*else if(payVo.getPayMethod() == TradeConst.PayMethod.YUE.getCode()){
			payVo.setPayChannelType(TradeConst.TradeType.BALANCE_PAY.getCode());
		}*///TODO 后续如果接入其它支付方式，在这里加入else if然后set他们的PayChannelType(非官方的微信支付或银行卡支付方式需要去支付中心查询支付通道)
		
		//如果用户在支付中心未开户，去支付中心开户（user传null不需要校验）
		JSONObject userAccount = userFeignService.getUserAccountByUid(uid);
		if(userAccount != null && !userAccount.getBooleanValue("openAccount")){
			if(!userOrderService.addAccountBalanceInfo(payVo.getUserId())){
				result.setStatus(JsonResult.FAIL);
	            result.setMsg("支付开户失败，请联系客服");
	            return result;
			}
			JSONObject userAccountParam = new JSONObject();
			userAccountParam.put("uid", payVo.getUserId());
			userAccountParam.put("openAccount", true);
			userFeignService.updateUserAccountByUidSelective(userAccountParam.toJSONString());
		}
		
		// 申请支付时候插入trade表生成交易id（后续作为商户订单号）
		Long tradeId = storageTrade(payVo);
		payVo.setTradeId(tradeId);
		
		if(tempAccount){
			//往做账临时表做账，并组合账目字段传给支付中心
			payVo.setMakeTempAccount(true);
			payService.makeAccountList(payVo);
		}
		return JsonResult.ok();
	}
	
	@Override
	public JsonResult<ReturnPayBusinessVo> updateRemotePayApply(PayBusinessVo payBusinessVo) throws Exception {
		JsonResult<ReturnPayBusinessVo> result = new JsonResult<ReturnPayBusinessVo>();
		result.setStatus(JsonResult.SUCCESS);
		
		ReturnPayBusinessVo returnPayBusinessVo = new ReturnPayBusinessVo();
//		returnPayBusinessVo.setPayChannelType(payBusinessVo.getPayChannelType());
		returnPayBusinessVo.setTradeId(payBusinessVo.getTradeId());
		
		JsonResult<ReturnPayVo> returnPayResult = apply(payBusinessVo, returnPayBusinessVo);
		result.setStatus(returnPayResult.getStatus());
		result.setMsg(returnPayResult.getMsg());
		result.setData(returnPayBusinessVo);
		if(StringUtils.isNotBlank(returnPayBusinessVo.getPayOrderNo())){
			//更新trade表payOrderNo
			Trade tradeParam = new Trade();
			tradeParam.setId(payBusinessVo.getTradeId());
			tradeParam.setPayOrderNo(returnPayBusinessVo.getPayOrderNo());
			tradeMapper.updateByPrimaryKeySelective(tradeParam);
		}
		return result;
	}
    
    private JsonResult<ReturnPayVo> apply(PayBusinessVo payBusinessVo,ReturnPayBusinessVo returnPayBusinessVo) throws Exception{
		JsonResult<ReturnPayVo> returnPayResult = new JsonResult<ReturnPayVo>();
		returnPayResult.setStatus(JsonResult.SUCCESS);
		
		// 是否真的去支付（测试环境为false）
		if (!basicConfig.isPayByService()) {
			returnPayBusinessVo.setPayOrderNo(payBusinessVo.getTradeId() + "");
			returnPayBusinessVo.setPayToken(payBusinessVo.getTradeId() + "");
			return returnPayResult;
		}
		// 其它情况(包括非协议支付的绑卡业务以及所有代收业务的申请)
		PayServiceVo payServiceVo = new PayServiceVo(payCenterConfigBean.getApiKey(),payCenterConfigBean.getApiSecret(),payCenterConfigBean.getMerchantSign());
		if(payBusinessVo.getPayMethod() == TradeConst.PayMethod.WECHAT.getCode()){
			payServiceVo.setOpenId(payBusinessVo.getOpenId());
		}
		payServiceVo.setPayChannelType(payBusinessVo.getPayChannelType());
		payServiceVo.setOrderNo(payBusinessVo.getTradeId());
		payServiceVo.setAmount(payBusinessVo.getTotalAmount());
		payServiceVo.setUid(payBusinessVo.getUserId());
		if(payBusinessVo.getPayMethod() == TradeConst.PayMethod.WECHAT.getCode()){
			AccountZone accountZone = assembleAccountZone(payBusinessVo);
			payServiceVo.setAccountZone(JSONObject.toJSONString(accountZone));//做账字符串
		}
		payServiceVo.setOrderSubject(TradeConst.PayBusinessType.getPayBusinessType(payBusinessVo.getPayBusinessType()).getMessage());
		returnPayResult = HttpClient.doPostWrapResult(payCenterConfigBean.getPayBaseUrl() + payCenterConfigBean.getPayCollectionApply(),
				payServiceVo.toCollectionApplyJSONString(),ReturnPayVo.class);
		
		if (returnPayResult.getStatus() == JsonResult.SUCCESS) {
			if(returnPayResult.getData().getRespCode() == JsonResult.SUCCESS){
				returnPayBusinessVo.setPayOrderNo(returnPayResult.getData().getPayOrderNo());
				returnPayBusinessVo.setPayToken(returnPayResult.getData().getPayTokenStr());
			}else{
				returnPayResult.setStatus(returnPayResult.getData().getRespCode());
				returnPayResult.setMsg(returnPayResult.getData().getRespMsg());
			}
		}
		return returnPayResult;
	}
    
    @Override
    public JsonResult updateReceiveBank(Long orderId, int orderStatus, String msg) throws Exception {
        boolean status = orderStatus == 1 ? true : false;//将支付中心的订单状态转换为今借到的处理结果
        //将支付中心的订单状态（1:成功, 2:失败, 3:过期, 4:关闭）转换为今借到的订单状态
        byte tradeStatus = TradeConst.TradeStatus.CANCEL.getCode();
        if (orderStatus == 1) {
            tradeStatus = TradeConst.TradeStatus.SUCCESS.getCode();
        } else if (orderStatus == 2) {
            tradeStatus = TradeConst.TradeStatus.FAIL.getCode();
        }

        Trade trade = tradeMapper.selectByPrimaryKeyForUpdate(orderId);
        if (null == trade) {
            return JsonResult.fail(JsonResult.FAIL, "订单不存在");
        }
        if (trade.getTradeStatus() > TradeConst.TradeStatus.COMFIRM.getCode()) {//已回过盘的
            return JsonResult.ok();
        }
        PayService payService = (PayService) springUtil.getBean(TradeConst.PayBusinessType.getPayBusinessBeanName(trade.getBusinessType()));
        
        PayBusinessVo payVo = null;
        boolean sendBankSupplement = false;//是否补充报盘（银联和微信支付由于不知道何时报盘，所以报盘操作放到回盘中）
        if(trade.getTradeType() == TradeConst.TradeType.MINI_WX_PAY.getCode()){
        	//如果是支付宝/微信第三方或银联第三方，由于不知道何时帮盘，只能在回盘的时补报盘
            sendBankSupplement = true;
            if(StringUtils.isBlank(trade.getReserveData())) {
            	return JsonResult.fail(JsonResult.FAIL, "订单有误：reserveData不能为空！");
            }
        	payVo = new PayBusinessVo();
        	payVo.setUserId(trade.getUid());
        	payVo.setTradeId(trade.getId());
        	payVo.setCashFlowType(trade.getWithdrawType() ? TradeConst.CashFlowType.PAYMENT.getCode() : TradeConst.CashFlowType.COLLECTION.getCode());
        	payVo.setPayChannelType(trade.getTradeType());
        	payVo.setPayBusinessType(trade.getBusinessType());
        }else{
        	//除了支付宝/微信和银联，其它支付方式：报盘后状态是2
        	if (trade.getTradeStatus() != TradeConst.TradeStatus.COMFIRM.getCode()) {
        		return JsonResult.fail(JsonResult.FAIL, "订单状态有误");
        	}
        }
        boolean result = tradeStatus == TradeConst.TradeStatus.SUCCESS.getCode() ? true : false;
        if (sendBankSupplement) {
        	if(tradeStatus != TradeConst.TradeStatus.CANCEL.getCode()) {
        		JSONObject reserve = JSONObject.parseObject(trade.getReserveData());
        		payService.setUserParamsFromReserveData(payVo, reserve);
        		payService.setFeeAndTotalAmount(payVo);
        		payService.updateBusinessLocal(payVo);
        		payService.makeAccountList(payVo);
        		payService.updateReceiveTrade(trade,result,msg);
        	}
        }else {
        	payService.updateReceiveTrade(trade,result,msg);
        }
        if (!trade.getWithdrawType()) {
            //充值相关业务的相关用户余额变动在回盘时计算
            if (status) {
            	List<JSONObject> userMap = null;
                if (trade.getTradeType() != TradeConst.TradeType.BALANCE_PAY.getCode()) {
                	//非余额支付回盘成功时，所有相关用户金额变动
                    userMap = userAccountListMapper.getAccountListAmtNotSysAndBankByTradeId(trade.getId());
                }else{
                	//余额支付回盘成功时，除支付人以外的相关用户金额变动
                	UserAccountList userAccountList = new UserAccountList();
                	userAccountList.setTradeId(trade.getId());
                	userAccountList.setUid(trade.getUid());
                    userMap = userAccountListMapper.getAccountListAmtNotSysBankAndUidByTradeId(userAccountList);
                }
                String userAccountListStatis = JSON.toJSONString(userMap);
                userFeignService.updateUserAccountAccumulateAboutOrder(userAccountListStatis, (byte) 1,false);
            } else {
                //余额支付回盘支付失败时，支付人的金额变动回滚(加回来)
                if (trade.getTradeType() == TradeConst.TradeType.BALANCE_PAY.getCode()) {
                    // 获取因为此次交易每个人的账户变动情况
                	UserAccountList userAccountList = new UserAccountList();
                	userAccountList.setTradeId(trade.getId());
                	userAccountList.setUid(trade.getUid());
                	List<JSONObject> userMap = userAccountListMapper.getAccountListAmtByTradeIdAndUid(userAccountList);
                    String userAccountListStatis = JSON.toJSONString(userMap);
                    userFeignService.updateUserAccountAccumulateAboutOrder(userAccountListStatis, (byte) 0,true);
                }
            }
        } else {//代付
        	if(status){//交易成功
        		if (trade.getTradeType() == TradeConst.TradeType.BALANCE_PAY.getCode()) {//退款到余额
        			//退款到余额业务,在回盘成功时退还用户余额,做账时再退可提现余额
        			List<JSONObject> userMap = userAccountListMapper.getAccountListAmtNotSysAndBankByTradeId(trade.getId());
        			String userAccountListStatis = JSON.toJSONString(userMap);
        			userFeignService.updateUserAccountAccumulateAboutOrder(userAccountListStatis, (byte) 1,false);
        		}
        	}else{//交易失败
        		if (trade.getTradeType() != TradeConst.TradeType.BALANCE_PAY.getCode()) {//提现
        			//提现在报盘时就把余额和可提现余额减去了，回盘的时候如果支付失败，再给加回来
        			List<JSONObject> userMap = userAccountListMapper.getAccountListAmtNotSysAndBankByTradeId(trade.getId());
        			String userAccountListStatis = JSON.toJSONString(userMap);
        			userFeignService.updateUserAccountAccumulateAboutOrder(userAccountListStatis, (byte) 0,true);
        		}
        	}
        }

        int now = DateUtil.getSecond(new Date());
        Trade tradevo = new Trade();
        tradevo.setId(trade.getId());
        if(orderStatus == TradeConst.TradeStatus.ABANDON.getCode()){
        	tradevo.setTradeStatus(TradeConst.TradeStatus.ABANDON.getCode());
        }else{
        	tradevo.setTradeStatus(tradeStatus);//tradeStatus字段的值不能改变
        }
        tradevo.setReceiveTime(now);
        tradeMapper.updateByPrimaryKeySelective(tradevo);

        UserAccountList userAccountList = new UserAccountList();
        userAccountList.setTradeId(trade.getId());
        userAccountList.setReceiveBankStatus(true);
        userAccountList.setValidStatus(status);
        userAccountList.setReceiveTime(now);
        userAccountListMapper.updateByPrimaryKeySelective(userAccountList);
        
        //如果微信或银联支付成功后删掉做账临时表数据
        if (trade.getTradeType() != TradeConst.TradeType.BALANCE_PAY.getCode()) {
            userAccountListMapper.deleteTempUserAccountList(orderId);
        }
        return JsonResult.ok();
    }
    
    /**
	 * 各个业务支付发起前的公共校验部分（以后如果要加支付密码、验证码、被封/冻结等公共校验加在这里）
	 * @author xuhongyu
	 * @param payVo
	 * @return
	 */
	private JsonResult checkBeforePay(PayBusinessVo payVo){
		if(payVo.getAmount() == null || payVo.getAmount() <= 0) {
			return JsonResult.fail(JsonResult.FAIL, "金额不能为空且大于0");
		}
		if(payVo.getPayBusinessType() == null) {
			return JsonResult.fail(JsonResult.FAIL, "参数payBusinessType不能为空");
		}
		if(!TradeConst.PayBusinessType.isContain(payVo.getPayBusinessType())) {
			return JsonResult.fail(JsonResult.FAIL, "不支持该业务场景："+payVo.getPayBusinessType());
		}
		if(payVo.getPayMethod() == null) {
			return JsonResult.fail(JsonResult.FAIL, "参数payMethod不能为空");
		}
		if(!TradeConst.PayMethod.isContain(payVo.getPayMethod())) {
			return JsonResult.fail(JsonResult.FAIL, "不支持该支付方式："+payVo.getPayMethod());
		}
		return JsonResult.ok();
	}
	
	/**
	 * 获取账目区字符串
	 * @author xuhongyu
	 * @param payBusinessVo
	 * @return
	 */
	private AccountZone assembleAccountZone(PayBusinessVo payBusinessVo) {
		List<JSONObject> uidAmountList = null;
		if(payBusinessVo.isMakeTempAccount()){
			uidAmountList = userAccountListMapper.assembleTempAccountZoneByTradeId(payBusinessVo.getTradeId());
		}else{
			uidAmountList = userAccountListMapper.assembleAccountZoneByTradeId(payBusinessVo.getTradeId());
		}
		AccountZone accountZone = new AccountZone();
		if(uidAmountList != null && uidAmountList.size() > 0){
			accountZone.setComments(TradeConst.PayBusinessType.getPayBusinessType(payBusinessVo.getPayBusinessType()).getMessage());
			List<AccountZoneTo> accountZoneTos = new ArrayList<AccountZoneTo>();
			for(JSONObject uidAmountMap : uidAmountList){
				Long uid = uidAmountMap.getLong("uid");
				Integer amount = uidAmountMap.getInteger("amount");
				if(uid.intValue() == TradeConst.SYSTEM_BANK){//uid是银行
					accountZone.setAmount(Math.abs(amount));
					if(payBusinessVo.getPayBusinessType() == TradeConst.PayBusinessType.WITHDRAW.getCode()){
						//提现
						AccountZoneTo accountZoneTo = new AccountZoneTo(payBusinessVo.getPayMethod());
						accountZoneTo.setUid(uid);
						accountZoneTo.setAmount(Math.abs(amount));
						accountZoneTos.add(accountZoneTo);
					}else{
						accountZone.setUid(payBusinessVo.getUserId());
						if(payBusinessVo.getPayMethod() == TradeConst.PayMethod.WECHAT.getCode() || 
								payBusinessVo.getPayMethod() == TradeConst.PayMethod.APP_WECHAT.getCode()){
							accountZone.setFuid(new Long(TradeConst.WEIXIN));//支付中心定义的微信支付
						}else if(payBusinessVo.getPayMethod() == TradeConst.PayMethod.APP_ZFB.getCode()){
							accountZone.setFuid(new Long(TradeConst.ALIPAY));//支付中心定义的支付宝支付
						}else{
							accountZone.setFuid(new Long(TradeConst.YINHANGKA));//支付中心定义的银行
						}
					}
				}else if(uid.equals(payBusinessVo.getUserId()) && amount < 0){
					//余额支付(不含撤销)或提现
					accountZone.setAmount(Math.abs(amount));
					accountZone.setUid(new Long(TradeConst.XINYA));//支付中心定义的信呀
					accountZone.setFuid(payBusinessVo.getUserId());
				}else if(!uid.equals(payBusinessVo.getUserId()) && amount != 0){
					AccountZoneTo accountZoneTo = new AccountZoneTo(payBusinessVo.getPayMethod());
					accountZoneTo.setUid(uid);
					accountZoneTo.setAmount(Math.abs(amount));
					accountZoneTos.add(accountZoneTo);
				}
			}
			accountZone.setTo(accountZoneTos);
		}
		return accountZone;
	}
	
	/**
	 * 交易时插入交易表
	 * @author xuhongyu
	 */
	private Long storageTrade(PayBusinessVo payVo) throws Exception {
		Trade trade = new Trade();
		trade.setTradeStatus(TradeConst.TradeStatus.APPLY.getCode());// 已申请
		if(payVo.getPayMethod() == TradeConst.PayMethod.WECHAT.getCode()){
			trade.setSendTime(DateUtil.getSecond(new Date()));
		}
		trade.setTradeType(payVo.getPayChannelType());
		trade.setAmount(payVo.getTotalAmount());
		trade.setBusinessType(payVo.getPayBusinessType());
		trade.setWithdrawType(payVo.getCashFlowType() == TradeConst.CashFlowType.PAYMENT.getCode());
		trade.setUid(payVo.getUserId());
		trade.setReserveData(payVo.getReserveData());
		trade.setId(ClientToolUtil.getDistributedId(basicConfig.getSnowflakeNode()));
		tradeMapper.insertSelective(trade);
		return trade.getId();
	}

	@Override
	public void checkEnd(Long tradeId,Byte businessType) throws Exception {
		PayService payService = (PayService) springUtil.getBean(TradeConst.PayBusinessType.getPayBusinessBeanName(businessType));
		payService.checkEnd(tradeId);
	}

	@Override
    public List<Trade> getOrderToDealwith() throws Exception {
        return tradeMapper.getOrderToDealwith();
    }
	
	@Override
	public JsonResult<OrderStatusVo> getOrderStatus(long tradeId,Long fuid) {
		Trade trade = tradeMapper.selectByPrimaryKey(tradeId);
		OrderStatusVo orderStatusVo = new OrderStatusVo();
		orderStatusVo.setTradeStatus(trade.getTradeStatus());
		if(trade.getBusinessType() == TradeConst.PayBusinessType.BUY_CARD.getCode() && fuid != null) {
			String wxAccount = userFeignService.getUserWxAccount(fuid);
			orderStatusVo.setWxAccount(wxAccount);
		}
		return JsonResult.ok(orderStatusVo);
	}
    
    /*@Override
    public JsonResult<ReturnPayBusinessVo> updateLocalPayConfirm(PayBusinessVo payVo) throws Exception{
    	JsonResult result = checkBeforePay(payVo);
		if(!result.isOk()){
        	return result;
        }
		LoginUser loginUser = UserContextHolder.currentUser();
		
		Long uid = loginUser.getUserId();
		payVo.setUserId(uid);
		setCashFlowType(payVo);
		setFeeAndTotalAmount(payVo);
		
		result = checkBusinessBeforePay(payVo);
		if(!result.isOk()){
        	return result;
        }
		
		if(payVo.getPayMethod() == TradeConst.PayMethod.YUE.getCode()){
			payVo.setPayChannelType(TradeConst.TradeType.BALANCE_PAY.getCode());
			
			result = checkYueBeforeTrade(payVo);
			if(result.getStatus()!=JsonResult.SUCCESS){
				return result;
			}
		}

		result = checkConfirm(payVo);
		if(result.getStatus()!=JsonResult.SUCCESS){
			return result;
		}
		payService.updateBusinessLocal(payVo);
    	payService.makeAccountList(payVo);
		
		localPayConfirm(payVo);
		return result;
	}*/
    
    /*@Override
    public JsonResult<ReturnPayBusinessVo> updateRemotePayConfirm(PayBusinessVo payBusinessVo) throws Exception{
    	JsonResult<ReturnPayBusinessVo> result = new JsonResult<ReturnPayBusinessVo>();
        result.setStatus(JsonResult.SUCCESS);
        
        if(!basicConfig.isPayByService()){
        	return result;
        }
        
        AccountZone accountZone = assembleAccountZone(payBusinessVo);
        PayServiceVo payServiceVo = new PayServiceVo(payCenterConfigBean.getApiKey(),payCenterConfigBean.getApiSecret(),payCenterConfigBean.getMerchantSign());
        String url = null;
        if (payBusinessVo.getPayMethod() == TradeConst.PayMethod.YUE.getCode()) {//余额支付方式
            // 如果是app平台的余额支付，还要校验验证码是否正确，这个在之前已经做过了
            payServiceVo.setPayChannelType(payBusinessVo.getPayChannelType());
            payServiceVo.setOrderNo(payBusinessVo.getTradeId());
            payServiceVo.setAmount(payBusinessVo.getTotalAmount());
            payServiceVo.setUid(payBusinessVo.getUserId());
            payServiceVo.setOrderSubject(TradeConst.PayBusinessType.getPayBusinessType(payBusinessVo.getPayBusinessType()).getMessage());
            payServiceVo.setAccountZone(JSONObject.toJSONString(accountZone));//做账字符串
    		payServiceVo.setPaymentType((byte) 2);
            url = payCenterConfigBean.getPayBaseUrl() + payCenterConfigBean.getPayByBalance();
        } else if (payBusinessVo.getPayBusinessType() == TradeConst.PayBusinessType.WITHDRAW.getCode()) {//提现业务确认
            payServiceVo.setPayChannelType(payBusinessVo.getPayChannelType());
            payServiceVo.setOrderNo(payBusinessVo.getTradeId());
            payServiceVo.setAmount(payBusinessVo.getTotalAmount());
            payServiceVo.setUid(payBusinessVo.getUserId());
            payServiceVo.setOpenId(payBusinessVo.getOpenId());
            payServiceVo.setOrderSubject(TradeConst.PayBusinessType.getPayBusinessType(payBusinessVo.getPayBusinessType()).getMessage());
            payServiceVo.setAccountZone(JSONObject.toJSONString(accountZone));//做账字符串
            payServiceVo.setPaymentType((byte) 3);//代付类型|必填(1:银行卡代付,2:余额支付,3,微信提现, 4:退款, 5:撤销)
            url = payCenterConfigBean.getPayBaseUrl() + payCenterConfigBean.getPayPaymentConfirm();
        } else {
            // 其它情况(包括非协议支付的绑卡业务以及其它支付业务的确认)
            payServiceVo.setPayChannelType(payBusinessVo.getPayChannelType());
            payServiceVo.setOrderNo(payBusinessVo.getTradeId());
            payServiceVo.setPayOrderNo(payBusinessVo.getPayOrderNo());
            payServiceVo.setPayToken(payBusinessVo.getPayToken());
//            payServiceVo.setAuthCode(payBusinessVo.getAuthCode());
            payServiceVo.setAccountZone(JSONObject.toJSONString(accountZone));//做账字符串
            url = payCenterConfigBean.getPayBaseUrl() + payCenterConfigBean.getPayCollectionConfirm();
        }
        JsonResult<ReturnPayVo> returnPayResult = HttpClient.doPostWrapResult(url, payServiceVo.toCollectionApplyJSONString(), ReturnPayVo.class);
        if (returnPayResult.getStatus() == JsonResult.SUCCESS) {
            if (returnPayResult.getData().getRespCode() == JsonResult.SUCCESS) {
            	ReturnPayBusinessVo returnPayBusinessVo = new ReturnPayBusinessVo();
            	returnPayBusinessVo.setPayOrderNo(returnPayResult.getData().getPayOrderNo());
                returnPayBusinessVo.setPayToken(returnPayResult.getData().getPayTokenStr());
                if (payBusinessVo.getPayBusinessType() == TradeConst.PayBusinessType.WITHDRAW.getCode()
                        || payBusinessVo.getPayMethod() == TradeConst.PayMethod.YUE.getCode()) {
                	//更新trade表payOrderNo
                	Trade tradeParam = new Trade();
        			tradeParam.setId(payBusinessVo.getTradeId());
        			tradeParam.setPayOrderNo(returnPayResult.getData().getPayOrderNo());
        			tradeMapper.updateByPrimaryKeySelective(tradeParam);
                }
                result.setData(returnPayBusinessVo);
            } else {
            	result.setStatus(returnPayResult.getData().getRespCode());
            	result.setMsg(returnPayResult.getData().getRespMsg());
            }
        }else{
        	result.setStatus(returnPayResult.getStatus());
        	result.setMsg(returnPayResult.getMsg());
        }
        return result;
    }*/
	
	/**
	 * 本地订单确认支付操作(状态置为已报盘待回盘，余额支付或提现，报盘时当即扣除支付人的余额和可提现余额)
	 * @author xuhongyu
	 * @param payVo
	 * @throws Exception
	 * void
	 */
	/*protected void localPayConfirm(PayBusinessVo payVo) throws Exception {
        if (((payVo.getPayMethod() == TradeConst.PayMethod.YUE.getCode() && payVo.getCashFlowType() == TradeConst.CashFlowType.COLLECTION.getCode()) 
        		|| payVo.getPayBusinessType() == TradeConst.PayBusinessType.WITHDRAW.getCode()) 
        		&& payVo.getAmount() != 0) {
        	//如果是余额支付或提现，报盘时当即扣除支付人的余额和可提现余额,回盘时如果失败再加回来(受益人的钱回盘成功再加)
			JSONObject userAccountParam = new JSONObject();
			userAccountParam.put("balance", payVo.getUserId());
			userAccountParam.put("openAccount", payVo.getAmount() * (-1));//兼容成交分钱业务：这里的amount存的是赏金全额(而totalAmount中已被置为实际赏金)
			userAccountParam.put("withdrawBalance", payVo.getAmount() * (-1));
			userFeignService.updateUserAccountByUidSelective(userAccountParam.toJSONString());
        }
        if(payVo.getTotalAmount() > 0){
        	Trade trade = new Trade();
        	trade.setId(payVo.getTradeId());
        	trade.setTradeStatus(TradeConst.TradeStatus.COMFIRM.getCode());// 已确认未回盘
        	trade.setSendTime(DateUtil.getSecond(new Date()));
        	tradeMapper.updateByPrimaryKeySelective(trade);
        }
    }*/

	/**
	 * 用户余额支付或提现前余额相关数据校验
	 * @author xuhongyu
	 */
	/*protected JsonResult<ReturnPayBusinessVo> checkYueBeforeTrade(PayBusinessVo payVo,JSONObject userAccount) throws Exception {
		JsonResult<ReturnPayBusinessVo> result = new JsonResult<ReturnPayBusinessVo>();
		result.setStatus(JsonResult.SUCCESS);
		
		int balance = userAccount.getIntValue("balance");
		// 检查用户余额
		if (balance < payVo.getTotalAmount().intValue()) {
			if(payVo.getPayBusinessType() != TradeConst.PayBusinessType.DEAL_DIVIDE_MONEY.getCode()){
				String amtLeft = new BigDecimal(payVo.getTotalAmount() - balance).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).toString();
				String arrearsMsg = "";
        		if(balance < 0){
        			arrearsMsg = "，其中包含之前成交分赏金时的欠费部分";
                }
				result.setStatus(JsonResult.FAIL);
				result.setMsg("余额不足，还差"+amtLeft+"元"+arrearsMsg);
				return result;
			}else{
				if(balance > TradeConst.PAY_INFO.MIN_BOUNTY_AMT){
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
	}*/
}
