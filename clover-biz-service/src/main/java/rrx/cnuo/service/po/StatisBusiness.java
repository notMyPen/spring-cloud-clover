package rrx.cnuo.service.po;

import java.util.Date;

public class StatisBusiness extends StatisBusinessKey {
    private Long value;

    private Date updateTime;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}