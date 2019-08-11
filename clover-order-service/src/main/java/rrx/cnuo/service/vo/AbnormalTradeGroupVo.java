package rrx.cnuo.service.vo;

public class AbnormalTradeGroupVo {

	/**
	 * 支付通道类型
	 */
	private Byte tradeType;
	
	/**
	 * 订单对账异常类型：1-该订单我们有第三方没有；2-该订单我们没有第三方有；3，状态不一致；4-金额不一致；5-状态和金额都不一致
	 */
	private Byte abnormalType;
	
	/**
	 * 对账日内，每种支付通道类型下每种异常类型的个数
	 */
	private Long abTypeCount;

	public Byte getTradeType() {
		return tradeType;
	}

	public void setTradeType(Byte tradeType) {
		this.tradeType = tradeType;
	}

	public Byte getAbnormalType() {
		return abnormalType;
	}

	public void setAbnormalType(Byte abnormalType) {
		this.abnormalType = abnormalType;
	}

	public Long getAbTypeCount() {
		return abTypeCount;
	}

	public void setAbTypeCount(Long abTypeCount) {
		this.abTypeCount = abTypeCount;
	}
	
}
