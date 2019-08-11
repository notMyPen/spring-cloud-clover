package rrx.cnuo.service.po;

import java.util.Date;

public class UserAccountList {
    private Long id;

    private Long tradeId;

    private Long uid;

    private Long fuid;

    private Integer amount;

    private Byte acountType;

    private Byte businessType;

    private Byte tradeType;

    private Boolean validStatus;

    private Boolean receiveBankStatus;

    private Boolean reconciliationStatus;

    private Integer receiveTime;

    private Integer reconciliationTime;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getFuid() {
        return fuid;
    }

    public void setFuid(Long fuid) {
        this.fuid = fuid;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Byte getAcountType() {
        return acountType;
    }

    public void setAcountType(Byte acountType) {
        this.acountType = acountType;
    }

    public Byte getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Byte businessType) {
        this.businessType = businessType;
    }

    public Byte getTradeType() {
        return tradeType;
    }

    public void setTradeType(Byte tradeType) {
        this.tradeType = tradeType;
    }

    public Boolean getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Boolean validStatus) {
        this.validStatus = validStatus;
    }

    public Boolean getReceiveBankStatus() {
        return receiveBankStatus;
    }

    public void setReceiveBankStatus(Boolean receiveBankStatus) {
        this.receiveBankStatus = receiveBankStatus;
    }

    public Boolean getReconciliationStatus() {
        return reconciliationStatus;
    }

    public void setReconciliationStatus(Boolean reconciliationStatus) {
        this.reconciliationStatus = reconciliationStatus;
    }

    public Integer getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Integer receiveTime) {
        this.receiveTime = receiveTime;
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
}