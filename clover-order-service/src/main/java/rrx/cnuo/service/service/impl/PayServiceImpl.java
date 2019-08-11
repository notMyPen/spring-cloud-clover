package rrx.cnuo.service.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.util.DateUtil;
import rrx.cnuo.cncommon.util.http.HttpClient;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.PayBusinessVo;
import rrx.cnuo.cncommon.vo.ReturnPayBusinessVo;
import rrx.cnuo.service.dao.TradeMapper;
import rrx.cnuo.service.po.Trade;
import rrx.cnuo.service.po.UserAccountList;
import rrx.cnuo.service.service.PayService;
import rrx.cnuo.service.service.RechargeService;
import rrx.cnuo.service.service.WithdrawService;
import rrx.cnuo.service.vo.paycenter.AccountZone;
import rrx.cnuo.service.vo.paycenter.PayServiceVo;
import rrx.cnuo.service.vo.paycenter.ReturnPayVo;

@Service
public class PayServiceImpl extends PayBase implements PayService {

    @Autowired private RechargeService rechargeService;
    @Autowired private WithdrawService withDrawService;
    @Autowired private TradeMapper tradeMapper;

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
		if(payBusinessVo.getPayMethod() == Const.PayMethod.WECHAT.getCode()){
			payServiceVo.setOpenId(payBusinessVo.getOpenId());
		}
		payServiceVo.setPayChannelType(payBusinessVo.getPayChannelType());
		payServiceVo.setOrderNo(payBusinessVo.getTradeId());
		payServiceVo.setAmount(payBusinessVo.getTotalAmount());
		payServiceVo.setUid(payBusinessVo.getUserId());
		if(payBusinessVo.getPayMethod() == Const.PayMethod.WECHAT.getCode()){
			AccountZone accountZone = assembleAccountZone(payBusinessVo);
			payServiceVo.setAccountZone(JSONObject.toJSONString(accountZone));//做账字符串
		}
		payServiceVo.setOrderSubject(getBusinessNameByBusinessTpe(payBusinessVo.getPayBusinessType()));
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
    
    public JsonResult<ReturnPayBusinessVo> updateRemotePayConfirm(PayBusinessVo payBusinessVo) throws Exception{
    	JsonResult<ReturnPayBusinessVo> result = new JsonResult<ReturnPayBusinessVo>();
        result.setStatus(JsonResult.SUCCESS);
        
        if(!basicConfig.isPayByService()){
        	return result;
        }
        
        AccountZone accountZone = assembleAccountZone(payBusinessVo);
        PayServiceVo payServiceVo = new PayServiceVo(payCenterConfigBean.getApiKey(),payCenterConfigBean.getApiSecret(),payCenterConfigBean.getMerchantSign());
        String url = null;
        if (payBusinessVo.getPayMethod() == Const.PayMethod.YUE.getCode()) {//余额支付方式
            // 如果是app平台的余额支付，还要校验验证码是否正确，这个在之前已经做过了
            payServiceVo.setPayChannelType(payBusinessVo.getPayChannelType());
            payServiceVo.setOrderNo(payBusinessVo.getTradeId());
            payServiceVo.setAmount(payBusinessVo.getTotalAmount());
            payServiceVo.setUid(payBusinessVo.getUserId());
            payServiceVo.setOrderSubject(getBusinessNameByBusinessTpe(payBusinessVo.getPayBusinessType()));
            payServiceVo.setAccountZone(JSONObject.toJSONString(accountZone));//做账字符串
            /*if (payBusinessVo.getPayBusinessType() == Const.PayBusinessType.REVOKE_FAST_LOAN.getCode()) {
                payServiceVo.setPaymentType((byte) 5);//代付类型|必填(1:银行卡代付,2:余额支付, 4:退款, 5:撤销)
                payServiceVo.setSourceOrderNo(payBusinessVo.getRelationId().toString());
            } else {*/
            payServiceVo.setPaymentType((byte) 2);
//            }
            url = payCenterConfigBean.getPayBaseUrl() + payCenterConfigBean.getPayByBalance();
        } else if (payBusinessVo.getPayBusinessType() == Const.PayBusinessType.WITHDRAW.getCode()) {//提现业务确认
            payServiceVo.setPayChannelType(payBusinessVo.getPayChannelType());
            payServiceVo.setOrderNo(payBusinessVo.getTradeId());
            payServiceVo.setAmount(payBusinessVo.getTotalAmount());
            payServiceVo.setUid(payBusinessVo.getUserId());
            payServiceVo.setOpenId(payBusinessVo.getOpenId());
            /*payServiceVo.setUserTel(bindBank.getBankTel());
            payServiceVo.setUserFullName(bindBank.getUserName());
            payServiceVo.setUserIdCardNo(bindBank.getIdCardNo());
            payServiceVo.setBankCardNo(bindBank.getBankAccount());*/
            payServiceVo.setOrderSubject(getBusinessNameByBusinessTpe(payBusinessVo.getPayBusinessType()));
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
                if (payBusinessVo.getPayBusinessType() == Const.PayBusinessType.WITHDRAW.getCode()
                        || payBusinessVo.getPayMethod() == Const.PayMethod.YUE.getCode()) {
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
    }

    @SuppressWarnings({"rawtypes"})
    public JsonResult updateReceiveTrade(Long orderId, int orderStatus, String msg) throws Exception {
        JsonResult result = new JsonResult();
        result.setStatus(JsonResult.SUCCESS);

        boolean status = orderStatus == 1 ? true : false;//将支付中心的订单状态转换为今借到的处理结果
        //将支付中心的订单状态（1:成功, 2:失败, 3:过期, 4:关闭）转换为今借到的订单状态
        byte tradeStatus = Const.TradeStatus.CANCEL.getCode();
        if (orderStatus == 1) {
            tradeStatus = Const.TradeStatus.SUCCESS.getCode();
        } else if (orderStatus == 2) {
            tradeStatus = Const.TradeStatus.FAIL.getCode();
        }

        Trade trade = tradeMapper.selectByPrimaryKeyForUpdate(orderId);
        if (null == trade) {
            result.setStatus(JsonResult.FAIL);
            result.setMsg("订单不存在");
            return result;
        }
        if (trade.getTradeStatus() > Const.TradeStatus.COMFIRM.getCode()) {//已回过盘的
            return result;
        }
        PayBusinessVo payVo = null;
        if(trade.getTradeType() == Const.TradeType.MINI_WX_PAY.getCode()){
        	//如果是微信，由于不知道何时帮盘，只能在回盘的时补报盘
        	payVo = new PayBusinessVo();
        	payVo.setUserId(trade.getUid());
        	payVo.setTradeId(trade.getId());
        	payVo.setPayOrderNo(trade.getPayOrderNo());
        	payVo.setPayChannelType(trade.getTradeType());
        	payVo.setPayMethod(Const.PayMethod.WECHAT.getCode());
        }else{
        	//除了微信和银联，其它支付方式：报盘后状态是2
        	if (trade.getTradeStatus() != Const.TradeStatus.COMFIRM.getCode()) {
        		result.setStatus(JsonResult.FAIL);
        		result.setMsg("订单状态有误");
        		return result;
        	}
        }
        Const.PayBusinessType type = Const.PayBusinessType.getPayBusinessType(trade.getBusinessType());
        if (!trade.getWithdrawType()) {
            switch (type) {
                case RECHARGE: //充值
                	//过期或关闭的订单表示用户只是唤起了微信支付或银联支付却根本没用支付动作,所以无需写业务表
                    if (tradeStatus != Const.TradeStatus.CANCEL.getCode()) {
                        payVo.setPayBusinessType(Const.PayBusinessType.RECHARGE.getCode());
                        if (StringUtils.isNotBlank(trade.getReserveData())) {
                            JSONObject reserve = JSONObject.parseObject(trade.getReserveData());
                            if (reserve != null && reserve.getInteger("amount") != null) {
                                payVo.setAmount(reserve.getInteger("amount"));
                                calculateFee(payVo);
                                rechargeService.recharge(payVo);
                                rechargeService.updateReceiveBank(trade, tradeStatus,msg);
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
            //充值相关业务的相关用户余额变动在回盘时计算
            if (status) {
            	List<JSONObject> userMap = null;
                if (trade.getTradeType() != Const.TradeType.BALANCE_PAY.getCode()) {
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
                if (trade.getTradeType() == Const.TradeType.BALANCE_PAY.getCode()) {
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
        	switch (type) {
			case WITHDRAW://提现
				withDrawService.updateReceiveBank(trade, tradeStatus,msg);
				break;
			default:
				break;
			}
        	if(status){//交易成功
        		if (trade.getTradeType() == Const.TradeType.BALANCE_PAY.getCode()) {//退款到余额
        			//退款到余额业务,在回盘成功时退还用户余额,做账时再退可提现余额
        			List<JSONObject> userMap = userAccountListMapper.getAccountListAmtNotSysAndBankByTradeId(trade.getId());
        			String userAccountListStatis = JSON.toJSONString(userMap);
        			userFeignService.updateUserAccountAccumulateAboutOrder(userAccountListStatis, (byte) 1,false);
        		}
        	}else{//交易失败
        		if (trade.getTradeType() != Const.TradeType.BALANCE_PAY.getCode()) {//提现
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
        tradevo.setTradeStatus(tradeStatus);
        tradevo.setReceiveTime(now);
        tradeMapper.updateByPrimaryKeySelective(tradevo);

        UserAccountList userAccountList = new UserAccountList();
        userAccountList.setTradeId(trade.getId());
        userAccountList.setReceiveBankStatus(true);
        userAccountList.setValidStatus(status);
        userAccountList.setReceiveTime(now);
        userAccountListMapper.updateByPrimaryKeySelective(userAccountList);
        //如果微信或银联支付成功后删掉做账临时表数据
        if (trade.getTradeType() != Const.TradeType.BALANCE_PAY.getCode()) {
            userAccountListMapper.deleteTempUserAccountList(orderId);
        }
        return result;
    }

    @Override
    public List<Trade> getOrderToDealwith() throws Exception {
        return tradeMapper.getOrderToDealwith();
    }
}
