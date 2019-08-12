package rrx.cnuo.cncommon.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ReturnPayBusinessVo {

	/**
	 * 商户订单号（交易id）
	 */
	private Long tradeId;
	
	/**
	 * 支付订单号或协议支付绑卡流水号(第三方支付公司返回)
	 */
	private String payOrderNo;
	
	/**
	 * 支付令牌
	 * 1.当为银行卡支付时, 传回payToken字符串;
	 * 2.当为银联支付时, 传回跳转html代码;
	 * 3.当为微信支付时, 传回唤醒公众号支付组件的六要素;
	 */
	private String payToken;
	
	/**
	 * 银行卡支付通道：0-掌上汇通P2P通道；1-掌上汇通快捷通道；2-余额支付通道；4-易联插件通道；5-易联代收代付通道；7-合利宝支付通道；8-易宝支付通道；17-富友-协议支付(代收)；18-银联WAP支付(代收)；19-联拓
	 */
//	private Byte payChannelType;
	
	
	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}

	/**
	 * 支付令牌
	 * 1.当为银行卡支付时, 传回payToken字符串;
	 * 2.当为银联支付时, 传回跳转html代码;
	 * 3.当为微信支付时, 传回唤醒公众号支付组件的六要素;
	 */
	public String getPayToken() {
		return payToken;
	}

	/**
	 * 支付令牌
	 * 1.当为银行卡支付时, 传回payToken字符串;
	 * 2.当为银联支付时, 传回跳转html代码;
	 * 3.当为微信支付时, 传回唤醒公众号支付组件的六要素;
	 */
	public void setPayToken(String payToken) {
		this.payToken = payToken;
	}

	@JsonIgnore
	public Long getTradeId() {
		return tradeId;
	}

	public void setTradeId(Long tradeId) throws Exception {
		this.tradeId = tradeId;
	}

}
