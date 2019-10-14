package rrx.cnuo.service.po;

import java.util.Date;

public class ShareToFriend {
    private Long uid;

    private Long fuid;
    
    private Boolean award;

    private Date createTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Boolean getAward() {
		return award;
	}

	public void setAward(Boolean award) {
		this.award = award;
	}
    
}