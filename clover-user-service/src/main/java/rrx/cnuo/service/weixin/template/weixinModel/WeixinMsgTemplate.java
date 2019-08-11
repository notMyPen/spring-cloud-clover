package rrx.cnuo.service.weixin.template.weixinModel;

import com.alibaba.fastjson.JSON;

import rrx.cnuo.service.weixin.template.base.ValueColor;
import rrx.cnuo.service.weixin.template.base.WeiXinBaseTemplate;
import rrx.cnuo.service.weixin.template.base.WeiXinTemplate;

/**
 * 微信消息发送
 * Title: WeixinMsgTemplate.java
 * Description
 * @author snowy
 * @date 2016年8月3日
 * @version 1.0
 */
public class WeixinMsgTemplate extends WeiXinBaseTemplate{


	private ValueColor keyword1;
	private ValueColor keyword2;
	private ValueColor keyword3;
	private ValueColor keyword4;
	private ValueColor keyword5;

	private ValueColor OrderSn;
	private ValueColor OrderStatus;

	public ValueColor getOrderSn() {
		return OrderSn;
	}

	public void setOrderSn(ValueColor OrderSn) {
		this.OrderSn = OrderSn;
	}

	public ValueColor getOrderStatus() {
		return OrderStatus;
	}

	public void setOrderStatus(ValueColor OrderStatus) {
		this.OrderStatus = OrderStatus;
	}

	public ValueColor getKeyword1() {
		return keyword1;
	}
	public void setKeyword1(ValueColor keyword1) {
		this.keyword1 = keyword1;
	}
	public ValueColor getKeyword2() {
		return keyword2;
	}
	public void setKeyword2(ValueColor keyword2) {
		this.keyword2 = keyword2;
	}
	public ValueColor getKeyword3() {
		return keyword3;
	}
	public void setKeyword3(ValueColor keyword3) {
		this.keyword3 = keyword3;
	}	
	public ValueColor getKeyword4() {
		return keyword4;
	}
	public void setKeyword4(ValueColor keyword4) {
		this.keyword4 = keyword4;
	}
	public ValueColor getKeyword5() {
		return keyword5;
	}
	public void setKeyword5(ValueColor keyword5) {
		this.keyword5 = keyword5;
	}
	
	public String toSendMessage(WeiXinTemplate w,String type){
		w.setData(this);
	//	w.setTemplate_id(Config.WECHAT_MSG_LEND_SUCCESS);
		w.setTemplate_id(type);
		return JSON.toJSONString(w);
	}
	
}
