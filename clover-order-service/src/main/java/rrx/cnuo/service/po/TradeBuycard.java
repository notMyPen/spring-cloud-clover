package rrx.cnuo.service.po;

import java.util.Date;

public class TradeBuycard {
    private Long tradeId;

    private Long uid;

    private Integer amount;

    private Byte buyNum;

    private Byte awardBoardNum;

    private Boolean validStatus;

    private Boolean receiveStatus;

    private Date createTime;

    private Date updateTime;

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Byte getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Byte buyNum) {
        this.buyNum = buyNum;
    }

    public Byte getAwardBoardNum() {
        return awardBoardNum;
    }

    public void setAwardBoardNum(Byte awardBoardNum) {
        this.awardBoardNum = awardBoardNum;
    }

    public Boolean getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Boolean validStatus) {
        this.validStatus = validStatus;
    }

    public Boolean getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(Boolean receiveStatus) {
        this.receiveStatus = receiveStatus;
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