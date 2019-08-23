package rrx.cnuo.service.po;

import java.util.Date;

public class UserPic {
    private Long id;

    private Long uid;

    private String picUrl;

    private String picUrlThumb;

    private String picUrlOrigin;

    private Byte picOrder;

    private Boolean validStatus;

    private Byte auditStatus;

    private Date createTime;

    private Date updateTime;

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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public String getPicUrlThumb() {
        return picUrlThumb;
    }

    public void setPicUrlThumb(String picUrlThumb) {
        this.picUrlThumb = picUrlThumb == null ? null : picUrlThumb.trim();
    }

    public String getPicUrlOrigin() {
        return picUrlOrigin;
    }

    public void setPicUrlOrigin(String picUrlOrigin) {
        this.picUrlOrigin = picUrlOrigin == null ? null : picUrlOrigin.trim();
    }

    public Byte getPicOrder() {
        return picOrder;
    }

    public void setPicOrder(Byte picOrder) {
        this.picOrder = picOrder;
    }

    public Boolean getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Boolean validStatus) {
        this.validStatus = validStatus;
    }

    public Byte getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Byte auditStatus) {
        this.auditStatus = auditStatus;
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