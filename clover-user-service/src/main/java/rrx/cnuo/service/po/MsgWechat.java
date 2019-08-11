package rrx.cnuo.service.po;

import java.util.Date;

public class MsgWechat {
    private Long id;

    private Long uid;

    private Boolean viewStatus;

    private Byte msgType;

    private String msgValue;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Boolean getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(Boolean viewStatus) {
        this.viewStatus = viewStatus;
    }

    public Byte getMsgType() {
        return msgType;
    }

    public void setMsgType(Byte msgType) {
        this.msgType = msgType;
    }

    public String getMsgValue() {
        return msgValue;
    }

    public void setMsgValue(String msgValue) {
        this.msgValue = msgValue == null ? null : msgValue.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}