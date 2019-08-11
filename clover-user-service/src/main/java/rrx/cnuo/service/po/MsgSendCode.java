package rrx.cnuo.service.po;

import java.util.Date;

public class MsgSendCode {
    private Long id;

    private String telephone;

    private Boolean voiceStatus;

    private Integer code;

    private Boolean state;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public Boolean getVoiceStatus() {
        return voiceStatus;
    }

    public void setVoiceStatus(Boolean voiceStatus) {
        this.voiceStatus = voiceStatus;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}