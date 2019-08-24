package rrx.cnuo.cncommon.vo.config;

/**
 * 基础配置
 * @author xuhongyu
 * @date 2019年7月8日
 */
public class BasicConfig {

	private boolean sendSMS;// 是否真发短信
	private boolean payByService;// 是否调用支付中心
	private boolean realReconciliations;// 是否真实对账
	private boolean prodEnvironment;// 是否是生产环境
//	private String aesEncryptKey; // aes加密算法秘钥
	private Integer snowflakeNode; // 雪花算法节点名
	
	public boolean isSendSMS() {
		return sendSMS;
	}
	public void setSendSMS(boolean sendSMS) {
		this.sendSMS = sendSMS;
	}
	public boolean isPayByService() {
		return payByService;
	}
	public void setPayByService(boolean payByService) {
		this.payByService = payByService;
	}
	public boolean isRealReconciliations() {
		return realReconciliations;
	}
	public void setRealReconciliations(boolean realReconciliations) {
		this.realReconciliations = realReconciliations;
	}
	public boolean isProdEnvironment() {
		return prodEnvironment;
	}
	public void setProdEnvironment(boolean prodEnvironment) {
		this.prodEnvironment = prodEnvironment;
	}
//	public String getAesEncryptKey() {
//		return aesEncryptKey;
//	}
//	public void setAesEncryptKey(String aesEncryptKey) {
//		this.aesEncryptKey = aesEncryptKey;
//	}
	public Integer getSnowflakeNode() {
		return snowflakeNode;
	}
	public void setSnowflakeNode(Integer snowflakeNode) {
		this.snowflakeNode = snowflakeNode;
	}
	
}
