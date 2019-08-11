package rrx.cnuo.service.vo;

/**
 * 做账参数传递用
 * @author xuhongyu
 * @version 创建时间：2018年7月7日 下午3:30:19
 */
public class MakeAccountListVo {

	private Long userId;
	private Long targetUserId;
	private byte payAcountType;
	private byte recvAcountType;
	private int accountAmount;
//	private Long relationId;
	
	private Long tradeId;
	private Byte tradeType;
	private Integer receiveTime;
//	private Date createTime;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getTargetUserId() {
		return targetUserId;
	}
	public void setTargetUserId(Long targetUserId) {
		this.targetUserId = targetUserId;
	}
	public byte getPayAcountType() {
		return payAcountType;
	}
	public void setPayAcountType(byte payAcountType) {
		this.payAcountType = payAcountType;
	}
	public byte getRecvAcountType() {
		return recvAcountType;
	}
	public void setRecvAcountType(byte recvAcountType) {
		this.recvAcountType = recvAcountType;
	}
	public int getAccountAmount() {
		return accountAmount;
	}
	public void setAccountAmount(int accountAmount) {
		this.accountAmount = accountAmount;
	}
	
	public Long getTradeId() {
		return tradeId;
	}
	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}
	public Byte getTradeType() {
		return tradeType;
	}
	public void setTradeType(Byte tradeType) {
		this.tradeType = tradeType;
	}
	public Integer getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Integer receiveTime) {
		this.receiveTime = receiveTime;
	}
}
