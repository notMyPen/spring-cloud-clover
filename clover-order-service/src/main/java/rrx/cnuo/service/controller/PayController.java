package rrx.cnuo.service.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.accessory.context.UserContextHolder;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.PayBusinessVo;
import rrx.cnuo.cncommon.vo.ReturnPayBusinessVo;
import rrx.cnuo.service.accessory.config.PayCenterConfigBean;
import rrx.cnuo.service.service.PayService;
import rrx.cnuo.service.service.RechargeService;
import rrx.cnuo.service.service.WithdrawService;
import rrx.cnuo.service.vo.RechargeVo;
import rrx.cnuo.service.vo.paycenter.Notify;
import rrx.cnuo.service.vo.paycenter.OrderNotify;

@Slf4j
@RestController
@RequestMapping("/pay")
public class PayController {
	
	@Autowired private RechargeService rechargeService;
	@Autowired private WithdrawService withdrawService;
	@Autowired private PayService payService;
	@Autowired private PayCenterConfigBean payCenterConfigBean;

	/**
	 * 充值
	 * @author xuhongyu
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	public JsonResult<ReturnPayBusinessVo> recharge(@RequestBody RechargeVo payVo) throws Exception {
		JsonResult<ReturnPayBusinessVo> result = new JsonResult<ReturnPayBusinessVo>();
		
		payVo.setUserId(UserContextHolder.currentUser().getUserId());
		payVo.setPayBusinessType(Const.PayBusinessType.RECHARGE.getCode());
		payVo.setCashFlowType(Const.CashFlowType.COLLECTION.getCode());
		payVo.setPayMethod(Const.PayMethod.WECHAT.getCode());
		payVo.setPayChannelType(Const.TradeType.MINI_WX_PAY.getCode());
		
		result = rechargeService.updateRechargeApply(payVo);
		if(result.isOk()){
			result = payService.updateRemotePayApply(payVo);
			if(!result.isOk()){
				payService.updateReceiveTrade(payVo.getTradeId(), Const.TradeStatus.CANCEL.getCode(), result.getMsg());
			}
		}
		return result;
	}
	
	/**
	 * 提现
	 * @author xuhongyu
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/withdraw", method = RequestMethod.POST)
	public JsonResult<ReturnPayBusinessVo> withdraw(@RequestBody PayBusinessVo payVo) throws Exception {
		JsonResult<ReturnPayBusinessVo> result = new JsonResult<ReturnPayBusinessVo>();
		
		payVo.setUserId(UserContextHolder.currentUser().getUserId());
		payVo.setPayBusinessType(Const.PayBusinessType.WITHDRAW.getCode());
		payVo.setCashFlowType(Const.CashFlowType.PAYMENT.getCode());
		payVo.setPayMethod(Const.PayMethod.WECHAT.getCode());
		payVo.setPayChannelType(Const.TradeType.WX_WITHDRAW.getCode());
		
		result = withdrawService.updateWithdraw(payVo);
		if(result.isOk()){
			result = payService.updateRemotePayConfirm(payVo);
			if(!result.isOk()){
				payService.updateReceiveTrade(payVo.getTradeId(), Const.TradeStatus.CANCEL.getCode(), result.getMsg());
			}
		}
		return result;
	}
	
	/**
	 * 支付主动通知接口
	 * @param orderId // 订单号
	 * @param status // 支付状态 0.失败 1.成功
	 * @param msg // 返回信息
	 * @return{
	   		"status":"200",
	   }
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/notify",method=RequestMethod.POST)
	public String notify(HttpServletRequest request, @RequestBody OrderNotify orderNotify) throws Exception {
		/*if (ConfigBean.isProdEnvironment() && !ToolUtil.checkIPValid(request)) {
			log.error("=========非法IP访问支付回调接口==========");
			return "非法IP访问支付回调接口";
		}*/
		JsonResult result = new JsonResult();
		result.setStatus(JsonResult.SUCCESS);
		
		if(orderNotify != null && StringUtils.isNotBlank(orderNotify.getNotifySecret())){
			if(orderNotify.getNotifySecret().equals(payCenterConfigBean.getNotifySecret())){
				Notify notify = orderNotify.getData();
				try {
					//orderStatus|Integer|订单状态|0:处理中, 1:成功, 2:失败, 3:过期, 4:关闭, 5:待商户确认 9:其他
					int orderStatus = notify.getOrderStatus();
					log.info("===============订单："+notify.getOrderNo()+"，通知回盘状态：" + orderStatus + "，msg" + notify.getMsg()+ "==================");
					if(orderStatus != 0 && orderStatus != 5 && orderStatus != 9){
						result = payService.updateReceiveTrade(notify.getOrderNo(), orderStatus, notify.getMsg());
	                }
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
			}else{
				return "非法NotifySecret";
			}
		}else{
			return "非法通知";
		}
		if(result.getStatus() == JsonResult.SUCCESS){
			return "success";
		}else{
			return "fail";
		}
	}
}
