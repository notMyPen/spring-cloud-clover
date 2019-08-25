package rrx.cnuo.service.po;

import java.util.Date;

public class UserCreditStatus {
    private Long uid;

    private Byte idcardVerifyStatus;

    private Byte faceVerifyStatus;

    private Byte xuexinCreditStatus;

    private Byte shebaoCreditStatus;

    private Byte gjjCreditStatus;

    private Byte duotouLendStatus;

    private Byte dishonestCreditStatus;

    private Byte marryStatus;

//    private Byte taobaoCreditStatus;
//
//    private Byte alipayCreditStatus;
//
//    private Byte jingdongCreditStatus;
//
//    private Byte zhengxinCreditStatus;
//
//    private Byte zhimaStatus;
//
//    private Integer zhimaMonth;

    private Byte houseCreditStatus;

    private Byte carCreditStatus;

    private Byte incomeCreditStatus;

    private Date createTime;

    private Date updateTime;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Byte getIdcardVerifyStatus() {
        return idcardVerifyStatus;
    }

    public void setIdcardVerifyStatus(Byte idcardVerifyStatus) {
        this.idcardVerifyStatus = idcardVerifyStatus;
    }

    public Byte getFaceVerifyStatus() {
        return faceVerifyStatus;
    }

    public void setFaceVerifyStatus(Byte faceVerifyStatus) {
        this.faceVerifyStatus = faceVerifyStatus;
    }

    public Byte getXuexinCreditStatus() {
        return xuexinCreditStatus;
    }

    public void setXuexinCreditStatus(Byte xuexinCreditStatus) {
        this.xuexinCreditStatus = xuexinCreditStatus;
    }

    public Byte getShebaoCreditStatus() {
        return shebaoCreditStatus;
    }

    public void setShebaoCreditStatus(Byte shebaoCreditStatus) {
        this.shebaoCreditStatus = shebaoCreditStatus;
    }

    public Byte getGjjCreditStatus() {
        return gjjCreditStatus;
    }

    public void setGjjCreditStatus(Byte gjjCreditStatus) {
        this.gjjCreditStatus = gjjCreditStatus;
    }

    public Byte getDuotouLendStatus() {
        return duotouLendStatus;
    }

    public void setDuotouLendStatus(Byte duotouLendStatus) {
        this.duotouLendStatus = duotouLendStatus;
    }

    public Byte getDishonestCreditStatus() {
        return dishonestCreditStatus;
    }

    public void setDishonestCreditStatus(Byte dishonestCreditStatus) {
        this.dishonestCreditStatus = dishonestCreditStatus;
    }

    public Byte getMarryStatus() {
        return marryStatus;
    }

    public void setMarryStatus(Byte marryStatus) {
        this.marryStatus = marryStatus;
    }

    public Byte getHouseCreditStatus() {
        return houseCreditStatus;
    }

    public void setHouseCreditStatus(Byte houseCreditStatus) {
        this.houseCreditStatus = houseCreditStatus;
    }

    public Byte getCarCreditStatus() {
        return carCreditStatus;
    }

    public void setCarCreditStatus(Byte carCreditStatus) {
        this.carCreditStatus = carCreditStatus;
    }

    public Byte getIncomeCreditStatus() {
        return incomeCreditStatus;
    }

    public void setIncomeCreditStatus(Byte incomeCreditStatus) {
        this.incomeCreditStatus = incomeCreditStatus;
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