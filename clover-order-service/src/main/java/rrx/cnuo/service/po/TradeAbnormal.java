package rrx.cnuo.service.po;

import java.util.Date;

public class TradeAbnormal {
    private Long tradeId;

    private Byte tradeType;

    private Byte type;

    private Date reconDate;

    private Boolean result;

    private Integer amount;

    private Byte abnormalType;

    private Boolean sendMsgStatus;

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

    public Date getReconDate() {
        return reconDate;
    }

    public void setReconDate(Date reconDate) {
        this.reconDate = reconDate;
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

    public Byte getAbnormalType() {
        return abnormalType;
    }

    public void setAbnormalType(Byte abnormalType) {
        this.abnormalType = abnormalType;
    }

    public Boolean getSendMsgStatus() {
        return sendMsgStatus;
    }

    public void setSendMsgStatus(Boolean sendMsgStatus) {
        this.sendMsgStatus = sendMsgStatus;
    }
}