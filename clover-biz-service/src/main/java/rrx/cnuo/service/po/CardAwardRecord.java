package rrx.cnuo.service.po;

import java.util.Date;

public class CardAwardRecord {
    private Long id;

    private Long uid;

    private Integer awardNum;

    private Byte awardType;

    private Long relationId;

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

    public Integer getAwardNum() {
        return awardNum;
    }

    public void setAwardNum(Integer awardNum) {
        this.awardNum = awardNum;
    }

    public Byte getAwardType() {
        return awardType;
    }

    public void setAwardType(Byte awardType) {
        this.awardType = awardType;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}