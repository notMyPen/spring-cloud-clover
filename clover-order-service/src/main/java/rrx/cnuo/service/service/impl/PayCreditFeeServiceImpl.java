package rrx.cnuo.service.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.accessory.consts.Const.WeChatMsgEnum;
import rrx.cnuo.cncommon.utils.MqSendTool;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.feign.UserPassportVo;
import rrx.cnuo.service.accessory.consts.TradeConst;
import rrx.cnuo.service.dao.TradeCreditFeeMapper;
import rrx.cnuo.service.feignclient.UserFeignService;
import rrx.cnuo.service.po.Trade;
import rrx.cnuo.service.po.TradeCreditFee;
import rrx.cnuo.service.service.PayService;
import rrx.cnuo.service.vo.MakeAccountListVo;
import rrx.cnuo.service.vo.request.PayBusinessVo;

@Service("payCreditFee")
@SuppressWarnings("rawtypes")
public class PayCreditFeeServiceImpl extends PayBase implements PayService {

	@Autowired private TradeCreditFeeMapper tradeCreditFeeMapper;
	@Autowired private MqSendTool mqSendTool;
	@Autowired private UserFeignService userFeignService;
	
	@Override
	public void setCashFlowType(PayBusinessVo payVo) {
		payVo.setCashFlowType(TradeConst.CashFlowType.COLLECTION.getCode());
	}

	@Override
	public void setFeeAndTotalAmount(PayBusinessVo payVo) {
		int rechargeFee = new BigDecimal(payVo.getAmount()).multiply(new BigDecimal(TradeConst.PAY_INFO.RECHARGE_FEE_RATE)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
		payVo.setRechargeWithdrawFee(rechargeFee);
		payVo.setTotalAmount(payVo.getAmount() + payVo.getRechargeWithdrawFee());// 充值金额
	}

	@Override
	public void setReserveData(PayBusinessVo payVo) {
		JSONObject json = new JSONObject();
        json.put("amount", payVo.getAmount());
        json.put("payMethod", payVo.getPayMethod());
        payVo.setReserveData(json.toJSONString());
	}

	@Override
	public JsonResult checkBusinessBeforePay(PayBusinessVo payVo) throws Exception {
		UserPassportVo userPassportVo = userFeignService.getUserPassportByUid(payVo.getUserId());
		if(StringUtils.isBlank(userPassportVo.getRegistTel())) {
			return JsonResult.fail(JsonResult.FAIL, "您尚未注册手机号，请先认证手机号");
		}
		
		TradeCreditFee record  = new TradeCreditFee();
		record.setUid(payVo.getUserId());
		record.setValidStatus(true);
		record.setReceiveStatus(false);
		List<TradeCreditFee> list = tradeCreditFeeMapper.selectByParam(record);
		if(list != null && list.size() > 0) {
			return JsonResult.fail(JsonResult.FAIL, "您已支付过信用认证费，如支付完成后还不能进行认证，请联系客服");
		}
		return JsonResult.ok();
	}

	@Override
	public void updateBusinessLocal(PayBusinessVo payVo) throws Exception {
		TradeCreditFee record  = new TradeCreditFee();
		record.setTradeId(payVo.getTradeId());
		record.setAmount(payVo.getAmount());
		record.setUid(payVo.getUserId());
		record.setCreditType(TradeConst.CreditType.BASIC_CREDIT.getCode());
		tradeCreditFeeMapper.insertSelective(record);
	}

	@Override
	public void makeAccountList(PayBusinessVo payVo) {
		//充值
		rechargeMakeAccountList(payVo);
		if(payVo.getRechargeWithdrawFee() > 0){
			//充值手续费  
			rechargeFeeMakeAccountList(payVo);
		}
		//缴认证费
		MakeAccountListVo userAccountListVo = new MakeAccountListVo();
		userAccountListVo.setUserId(payVo.getUserId());//支出方
		userAccountListVo.setTargetUserId(TradeConst.SYSTEM_USER);//收款方
		userAccountListVo.setPayAcountType(TradeConst.AccountType.PAY_CREDIT_FEE.getCode());//支出类型
		userAccountListVo.setRecvAcountType(TradeConst.AccountType.RCV_CREDIT_FEE.getCode());//收款类型
		userAccountListVo.setAccountAmount(payVo.getAmount());//缴认证费金额
		makeUserPayTargetUserAccount(payVo, userAccountListVo);
	}
	
	@Override
	public void setUserParamsFromReserveData(PayBusinessVo payVo, JSONObject reserve) {
		payVo.setAmount(reserve.getInteger("amount"));
		payVo.setPayMethod(reserve.getByte("payMethod"));
	}

	@Override
	public void updateReceiveTrade(Trade trade,boolean result, String msg) throws Exception {
		TradeCreditFee record  = new TradeCreditFee();
		record.setTradeId(trade.getId());
		record.setReceiveStatus(true);
		record.setValidStatus(result);
		tradeCreditFeeMapper.updateByPrimaryKeySelective(record);
		if(result) {
			//发消息
			JSONObject json = new JSONObject();
			json.put(Const.MQ.MQ_HANDLER_TYPE_KEY, Const.MqHandleType.SEND_WX_MSG.getCode());
			json.put("uid", trade.getUid());
			json.put("msgType", WeChatMsgEnum.CREDIT_FEE_PAIED.getCode());
//			json.put("msgVariableVal", );
			mqSendTool.normalMqSender(UUID.randomUUID().toString(), json);
			
			//注册信用中心（在order模块调用信用中心，以后如果信用中心连到rabbitmq可以改成直连信用中心交换机）
			UserPassportVo userPassportVo = userFeignService.getUserPassportByUid(trade.getUid());
			json = new JSONObject();
			json.put(Const.MQ.MQ_HANDLER_TYPE_KEY, Const.MqHandleType.REGIST_CREDIT_CENTER.getCode());
			json.put("uid", trade.getUid());
			json.put("telephone", userPassportVo.getRegistTel());
			mqSendTool.normalMqSender(UUID.randomUUID().toString(), json);
		}
	}

	@Override
	public void checkEnd(Long tradeId) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
