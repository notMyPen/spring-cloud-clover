package rrx.cnuo.service.vo.paycenter;

import org.apache.commons.lang3.StringUtils;

import rrx.cnuo.cncommon.util.DateUtil;

public class Record {

	/**
	 * 订单号
	 */
	private Long orderNo;
	
	/**
	 * 交易id（根据orderNo转换而来）
	 */
//	private Long tradeId;
	
	/**
	 * 订单状态|0:处理中, 1:成功, 2:失败, 3:过期, 4:关闭, 5:待商户确认 9:其他
	 */
	private Integer orderStatus;
	
	/**
	 * 订单是否成功（根据orderStatus转换而来）：1:成功, 2:失败--》0.否1.是
	 */
	private Boolean result;
	
	/**
	 * 支付通道
	 */
	private Integer payChannelType;
	
	/**
	 * 现金流动类型：1:流入, 2:流出---》代收0；代付1
	 */
	private Integer cashFlowType;
	
	/**
	 * 订单金额
	 */
	private Integer amount;
	
	/**
     * 第三方交易手续费
     */
//    private Integer commision;
	
	/**
	 * 账单日期（yyyy-MM-dd）
	 */
	private String billDate;
	
	/**
	 * 根据billDate转换出的date类型的账单日期
	 */
	private Integer billDateDay;
	
	/**
	 * 对账日期（yyyy-MM-dd）
	 */
	private String reconciliationDate;
	
	/**
	 * 根据reconciliationDate转换出的date类型的对账日期
	 */
	private Integer reconciliationDateDay;
	
	
	
	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		/*if(StringUtils.isNotBlank(orderNo)){
			this.tradeId = Long.parseLong(orderNo);
		}*/
		this.orderNo = orderNo;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	/**
	 * 订单是否成功：1:成功, 2:失败--》0.否1.是
	 */
	public void setOrderStatus(Integer orderStatus) {
		if(orderStatus != null){
			if(orderStatus == 1){
				this.result = true;
			}else{
				this.result = false;
			}
			this.orderStatus = orderStatus;
		}
	}
	
	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		if(result != null){
			this.result = result;
		}
	}

	public Integer getPayChannelType() {
		return payChannelType;
	}

	public void setPayChannelType(Integer payChannelType) {
		this.payChannelType = payChannelType;
	}

	public Integer getCashFlowType() {
		return cashFlowType;
	}

	/**
	 * 现金流动类型：1:流入, 2:流出---》代收0；代付1
	 */
	public void setCashFlowType(Integer cashFlowType) {
		if(cashFlowType != null){
			if(cashFlowType == 1){
				this.cashFlowType = 0;
			}else if(cashFlowType == 2){
				this.cashFlowType = 1;
			}else{
				this.cashFlowType = cashFlowType;
			}
		}
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
     * 第三方交易手续费(单位已被转换为"分")
     */
    /*public Integer getCommision() {
		return commision;
	}*/

    /**
     * 第三方交易手续费(将支付中心传过来的单位"厘"转换为单位"分")
     */
	/*public void setCommision(Integer commision) {
		if(commision != null){
			BigDecimal commisionB = new BigDecimal(commision).divide(new BigDecimal(10), 0, BigDecimal.ROUND_HALF_UP);
			this.commision = commisionB.intValue();
		}
	}*/
	
	/*public Long getTradeId() {
		return tradeId;
	}

	public void setTradeId(Long tradeId) {
		if(tradeId != null){
			this.orderNo = tradeId.toString();
			this.tradeId = tradeId;
		}
	}*/

	public String getBillDate() {
		return billDate;
	}

	/**
	 * 账单日期（yyyy-MM-dd）
	 * @author xuhongyu
	 * @param billDate
	 * @throws Exception 
	 */
	public void setBillDate(String billDate) throws Exception {
		if(StringUtils.isNotBlank(billDate)){
			this.billDateDay = DateUtil.getSecond(DateUtil.format(billDate, "yyyy-MM-dd"));
			this.billDate = billDate;
		}
	}

	public Integer getBillDateDay() {
		return billDateDay;
	}

	public void setBillDateDay(Integer billDateDay) {
		if(billDateDay != null){
			this.billDateDay = billDateDay;
		}
	}

	public String getReconciliationDate() {
		return reconciliationDate;
	}

	/**
	 * 对账日期（yyyy-MM-dd）
	 * @author xuhongyu
	 * @param reconciliationDate
	 * @throws Exception 
	 */
	public void setReconciliationDate(String reconciliationDate) throws Exception {
		if(StringUtils.isNotBlank(reconciliationDate)){
			this.reconciliationDateDay = DateUtil.getSecond(DateUtil.format(reconciliationDate, "yyyy-MM-dd"));
			this.reconciliationDate = reconciliationDate;
		}
	}

	public Integer getReconciliationDateDay() {
		return reconciliationDateDay;
	}

	public void setReconciliationDateDay(Integer reconciliationDateDay) {
		if(reconciliationDateDay != null){
			this.reconciliationDateDay = reconciliationDateDay;
		}
	}
	
}
