package rrx.cnuo.service.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.PayBusinessVo;
import rrx.cnuo.cncommon.vo.ReturnPayBusinessVo;
import rrx.cnuo.service.po.Trade;
import rrx.cnuo.service.service.RechargeService;
import rrx.cnuo.service.vo.RechargeVo;

@Service
public class RechargeServiceImpl extends PayBase implements RechargeService {

	/*@Autowired
	private IPayService payService;*/
	
	private JsonResult<ReturnPayBusinessVo> checkBeforeRecharge(RechargeVo payVo) throws Exception{
		JsonResult<ReturnPayBusinessVo> result = new JsonResult<ReturnPayBusinessVo>();
        result.setStatus(JsonResult.SUCCESS);
        
        JSONObject userBasic = userFeignService.getUserPassportByUid(payVo.getUserId());
        String openId = userBasic.getString("openId");
        if(StringUtils.isBlank(openId)){
        	result.setStatus(JsonResult.FAIL);
        	result.setMsg("openId为空，请退出重新登录");
        	return result;
        }
        payVo.setOpenId(openId);
        
        if (payVo.getAmount() < Const.PAY_INFO.MIN_BOUNTY_AMT || payVo.getAmount() > Const.PAY_INFO.MAX_RECHARGE_AMT) {
            result.setStatus(JsonResult.FAIL);
            result.setMsg("充值金额必须在" + Const.PAY_INFO.MIN_BOUNTY_AMT/100 + "-" + Const.PAY_INFO.MAX_RECHARGE_AMT/100 + "元");
            return result;
        }
        calculateFee(payVo);
        
        return result;
	}
	
	@Override
	public JsonResult<ReturnPayBusinessVo> updateRechargeApply(RechargeVo payVo) throws Exception {
		JsonResult<ReturnPayBusinessVo> result = new JsonResult<ReturnPayBusinessVo>();
        result.setStatus(JsonResult.SUCCESS);

        result = checkBeforeRecharge(payVo);
        if(!result.isOk()){
        	return result;
        }

        JSONObject json = new JSONObject();
        json.put("amount", payVo.getAmount());
        if(payVo.getProdId() != null){
        	json.put("prodId", payVo.getProdId());
        }
        payVo.setReserveData(json.toJSONString());
        
        JSONObject userAccount = userFeignService.getUserAccountByUid(payVo.getUserId());
        return localPayApply(payVo,userAccount);
	}

	@Override
    public void recharge(PayBusinessVo payVo) throws Exception {
        makeAccountList(payVo);
    }

	@Override
	public void updateReceiveBank(Trade trade, byte tradeStatus, String msg) throws Exception {
		/*boolean result = tradeStatus == Const.TradeStatus.SUCCESS.getCode() ? true : false;
		if(result){
			JSONObject reserve = JSONObject.parseObject(trade.getReserveData());
			//1,充值时判断是否为出借人发布产品时余额不足而发起的充值，如果是自动完成发布
			Long prodId = reserve.getLong("prodId");
			
			//2,充值时判断该用户是否欠费，如果有欠费则优先补欠费
		}*/
	}

}
