package rrx.cnuo.service.service.impl;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.accessory.consts.Const.WeChatMsgEnum;
import rrx.cnuo.cncommon.utils.MqSendTool;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.accessory.consts.TradeConst;
import rrx.cnuo.service.dao.TradeBuycardMapper;
import rrx.cnuo.service.feignclient.BizFeignService;
import rrx.cnuo.service.feignclient.UserFeignService;
import rrx.cnuo.service.po.Trade;
import rrx.cnuo.service.po.TradeBuycard;
import rrx.cnuo.service.service.PayService;
import rrx.cnuo.service.vo.MakeAccountListVo;
import rrx.cnuo.service.vo.request.PayBusinessVo;

@Service("payBuyCard")
@SuppressWarnings("rawtypes")
public class PayBuyCardServiceImpl extends PayBase implements PayService {

	@Autowired private TradeBuycardMapper tradeBuycardMapper;
	@Autowired private BizFeignService bizFeignService;
	@Autowired private UserFeignService userFeignService;
	@Autowired private MqSendTool mqSendTool;
	
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
        json.put("buyCardNum", payVo.getBuyCardNum());
        payVo.setReserveData(json.toJSONString());
	}

	@Override
	public JsonResult checkBusinessBeforePay(PayBusinessVo payVo) throws Exception {
		if(payVo.getBuyCardNum() == null) {
			return JsonResult.fail(JsonResult.FAIL, "礼券购买个数不能为空！");
		}
		
		if(TradeConst.BuyCardType.isMatch(payVo.getBuyCardNum(), payVo.getAmount())) {
			return JsonResult.fail(JsonResult.FAIL, "礼券购买个数和金额不匹配！");
		}
		return JsonResult.ok();
	}

	@Override
	public void updateBusinessLocal(PayBusinessVo payVo) throws Exception {
		TradeBuycard record = new TradeBuycard();
		record.setTradeId(payVo.getTradeId());
		record.setUid(payVo.getUserId());
		record.setAmount(payVo.getAmount());
		record.setBuyNum(payVo.getBuyCardNum());
		tradeBuycardMapper.insertSelective(record);
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
		userAccountListVo.setPayAcountType(TradeConst.AccountType.PAY_BUG_CARD.getCode());//支出类型
		userAccountListVo.setRecvAcountType(TradeConst.AccountType.RCV_BUG_CARD.getCode());//收款类型
		userAccountListVo.setAccountAmount(payVo.getAmount());//购买礼券的金额
		makeUserPayTargetUserAccount(payVo, userAccountListVo);
	}

	@Override
	public void setUserParamsFromReserveData(PayBusinessVo payVo, JSONObject reserve) {
		payVo.setAmount(reserve.getInteger("amount"));
		payVo.setPayMethod(reserve.getByte("payMethod"));
		payVo.setBuyCardNum(reserve.getInteger("buyCardNum"));
	}
	
	@Override
	public void updateReceiveTrade(Trade trade,boolean result, String msg) throws Exception {
		TradeBuycard record = new TradeBuycard();
		record.setTradeId(trade.getId());
		record.setReceiveStatus(true);
		record.setValidStatus(result);
		tradeBuycardMapper.updateByPrimaryKeySelective(record);
		if(result) {
			//赠送礼券
			bizFeignService.addCardAward(Const.AwardType.BUY.getCode());
			
			//统计用户剩余礼券数
			userFeignService.updateUserCardNum(trade.getUid());
			
			//发消息
			JSONObject json = new JSONObject();
			json.put(Const.MQ.MQ_HANDLER_TYPE_KEY, Const.MqHandleType.SEND_WX_MSG.getCode());
			json.put("uid", trade.getUid());
			json.put("msgType", WeChatMsgEnum.BUYCARD_PAIED.getCode());
			json.put("msgVariableVal", Const.AwardType.BUY.getAwardNum());
			mqSendTool.normalMqSender(UUID.randomUUID().toString(), json);
		}
	}

	@Override
	public void checkEnd(Long tradeId) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
