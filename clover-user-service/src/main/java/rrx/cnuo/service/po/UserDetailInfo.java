package rrx.cnuo.service.po;

import java.util.Date;

public class UserDetailInfo {
    private Long uid;

    private String companyName;

    private Byte companyType;

    private Byte industryType;

    private Byte rankType;

    private Byte dishonestCreditResult;

    private Byte duotouLendResult;

    private Byte parentalSituation;

    private String relationWithParentsList;

    private Byte onlyChild;

    private Boolean haveBrother;

    private Boolean haveYoungerBrother;

    private Boolean haveSister;

    private Boolean haveYoungerSister;

    private String interestList;

    private String likeEatList;

    private Byte marryPlan;

    private Byte childPlan;

    private String idealPartnerTypeList;

    private Byte consumeAttitude;

    private Byte activePursuit;

    private Byte mindEmotionExperiences;

    private String singleReasonList;

    private Byte faith;

    private Byte smokDrink;

    private Byte housework;

    private Date createTime;

    private Date updateTime;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public Byte getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Byte companyType) {
        this.companyType = companyType;
    }

    public Byte getIndustryType() {
        return industryType;
    }

    public void setIndustryType(Byte industryType) {
        this.industryType = industryType;
    }

    public Byte getRankType() {
        return rankType;
    }

    public void setRankType(Byte rankType) {
        this.rankType = rankType;
    }

    public Byte getDishonestCreditResult() {
        return dishonestCreditResult;
    }

    public void setDishonestCreditResult(Byte dishonestCreditResult) {
        this.dishonestCreditResult = dishonestCreditResult;
    }

    public Byte getDuotouLendResult() {
        return duotouLendResult;
    }

    public void setDuotouLendResult(Byte duotouLendResult) {
        this.duotouLendResult = duotouLendResult;
    }

    public Byte getParentalSituation() {
        return parentalSituation;
    }

    public void setParentalSituation(Byte parentalSituation) {
        this.parentalSituation = parentalSituation;
    }

    public String getRelationWithParentsList() {
        return relationWithParentsList;
    }

    public void setRelationWithParentsList(String relationWithParentsList) {
        this.relationWithParentsList = relationWithParentsList == null ? null : relationWithParentsList.trim();
    }

    public Byte getOnlyChild() {
        return onlyChild;
    }

    public void setOnlyChild(Byte onlyChild) {
        this.onlyChild = onlyChild;
    }

    public Boolean getHaveBrother() {
        return haveBrother;
    }

    public void setHaveBrother(Boolean haveBrother) {
        this.haveBrother = haveBrother;
    }

    public Boolean getHaveYoungerBrother() {
        return haveYoungerBrother;
    }

    public void setHaveYoungerBrother(Boolean haveYoungerBrother) {
        this.haveYoungerBrother = haveYoungerBrother;
    }

    public Boolean getHaveSister() {
        return haveSister;
    }

    public void setHaveSister(Boolean haveSister) {
        this.haveSister = haveSister;
    }

    public Boolean getHaveYoungerSister() {
        return haveYoungerSister;
    }

    public void setHaveYoungerSister(Boolean haveYoungerSister) {
        this.haveYoungerSister = haveYoungerSister;
    }

    public String getInterestList() {
        return interestList;
    }

    public void setInterestList(String interestList) {
        this.interestList = interestList == null ? null : interestList.trim();
    }

    public String getLikeEatList() {
        return likeEatList;
    }

    public void setLikeEatList(String likeEatList) {
        this.likeEatList = likeEatList == null ? null : likeEatList.trim();
    }

    public Byte getMarryPlan() {
        return marryPlan;
    }

    public void setMarryPlan(Byte marryPlan) {
        this.marryPlan = marryPlan;
    }

    public Byte getChildPlan() {
        return childPlan;
    }

    public void setChildPlan(Byte childPlan) {
        this.childPlan = childPlan;
    }

    public String getIdealPartnerTypeList() {
        return idealPartnerTypeList;
    }

    public void setIdealPartnerTypeList(String idealPartnerTypeList) {
        this.idealPartnerTypeList = idealPartnerTypeList == null ? null : idealPartnerTypeList.trim();
    }

    public Byte getConsumeAttitude() {
        return consumeAttitude;
    }

    public void setConsumeAttitude(Byte consumeAttitude) {
        this.consumeAttitude = consumeAttitude;
    }

    public Byte getActivePursuit() {
        return activePursuit;
    }

    public void setActivePursuit(Byte activePursuit) {
        this.activePursuit = activePursuit;
    }

    public Byte getMindEmotionExperiences() {
        return mindEmotionExperiences;
    }

    public void setMindEmotionExperiences(Byte mindEmotionExperiences) {
        this.mindEmotionExperiences = mindEmotionExperiences;
    }

    public String getSingleReasonList() {
        return singleReasonList;
    }

    public void setSingleReasonList(String singleReasonList) {
        this.singleReasonList = singleReasonList == null ? null : singleReasonList.trim();
    }

    public Byte getFaith() {
        return faith;
    }

    public void setFaith(Byte faith) {
        this.faith = faith;
    }

    public Byte getSmokDrink() {
        return smokDrink;
    }

    public void setSmokDrink(Byte smokDrink) {
        this.smokDrink = smokDrink;
    }

    public Byte getHousework() {
        return housework;
    }

    public void setHousework(Byte housework) {
        this.housework = housework;
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