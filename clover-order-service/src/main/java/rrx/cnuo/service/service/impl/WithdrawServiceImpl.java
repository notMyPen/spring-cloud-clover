package rrx.cnuo.service.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.util.DateUtil;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.PayBusinessVo;
import rrx.cnuo.cncommon.vo.ReturnPayBusinessVo;
import rrx.cnuo.service.dao.SystemCapitalListMapper;
import rrx.cnuo.service.po.SystemCapitalList;
import rrx.cnuo.service.po.Trade;
import rrx.cnuo.service.service.WithdrawService;

@Service
public class WithdrawServiceImpl extends PayBase implements WithdrawService {

	@Autowired
	private SystemCapitalListMapper systemCapitalListMapper;
	
	/**
     * 提现前的通用校验
     *
     * @param userId
     * @param vo
     * @return
     * @throws Exception
     */
    private JsonResult<ReturnPayBusinessVo> checkWithdrawBeforeTrade(PayBusinessVo vo) throws Exception {
        JsonResult<ReturnPayBusinessVo> result = new JsonResult<ReturnPayBusinessVo>();
        result.setStatus(JsonResult.SUCCESS);

        JSONObject userPassport = userFeignService.getUserPassportByUid(vo.getUserId());
        String openId = userPassport == null ? null : userPassport.getString("openId");
        if(StringUtils.isBlank(openId)){
        	result.setStatus(JsonResult.FAIL);
        	result.setMsg("openId为空，请退出重新登录");
        	return result;
        }
        vo.setOpenId(openId);
        
        //校验提现金额是否在正常范围
        if (vo.getAmount() <= Const.PAY_INFO.N_WITHDRAW_FEE) {
            result.setStatus(JsonResult.FAIL);
            result.setMsg("用户提现金额要大于" + Const.PAY_INFO.N_WITHDRAW_FEE / 100 + "元");
            return result;
        }

        JSONObject userAccount = userFeignService.getUserAccountByUid(vo.getUserId());
        int balance = userAccount.getInteger("balance");
        if (vo.getAmount().intValue() > balance) {
            result.setStatus(JsonResult.FAIL);
            result.setMsg("用户提现金额大于账户余额");
            return result;
        }
        int withdrawBalance = userAccount.getInteger("withdrawBalance");
        int capital = vo.getAmount();//垫资金额
        if (withdrawBalance > 0) {
            capital = vo.getAmount() - withdrawBalance;
        }
        if (capital > 0 && capital <= Const.PAY_INFO.N_WITHDRAW_FEE) {
            result.setStatus(JsonResult.FAIL);
            result.setMsg("系统垫资额必须大于提现费用，请重新选择金额提现");
            return result;
        }
        vo.setBusinessFee(capital);//垫资额
        calculateFee(vo);
        
        return result;

    }
	
	@Override
	public JsonResult<ReturnPayBusinessVo> updateWithdraw(PayBusinessVo payVo) throws Exception{
		JsonResult<ReturnPayBusinessVo> result = new JsonResult<ReturnPayBusinessVo>();
        result.setStatus(JsonResult.SUCCESS);

        result = checkWithdrawBeforeTrade(payVo);
        if(!result.isOk()){
			return result;
		}
        JSONObject userAccount = userFeignService.getUserAccountByUid(payVo.getUserId());
        result = checkYueBeforeTrade(payVo,userAccount);
		if(!result.isOk()){
			return result;
		}

		result = localPayApply(payVo,userAccount);
		if(!result.isOk()){
			return result;
		}
        withdraw(payVo, payVo.getBusinessFee() - payVo.getRechargeWithdrawFee());
        
        localPayConfirm(payVo);
        return result;
	}
	
	private void withdraw(PayBusinessVo withdrawVo, Integer capital) throws Exception {
        if (capital > 0) {
            SystemCapitalList systemCapitalList = new SystemCapitalList();
            systemCapitalList.setTradeId(withdrawVo.getTradeId());
            systemCapitalList.setUid(withdrawVo.getUserId());
            systemCapitalList.setTradeType((int) withdrawVo.getPayChannelType());
            systemCapitalList.setAmount(capital);
            systemCapitalListMapper.insertSelective(systemCapitalList);
        }
        //做账
        makeAccountList(withdrawVo);
        
        // 获取因为此次交易每个人的账户变动情况
//        List<JSONObject> userMap = userAccountListMapper.getAccountListAmtNotSysAndBankByTradeId(withdrawVo.getTradeId());
        // 更新每个人的账户余额
        /*UserBasic account = new UserBasic();
        for (JSONObject userAccount : userMap) {
            account.setUid(userAccount.getLong("uid"));
            account.setBalance(userAccount.getInteger("amount"));
            account.setWithdrawBalance(userAccount.getInteger("amount"));
            userDataService.updateUserAccountAccumulate(account);
        }*/
    }
	
	@Override
	public void updateReceiveBank(Trade trade, byte tradeStatus, String msg) throws Exception {
		boolean result = tradeStatus == Const.TradeStatus.SUCCESS.getCode() ? true : false;
    	SystemCapitalList capitalListParam = new SystemCapitalList();
        capitalListParam.setTradeId(trade.getId());
        capitalListParam.setReceiveBankStatus(true);
        capitalListParam.setReceiveTime(DateUtil.getSecond(new Date()));
        capitalListParam.setValidStatus(result);
        systemCapitalListMapper.updateByPrimaryKeySelective(capitalListParam);

        /*if(tradeStatus != Const.TradeStatus.CANCEL.getCode()){
        	//推送微信消息
        	UserPassport userPassport = userPassportService.getUserPassport(userId);
        	WeiXinMsgVo weiXinMsgVo = new WeiXinMsgVo();
        	weiXinMsgVo.setTouser(userPassport.getOpenId());
        	weiXinMsgVo.setUrl(String.valueOf(MsgConst.WeChatMsgUrl.WECHAT_MSG_URL_16.getCode()));
        	//weiXinMsgVo.setUrl("/wallet");
        	WeiXinMsgVoData weiXinMsgVoData = new WeiXinMsgVoData();
        	if(result){
        		weiXinMsgVoData.setFirst(new ValueColor(String.valueOf(MsgConst.WeChatMsgContent.WECHAT_MSG_CONTENT_51.getCode())));
        	}else{
        		weiXinMsgVoData.setFirst(new ValueColor(String.valueOf(MsgConst.WeChatMsgContent.WECHAT_MSG_CONTENT_52.getCode())));
        	}
        	//weiXinMsgVoData.setFirst(new ValueColor("您的提现" + (result ? "成功" : "失败")));
        	weiXinMsgVoData.setKeyword1(new ValueColor("提现"));
        	weiXinMsgVoData.setKeyword2(new ValueColor(getTradeTypeToString(tradeType)));
        	weiXinMsgVoData.setKeyword3(new ValueColor(ToolUtil.FEN2YUAN(amt)));
        	weiXinMsgVoData.setKeyword4(new ValueColor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sendTm)));
        	weiXinMsgVoData.setRemark(new ValueColor(""));
        	weiXinMsgVo.setData(weiXinMsgVoData);
        	weiXinMsgVo.setUserId(userId);
        	weiXinMsgVo.setType(MsgConst.WeChatMsgTypeEnum.TRADE_NOTIFY.getCode());
        	weiXinService.sendWeChatMsg(weiXinMsgVo);
        }*/
	}

	@Override
    public void checkEnd(Long tradeId) throws Exception {
    	SystemCapitalList systemCapitalList = new SystemCapitalList();
    	systemCapitalList.setTradeId(tradeId);
    	systemCapitalList.setReconciliationStatus(true);
    	systemCapitalList.setReconciliationTime(DateUtil.getSecond(new Date()));
    	systemCapitalListMapper.updateByPrimaryKeySelective(systemCapitalList);
    }
}
