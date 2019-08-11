package rrx.cnuo.service.vo.paycenter;

import com.alibaba.fastjson.JSONObject;

/**
 * 支付中心支付相关返回字段
 * @author xuhongyu
 * @version 创建时间：2018年7月9日 下午4:45:33
 */
public class ReturnPayVo extends ReturnPayBase{

	/**
	 * 商户订单号
	 */
	private Long orderNo;
	
	/**
	 * 支付订单号或协议支付绑卡流水号(第三方支付公司返回)
	 */
	private String payOrderNo;
	
	/**
	 * 支付令牌(第三方支付公司返回)
	 */
	private Object payToken;
	
	/**
	 * 支付令牌字符串(第三方支付公司返回所转换)
	 */
	private String payTokenStr;
	
	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}
	
	public Object getPayToken() {
		return payToken;
	}

	public void setPayToken(Object payToken) {
		if(payToken != null){
			this.payToken = payToken;
			if(payToken instanceof String){
				this.payTokenStr = (String)payToken;
			}else{
				this.payTokenStr = JSONObject.toJSONString(payToken);
			}
		}
	}

	public String getPayTokenStr() {
		return payTokenStr;
	}

	public void setPayTokenStr(String payTokenStr) {
		this.payTokenStr = payTokenStr;
	}

}
