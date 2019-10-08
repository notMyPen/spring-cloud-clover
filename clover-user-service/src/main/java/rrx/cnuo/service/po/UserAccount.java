package rrx.cnuo.service.po;

import java.util.Date;

public class UserAccount {
    private Long uid;

    private String paySalt;

    private String payPassword;

    private Integer balance;

    private Integer withdrawBalance;

    private Boolean openAccount;
    private Boolean registCredit;

    private Date createTime;

    private Date updateTime;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getPaySalt() {
        return paySalt;
    }

    public void setPaySalt(String paySalt) {
        this.paySalt = paySalt == null ? null : paySalt.trim();
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword == null ? null : payPassword.trim();
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getWithdrawBalance() {
        return withdrawBalance;
    }

    public void setWithdrawBalance(Integer withdrawBalance) {
        this.withdrawBalance = withdrawBalance;
    }

    public Boolean getOpenAccount() {
        return openAccount;
    }

    public void setOpenAccount(Boolean openAccount) {
        this.openAccount = openAccount;
    }
    
    public Boolean getRegistCredit() {
		return registCredit;
	}

	public void setRegistCredit(Boolean registCredit) {
		this.registCredit = registCredit;
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