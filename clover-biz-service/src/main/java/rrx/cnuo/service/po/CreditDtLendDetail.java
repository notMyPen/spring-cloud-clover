package rrx.cnuo.service.po;

public class CreditDtLendDetail {
    private Long id;

    private Long uid;

    private Byte borrowType;

    private Byte borrowState;

    private Integer borrowAmount;

    private Integer contractDate;

    private Integer loanPeriod;

    private Integer repayState;

    private Integer arrearsAmount;

    private String companyCode;

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

    public Byte getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(Byte borrowType) {
        this.borrowType = borrowType;
    }

    public Byte getBorrowState() {
        return borrowState;
    }

    public void setBorrowState(Byte borrowState) {
        this.borrowState = borrowState;
    }

    public Integer getBorrowAmount() {
        return borrowAmount;
    }

    public void setBorrowAmount(Integer borrowAmount) {
        this.borrowAmount = borrowAmount;
    }

    public Integer getContractDate() {
        return contractDate;
    }

    public void setContractDate(Integer contractDate) {
        this.contractDate = contractDate;
    }

    public Integer getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(Integer loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public Integer getRepayState() {
        return repayState;
    }

    public void setRepayState(Integer repayState) {
        this.repayState = repayState;
    }

    public Integer getArrearsAmount() {
        return arrearsAmount;
    }

    public void setArrearsAmount(Integer arrearsAmount) {
        this.arrearsAmount = arrearsAmount;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }
}