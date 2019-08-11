package rrx.cnuo.service.vo.paycenter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 只封装传给支付中心参数
 * @author xuhongyu
 * @version 创建时间：2018年6月30日 上午11:40:44
 */
public class PayServiceVo {

	/**
	 * 支付中心分配,验证请求合法性
	 */
	private String apiKey;
	
	/**
	 * 支付中心分配,验证请求合法性
	 */
	private String apiSecret;
	
	/**
	 * 支付中心分配,商户标识(JJD)
	 */
	private String merchantSign;
	
	/**
	 * 支付中心分配,验证通知合法性
	 */
	private String notifySecret;
	
	/**
	 * 商户订单号
	 */
	private Long orderNo;
	
	/**
	 * 现金流动类型：1-代收/代扣；2-代付
	 */
	private Byte cashFlowType;
	
	/**
	 * 交易(已经加上各种费用了)金额，以分为单位
	 */
	private Integer amount;
	
	/**
	 * 卡支付通道：0-掌上汇通P2P通道；1-掌上汇通快捷通道；2-余额支付通道；4-易联插件通道；5-易联代收代付通道；
	 * 7-合利宝支付通道；8-易宝支付通道；17-富友-协议支付(代收)；18-银联WAP支付(代收)；19-联拓
	 */
	private Byte payChannelType;
	
	/**
	 * 微信支付时传openId
	 */
	private String openId;
	
	/**
	 * 用户ip
	 */
//	private String userIp;
	
	/**
	 * 用户id（支付中心用）
	 */
	private Long uid;
	
	/**
	 * 订单主题
	 */
	private String orderSubject;
	
	/**
	 * 代付业务类型：2-银行卡代付；3:余额支付, 4:退款, 5:撤销
	 */
	private Byte paymentType;
	
	/**
	 * 账目区
	 */
	private String accountZone;
	
	/**
	 * 源订单号，业务类型为撤销或退款时必填
	 */
	private String sourceOrderNo;
	
	/**
	 * 支付订单号或协议支付绑卡流水号(第三方支付公司返回)
	 */
	private String payOrderNo;
	
	/**
	 * 支付令牌(第三方支付公司返回)
	 */
	private String payToken;
	
	/**
	 * 代扣交易状态(0:处理中, 1:成功, 2:失败, 3:关闭
	 */
	private Integer collectionStatus;
	
	/**
	 * 对账日期
	 */
	private String reconciliationDate;
	
	/**
	 * 账单日期
	 */
	private String billDate;
	
	/**
	 * 对账后确认的交易状态(1:成功, 2:失败)
	 */
	private Integer transactionStatus;

	/**
	 * 对账时用
	 */
	private List<Byte> channels = new ArrayList<Byte>();
	
	
	/**
	 * 初始化对象时，写入apiKey、apiSecret和merchantSign
	 */
	public PayServiceVo(String apiKey,String apiSecret,String merchantSign){
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		this.merchantSign = merchantSign;
	}

	public String getNotifySecret() {
		return notifySecret;
	}

	public void setNotifySecret(String notifySecret) {
		this.notifySecret = notifySecret;
	}

	/**
	 * 最终传给支付中心的交易(已经加上各种费用了)金额，以分为单位
	 * @author xuhongyu
	 */
	public Integer getAmount() {
		return amount;
	}

	/**
	 * 最终传给支付中心的交易(已经加上各种费用了)金额，以分为单位
	 * @author xuhongyu
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
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

	public Integer getCollectionStatus() {
		return collectionStatus;
	}

	public void setCollectionStatus(Integer collectionStatus) {
		this.collectionStatus = collectionStatus;
	}

	public String getReconciliationDate() {
		return reconciliationDate;
	}

	public void setReconciliationDate(String reconciliationDate) {
		this.reconciliationDate = reconciliationDate;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public Integer getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(Integer transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public Byte getCashFlowType() {
		return cashFlowType;
	}

	public void setCashFlowType(Byte cashFlowType) {
		this.cashFlowType = cashFlowType;
	}

	public Byte getPayChannelType() {
		return payChannelType;
	}

	public void setPayChannelType(Byte payChannelType) {
		this.payChannelType = payChannelType;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getOrderSubject() {
		return orderSubject;
	}

	public void setOrderSubject(String orderSubject) {
		this.orderSubject = orderSubject;
	}

	public String getAccountZone() {
		return accountZone;
	}

	public void setAccountZone(String accountZone) {
		this.accountZone = accountZone;
	}

	public String getSourceOrderNo() {
		return sourceOrderNo;
	}

	public void setSourceOrderNo(String sourceOrderNo) {
		this.sourceOrderNo = sourceOrderNo;
	}

	public Byte getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Byte paymentType) {
		this.paymentType = paymentType;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public List<Byte> getChannels() {
		return channels;
	}

	public void setChannels(List<Byte> channels) {
		this.channels = channels;
	}

	/**
	 * 包含apiKey、apiSecret、merchantSign三个字段的JSONObject
	 * @author xuhongyu
	 * @return
	 */
	private JSONObject toCommenJSONString(){
		JSONObject commonJsonObj = new JSONObject();
		if(StringUtils.isNotBlank(this.apiKey)){
			commonJsonObj.put("apiKey", this.apiKey);
		}
		if(StringUtils.isNotBlank(this.apiSecret)){
			commonJsonObj.put("apiSecret", this.apiSecret);
		}
		if(StringUtils.isNotBlank(this.merchantSign)){
			commonJsonObj.put("merchantSign", this.merchantSign);
		}
		return commonJsonObj;
	}

	/**
	 * 申请调度支付通道的json报文
	 * @author xuhongyu
	 */
	public String toScheduleJSONString(){
		JSONObject jsonObj = toCommenJSONString();
		if(this.cashFlowType != null){
			jsonObj.put("cashFlowType", this.cashFlowType);
		}
		if(this.amount != null && this.amount != 0){
			jsonObj.put("amount", this.amount);
		}
		return jsonObj.toJSONString();
	}
	
	/**
	 * 申请代收的json报文
	 * @author xuhongyu
	 */
	public String toCollectionApplyJSONString(){
		JSONObject jsonObj = toCommenJSONString();
		if(this.orderNo != null){
			jsonObj.put("orderNo", this.orderNo);
		}
		if(this.amount != null){
			jsonObj.put("amount", this.amount);
		}
		if(this.payChannelType != null){
			jsonObj.put("payChannelType", this.payChannelType);
		}
		if(this.uid != null){
			jsonObj.put("uid", this.uid);
		}
		if(StringUtils.isNotBlank(this.orderSubject)){
			jsonObj.put("orderSubject", this.orderSubject);
		}
		if(this.accountZone != null){
			jsonObj.put("accountZone", this.accountZone);
		}
		if(StringUtils.isNotBlank(this.openId)){
			jsonObj.put("openId", this.openId);
		}
		if(StringUtils.isNotBlank(this.payToken)){
			jsonObj.put("payToken", this.payToken);
		}
		if(StringUtils.isNotBlank(this.payOrderNo)){
			jsonObj.put("payOrderNo", this.payOrderNo);
		}
		if(StringUtils.isNotBlank(this.sourceOrderNo)){
			jsonObj.put("sourceOrderNo", this.sourceOrderNo);
		}
		if(this.cashFlowType != null){
			jsonObj.put("cashFlowType", this.cashFlowType);
		}
		if(this.paymentType != null){
			jsonObj.put("paymentType", this.paymentType);
		}
		return jsonObj.toJSONString();
	}
	
	/**
	 * 申请对账的json报文
	 * @author xuhongyu
	 * @return
	 */
	public String toReconciliationApplyJSONString(){
		JSONObject jsonObj = toCommenJSONString();
		if(StringUtils.isNotBlank(this.reconciliationDate)){
			jsonObj.put("reconciliationDate", this.reconciliationDate);
		}
		if(this.channels != null && this.channels.size() > 0){
			jsonObj.put("channels", this.channels);
		}
		return jsonObj.toJSONString();
	}


}
