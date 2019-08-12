package rrx.cnuo.cncommon.vo;

/**
 * 支付业务相关参数
 * @author xuhongyu
 * @version 创建时间：2018年6月30日 上午11:41:43
 */
public class PayBusinessVo{

	private Long userId;
	
	/**
	 * 微信支付时传openId
	 */
	private String openId;
	
	/**
	 * 交易表主键tradeId
	 */
	private Long tradeId;
	
	/**
	 * 用户在前端想要支付或提现的钱(没有加或减任何系统费用)
	 */
	private Integer amount;
	
	/**
	 * 充值/提现手续费
	 */
	private Integer rechargeWithdrawFee = 0;
	
	/**
	 * 不同业务类型对应不同的系统费用：比如提现的垫资额等
	 */
	private Integer businessFee = 0;
	
	/**
	 * 交易(已经加或减各种费用了)金额，以分为单位
	 */
	private Integer totalAmount;
	
	/**
	 * 支付通道：0-掌上汇通P2P通道；1-掌上汇通快捷通道；2-余额支付通道；4-易联插件通道；5-易联代收代付通道；7-合利宝支付通道；8-易宝支付通道；17-富友-协议支付(代收)；18-银联WAP支付(代收)；19-联拓
	 */
	private Byte payChannelType;
	
	/**
	 * 支付业务类型：1-充值；2-提现；3-显示手机号；4-成交分钱；
	 */
	private Byte payBusinessType;
	
	/**
	 * 支付方式 ：0.余额  1.银行卡  2-线下 3.银联(收银台类) 4.微信(app类)
	 */
	private Byte payMethod;
	
    /**
	 * 现金流动类型：0-代收/代扣；1-代付
	 */
	private Byte cashFlowType;
	
	/**
	 * 支付订单号或协议支付绑卡流水号(第三方支付公司返回)
	 */
	private String payOrderNo;
	
	/**
	 * 支付令牌(第三方支付公司返回)
	 */
	private String payToken;
	
	/**
	 * 是否是临时做账(默认是false)
	 */
	private boolean makeTempAccount;
	
	/**
	 * 用户ip（用于余额支付、提现等需要发送短信验证码时用）
	 */
//	private String userIp;
	
	/**
	 * 支付密码
	 */
//	private String payPassword;
	
	/**
	 * 是否是微信端(支付申请时必然是false)
	 */
//	private boolean weixin;
	
	/**
	 * 短信验证码
	 */
//	private String authCode;
	
	/**
	 * 预留字符串，用于银联支付或微信支付时用于回盘时做报盘表操作的业务数据（存成json字符串）
	 */
	private String reserveData;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Long getTradeId() {
		return tradeId;
	}

	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}

	/**
	 * 未经处理的用户原始需求金额(不是实际交易金额)
	 * @author xuhongyu
	 * @return
	 */
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * 充值/提现手续费
	 * @author xuhongyu
	 * @return
	 */
	public Integer getRechargeWithdrawFee() {
		return rechargeWithdrawFee;
	}

	/**
	 * 充值/提现手续费
	 * @author xuhongyu
	 * @param rechargeWithdrawFee
	 */
	public void setRechargeWithdrawFee(Integer rechargeWithdrawFee) {
		this.rechargeWithdrawFee = rechargeWithdrawFee;
	}

	public Integer getBusinessFee() {
		return businessFee;
	}

	public void setBusinessFee(Integer businessFee) {
		this.businessFee = businessFee;
	}

	/**
	 * 最终(实际的)交易金额
	 * @author xuhongyu
	 * @return
	 */
	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Byte getPayChannelType() {
		return payChannelType;
	}

	public void setPayChannelType(Byte payChannelType) {
		this.payChannelType = payChannelType;
	}

	public Byte getPayBusinessType() {
		return payBusinessType;
	}

	public void setPayBusinessType(Byte payBusinessType) {
		this.payBusinessType = payBusinessType;
	}

	public Byte getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(Byte payMethod) {
		this.payMethod = payMethod;
	}

	public Byte getCashFlowType() {
		return cashFlowType;
	}

	public void setCashFlowType(Byte cashFlowType) {
		this.cashFlowType = cashFlowType;
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}

	public String getPayToken() {
		return payToken;
	}

	public void setPayToken(String payToken) {
		this.payToken = payToken;
	}
	
	/**
	 * 是否是临时做账
	 * @author xuhongyu
	 * @return
	 */
	public boolean isMakeTempAccount() {
		return makeTempAccount;
	}

	/**
	 * 是否是临时做账
	 * @author xuhongyu
	 * @param makeTempAccount
	 */
	public void setMakeTempAccount(boolean makeTempAccount) {
		this.makeTempAccount = makeTempAccount;
	}

	public String getReserveData() {
		return reserveData;
	}

	public void setReserveData(String reserveData) {
		this.reserveData = reserveData;
	}

}
