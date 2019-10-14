package rrx.cnuo.service.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.accessory.config.PayCenterConfigBean;
import rrx.cnuo.service.accessory.consts.TradeConst;
import rrx.cnuo.service.service.PayProcessService;
import rrx.cnuo.service.vo.paycenter.Notify;
import rrx.cnuo.service.vo.paycenter.OrderNotify;
import rrx.cnuo.service.vo.request.PayBusinessVo;
import rrx.cnuo.service.vo.response.OrderStatusVo;
import rrx.cnuo.service.vo.response.ReturnPayBusinessVo;

@Slf4j
@RestController
@RequestMapping("/pay")
@Api("支付控制器")
public class PayController {
	
//	@Autowired private RechargeService rechargeService;
//	@Autowired private WithdrawService withdrawService;
//	@Autowired private PayService payService;
	@Autowired private PayProcessService payProcessService;
	@Autowired private PayCenterConfigBean payCenterConfigBean;

	@ApiOperation("支付各种费用")
	@RequestMapping(value = "/apply", method = RequestMethod.POST)
	public JsonResult<ReturnPayBusinessVo> recharge(@RequestBody @ApiParam( value = "支付时前端的传参",required = true) PayBusinessVo payVo) throws Exception {
		JsonResult<ReturnPayBusinessVo> result = payProcessService.updateLocalPayApply(payVo);
		if(result.isOk()){
			result = payProcessService.updateRemotePayApply(payVo);
			if(!result.isOk()){
				payProcessService.updateReceiveBank(payVo.getTradeId(), TradeConst.TradeStatus.CANCEL.getCode(), result.getMsg());
			}
		}
		return result;
	}
	
	@ApiOperation("获取订单支付状态")
	@GetMapping("/status")
	public JsonResult<OrderStatusVo> getOrderStatus(@RequestParam @ApiParam(value = "订单号",required = true) String orderNo,
				@RequestParam(required = false) @ApiParam(value = "被翻牌子用户id（翻牌子支付时才传）",required = false) String fuid) throws Exception {
		return payProcessService.getOrderStatus(Long.parseLong(orderNo),StringUtils.isNotBlank(fuid) ? Long.parseLong(fuid) : null);
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
						result = payProcessService.updateReceiveBank(notify.getOrderNo(), orderStatus, notify.getMsg());
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
