package rrx.cnuo.service.po;

import java.util.Date;

public class Trade {
    private Long id;

    private String payOrderNo;

    private Byte tradeType;

    private Long uid;

    private Boolean withdrawType;

    private Integer amount;

    private String reserveData;

    private Byte tradeStatus;

    private Byte businessType;

    private Integer sendTime;

    private Integer receiveTime;

    private Boolean reconciliationStatus;

    private Integer reconciliationTime;

    private Date createTime;

    private Date updateTime;
    
    /*
     * 以下四个检索用
     */
    private Byte tradeStatusBegin;
    
    private Byte tradeStatusEnd;
    
    private Integer sendBeginTime;
    
    private Integer sendEndTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo == null ? null : payOrderNo.trim();
    }

    public Byte getTradeType() {
        return tradeType;
    }

    public void setTradeType(Byte tradeType) {
        this.tradeType = tradeType;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Boolean getWithdrawType() {
        return withdrawType;
    }

    public void setWithdrawType(Boolean withdrawType) {
        this.withdrawType = withdrawType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getReserveData() {
        return reserveData;
    }

    public void setReserveData(String reserveData) {
        this.reserveData = reserveData == null ? null : reserveData.trim();
    }

    public Byte getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Byte tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public Byte getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Byte businessType) {
        this.businessType = businessType;
    }

    public Integer getSendTime() {
        return sendTime;
    }

    public void setSendTime(Integer sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Integer receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Boolean getReconciliationStatus() {
        return reconciliationStatus;
    }

    public void setReconciliationStatus(Boolean reconciliationStatus) {
        this.reconciliationStatus = reconciliationStatus;
    }

    public Integer getReconciliationTime() {
        return reconciliationTime;
    }

    public void setReconciliationTime(Integer reconciliationTime) {
        this.reconciliationTime = reconciliationTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public Byte getTradeStatusBegin() {
		return tradeStatusBegin;
	}

	public void setTradeStatusBegin(Byte tradeStatusBegin) {
		this.tradeStatusBegin = tradeStatusBegin;
	}

	public Byte getTradeStatusEnd() {
		return tradeStatusEnd;
	}

	public void setTradeStatusEnd(Byte tradeStatusEnd) {
		this.tradeStatusEnd = tradeStatusEnd;
	}

	public Integer getSendBeginTime() {
		return sendBeginTime;
	}

	public void setSendBeginTime(Integer sendBeginTime) {
		this.sendBeginTime = sendBeginTime;
	}

	public Integer getSendEndTime() {
		return sendEndTime;
	}

	public void setSendEndTime(Integer sendEndTime) {
		this.sendEndTime = sendEndTime;
	}
    
}