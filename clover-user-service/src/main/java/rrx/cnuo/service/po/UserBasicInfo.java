package rrx.cnuo.service.po;

import java.util.Date;

public class UserBasicInfo {
    private Long uid;

    private String idCardNo;

    private Byte gender;

    private String wxAccount;

    private Integer birthday;

    private Byte zodiac;

    private Byte constellation;

    private Short height;

    private String figureList;

    private Integer homeProvinceId;

    private Integer homeCityId;

    private String homeProvinceCity;

    private Integer nowProvinceId;

    private Integer nowCityId;

    private String nowProvinceCity;

    private Byte maritalStatus;

    private Byte haveChildren;

    private String characterList;

    private Byte academic;

    private Integer collegeId;

    private String college;

    private Byte schoolType;

    private Byte yearIncome;

    private Byte houseStatus;

    private Byte carStatus;

    private Byte assetLevel;

    private Date createTime;

    private Date updateTime;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo == null ? null : idCardNo.trim();
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getWxAccount() {
        return wxAccount;
    }

    public void setWxAccount(String wxAccount) {
        this.wxAccount = wxAccount == null ? null : wxAccount.trim();
    }

    public Integer getBirthday() {
        return birthday;
    }

    public void setBirthday(Integer birthday) {
        this.birthday = birthday;
    }

    public Byte getZodiac() {
        return zodiac;
    }

    public void setZodiac(Byte zodiac) {
        this.zodiac = zodiac;
    }

    public Byte getConstellation() {
        return constellation;
    }

    public void setConstellation(Byte constellation) {
        this.constellation = constellation;
    }

    public Short getHeight() {
        return height;
    }

    public void setHeight(Short height) {
        this.height = height;
    }

    public String getFigureList() {
        return figureList;
    }

    public void setFigureList(String figureList) {
        this.figureList = figureList == null ? null : figureList.trim();
    }

    public Integer getHomeProvinceId() {
        return homeProvinceId;
    }

    public void setHomeProvinceId(Integer homeProvinceId) {
        this.homeProvinceId = homeProvinceId;
    }

    public Integer getHomeCityId() {
        return homeCityId;
    }

    public void setHomeCityId(Integer homeCityId) {
        this.homeCityId = homeCityId;
    }

    public String getHomeProvinceCity() {
        return homeProvinceCity;
    }

    public void setHomeProvinceCity(String homeProvinceCity) {
        this.homeProvinceCity = homeProvinceCity == null ? null : homeProvinceCity.trim();
    }

    public Integer getNowProvinceId() {
        return nowProvinceId;
    }

    public void setNowProvinceId(Integer nowProvinceId) {
        this.nowProvinceId = nowProvinceId;
    }

    public Integer getNowCityId() {
        return nowCityId;
    }

    public void setNowCityId(Integer nowCityId) {
        this.nowCityId = nowCityId;
    }

    public String getNowProvinceCity() {
        return nowProvinceCity;
    }

    public void setNowProvinceCity(String nowProvinceCity) {
        this.nowProvinceCity = nowProvinceCity == null ? null : nowProvinceCity.trim();
    }

    public Byte getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Byte maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Byte getHaveChildren() {
        return haveChildren;
    }

    public void setHaveChildren(Byte haveChildren) {
        this.haveChildren = haveChildren;
    }

    public String getCharacterList() {
        return characterList;
    }

    public void setCharacterList(String characterList) {
        this.characterList = characterList == null ? null : characterList.trim();
    }

    public Byte getAcademic() {
        return academic;
    }

    public void setAcademic(Byte academic) {
        this.academic = academic;
    }

    public Integer getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college == null ? null : college.trim();
    }

    public Byte getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(Byte schoolType) {
        this.schoolType = schoolType;
    }

    public Byte getYearIncome() {
        return yearIncome;
    }

    public void setYearIncome(Byte yearIncome) {
        this.yearIncome = yearIncome;
    }

    public Byte getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(Byte houseStatus) {
        this.houseStatus = houseStatus;
    }

    public Byte getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(Byte carStatus) {
        this.carStatus = carStatus;
    }

    public Byte getAssetLevel() {
        return assetLevel;
    }

    public void setAssetLevel(Byte assetLevel) {
        this.assetLevel = assetLevel;
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