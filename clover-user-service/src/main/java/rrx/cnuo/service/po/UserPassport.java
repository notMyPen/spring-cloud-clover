package rrx.cnuo.service.po;

import java.util.Date;

public class UserPassport {
    private Long uid;

    private String registTel;

    private String miniOpenId;

    private String openId;

    private String nickName;

    private String avatarUrl;

    private Integer cardNum;

    private Boolean creditPass;

    private Byte boardStatus;

    private Boolean highQuality;

    private Boolean subscribe;

    private Date createTime;

    private Date updateTime;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getRegistTel() {
        return registTel;
    }

    public void setRegistTel(String registTel) {
        this.registTel = registTel == null ? null : registTel.trim();
    }

    public String getMiniOpenId() {
        return miniOpenId;
    }

    public void setMiniOpenId(String miniOpenId) {
        this.miniOpenId = miniOpenId == null ? null : miniOpenId.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    public Integer getCardNum() {
        return cardNum;
    }

    public void setCardNum(Integer cardNum) {
        this.cardNum = cardNum;
    }

    public Boolean getCreditPass() {
        return creditPass;
    }

    public void setCreditPass(Boolean creditPass) {
        this.creditPass = creditPass;
    }

    public Byte getBoardStatus() {
        return boardStatus;
    }

    public void setBoardStatus(Byte boardStatus) {
        this.boardStatus = boardStatus;
    }

    public Boolean getHighQuality() {
        return highQuality;
    }

    public void setHighQuality(Boolean highQuality) {
        this.highQuality = highQuality;
    }

    public Boolean getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Boolean subscribe) {
        this.subscribe = subscribe;
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