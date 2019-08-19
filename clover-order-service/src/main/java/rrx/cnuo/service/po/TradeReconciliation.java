package rrx.cnuo.service.po;

import java.util.Date;

public class TradeReconciliation {
    private Long tradeId;

    private Byte tradeType;

    private Byte type;

    private Integer reconDate;

    private Integer billDate;

    private Boolean result;

    private Integer amount;

    private Integer commision;

    private Boolean billStatus;

    private Date createTime;
    
    /**
     * 异常类型：0-该订单我们有第三方没有；1-该订单我们没有第三方有；3，状态不一致；4-金额不一致；5-状态和金额都不一致
     */
    private Byte abnormalType;
    
    public TradeReconciliation(Long tradeId, Integer reconDate,Integer billDate, Byte type, Boolean result, Integer amount, Byte tradeType,Byte abnormalType) {
        this.tradeId = tradeId;
        this.reconDate = reconDate;
        this.billDate = billDate;
        this.type = type;
        this.result = result;
        this.amount = amount;
        this.tradeType = tradeType;
        this.abnormalType = abnormalType;
    }
    
    public TradeReconciliation() {
        super();
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getReconDate() {
        return reconDate;
    }

    public void setReconDate(Integer reconDate) {
        this.reconDate = reconDate;
    }

    public Integer getBillDate() {
        return billDate;
    }

    public void setBillDate(Integer billDate) {
        this.billDate = billDate;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getCommision() {
        return commision;
    }

    public void setCommision(Integer commision) {
        this.commision = commision;
    }

    public Boolean getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(Boolean billStatus) {
        this.billStatus = billStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Byte getAbnormalType() {
		return abnormalType;
	}

	public void setAbnormalType(Byte abnormalType) {
		this.abnormalType = abnormalType;
	}
    
}