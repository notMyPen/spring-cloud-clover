package rrx.cnuo.service.po;

import java.util.Date;

public class BoardStatis {
    private Long uid;

    private Integer likeCnt;

    private Integer likedCnt;

    private Integer turnCnt;

    private Integer turnedCnt;

    private Integer viewCnt;

    private Integer viewedCnt;

    private Date updateTime;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(Integer likeCnt) {
        this.likeCnt = likeCnt;
    }

    public Integer getLikedCnt() {
        return likedCnt;
    }

    public void setLikedCnt(Integer likedCnt) {
        this.likedCnt = likedCnt;
    }

    public Integer getTurnCnt() {
        return turnCnt;
    }

    public void setTurnCnt(Integer turnCnt) {
        this.turnCnt = turnCnt;
    }

    public Integer getTurnedCnt() {
        return turnedCnt;
    }

    public void setTurnedCnt(Integer turnedCnt) {
        this.turnedCnt = turnedCnt;
    }

    public Integer getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(Integer viewCnt) {
        this.viewCnt = viewCnt;
    }

    public Integer getViewedCnt() {
        return viewedCnt;
    }

    public void setViewedCnt(Integer viewedCnt) {
        this.viewedCnt = viewedCnt;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}