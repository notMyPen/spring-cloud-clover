package rrx.cnuo.service.vo.paycenter;

/**
 * 支付中心支付通道调度返回字段
 * @author xuhongyu
 * @version 创建时间：2018年7月9日 下午4:46:19
 */
public class ReturnScheduleVo extends ReturnPayBase{

	/**
	 * 银行卡支付通道：0-掌上汇通P2P通道；1-掌上汇通快捷通道；2-余额支付通道；4-易联插件通道；5-易联代收代付通道；7-合利宝支付通道；8-易宝支付通道；17-富友-协议支付(代收)；18-银联WAP支付(代收)；19-联拓
	 */
	private Byte payChannelType;
	
	/**
	 * 协议支付是否绑卡（如果payChannelType的值是某种协议支付，需要判断该种协议支付是否绑卡）
	 */
	private Boolean protocolBind;
	
	/**
	 * 如果是提现操作(代付)需要返回当前系统（今借到）在该支付通道的已对账金额
	 */
	private Integer channelWithdrawAmount;
	
	/**
	 * 银行卡在该通道的单笔限额
	 */
	private Integer singleLimitAmount;
	
	/**
	 * 银行卡在该通道的日限额剩余量
	 */
	private Integer dailyLimitAmount;
	
	public Byte getPayChannelType() {
		return payChannelType;
	}

	public void setPayChannelType(Byte payChannelType) {
		this.payChannelType = payChannelType;
	}

	/**
	 * 协议支付是否绑卡（如果payChannelType的值是某种协议支付，需要判断该种协议支付是否绑卡）
	 */
	public Boolean getProtocolBind() {
		return protocolBind;
	}

	public void setProtocolBind(Boolean protocolBind) {
		this.protocolBind = protocolBind;
	}

	/**
	 * 如果是提现操作(代付)需要返回当前系统（今借到）在该支付通道的已对账金额
	 * @author xuhongyu
	 * @return
	 */
	public Integer getChannelWithdrawAmount() {
		return channelWithdrawAmount;
	}

	/**
	 * 如果是提现操作(代付)需要返回当前系统（今借到）在该支付通道的已对账金额
	 * @author xuhongyu
	 * @param channelWithdrawAmount
	 */
	public void setChannelWithdrawAmount(Integer channelWithdrawAmount) {
		this.channelWithdrawAmount = channelWithdrawAmount;
	}

	public Integer getSingleLimitAmount() {
		return singleLimitAmount;
	}

	public void setSingleLimitAmount(Integer singleLimitAmount) {
		this.singleLimitAmount = singleLimitAmount;
	}

	public Integer getDailyLimitAmount() {
		return dailyLimitAmount;
	}

	public void setDailyLimitAmount(Integer dailyLimitAmount) {
		this.dailyLimitAmount = dailyLimitAmount;
	}
	
}
