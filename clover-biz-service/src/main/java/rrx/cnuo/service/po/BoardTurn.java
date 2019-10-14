package rrx.cnuo.service.po;

import java.util.Date;

public class BoardTurn {
    private Long id;

    private Long uid;

    private Long fuid;
    
    private Byte useCardNum;

    private String message;

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

    public Long getFuid() {
        return fuid;
    }

    public void setFuid(Long fuid) {
        this.fuid = fuid;
    }
    
    public Byte getUseCardNum() {
		return useCardNum;
	}

	public void setUseCardNum(Byte useCardNum) {
		this.useCardNum = useCardNum;
	}

	public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}