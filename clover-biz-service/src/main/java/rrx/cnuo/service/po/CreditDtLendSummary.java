package rrx.cnuo.service.po;

public class CreditDtLendSummary {
    private Long uid;

    private Short loanSuccessRate;

    private Short loanPlatformCount;

    private Short badnessPlatformCount;

    private Integer borrowCnt;

    private Integer borrowSuccessCnt;

    private Integer refuseCount;

    private Integer overdueCnt;

    private Long overdueAmt;

    private Integer auditCount;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Short getLoanSuccessRate() {
        return loanSuccessRate;
    }

    public void setLoanSuccessRate(Short loanSuccessRate) {
        this.loanSuccessRate = loanSuccessRate;
    }

    public Short getLoanPlatformCount() {
        return loanPlatformCount;
    }

    public void setLoanPlatformCount(Short loanPlatformCount) {
        this.loanPlatformCount = loanPlatformCount;
    }

    public Short getBadnessPlatformCount() {
        return badnessPlatformCount;
    }

    public void setBadnessPlatformCount(Short badnessPlatformCount) {
        this.badnessPlatformCount = badnessPlatformCount;
    }

    public Integer getBorrowCnt() {
        return borrowCnt;
    }

    public void setBorrowCnt(Integer borrowCnt) {
        this.borrowCnt = borrowCnt;
    }

    public Integer getBorrowSuccessCnt() {
        return borrowSuccessCnt;
    }

    public void setBorrowSuccessCnt(Integer borrowSuccessCnt) {
        this.borrowSuccessCnt = borrowSuccessCnt;
    }

    public Integer getRefuseCount() {
        return refuseCount;
    }

    public void setRefuseCount(Integer refuseCount) {
        this.refuseCount = refuseCount;
    }

    public Integer getOverdueCnt() {
        return overdueCnt;
    }

    public void setOverdueCnt(Integer overdueCnt) {
        this.overdueCnt = overdueCnt;
    }

    public Long getOverdueAmt() {
        return overdueAmt;
    }

    public void setOverdueAmt(Long overdueAmt) {
        this.overdueAmt = overdueAmt;
    }

    public Integer getAuditCount() {
        return auditCount;
    }

    public void setAuditCount(Integer auditCount) {
        this.auditCount = auditCount;
    }
}