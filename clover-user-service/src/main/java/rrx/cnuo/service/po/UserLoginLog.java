package rrx.cnuo.service.po;

import java.util.Date;

public class UserLoginLog {
    private Long id;

    private Long uid;

    private Boolean giveNode;

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

    public Boolean getGiveNode() {
        return giveNode;
    }

    public void setGiveNode(Boolean giveNode) {
        this.giveNode = giveNode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}