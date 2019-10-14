package rrx.cnuo.service.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import rrx.cnuo.cncommon.util.DateUtil;
import rrx.cnuo.service.accessory.consts.UserConst;

@ApiModel(description = "首页用户列表信息")
public class BoardBasicInfoPart1Vo extends UserCreditStatusVo{

	@ApiModelProperty(value = "用户昵称")
	private String nickName;
	
	@ApiModelProperty(value = "用户主照片url")
	private String avatarUrl;
	
	@ApiModelProperty(value = "信用认证是否全部通过：0-否 1-是")
	private Byte creditPass;
	
	@ApiModelProperty(value = "个人资料完成度(%)")
	private Integer personalDataIntegrity;
	
	@ApiModelProperty(value = "生日(yyyy-MM-dd)")
	private String birthdayShow;
	
	@JsonIgnore
	private Integer birthday;
	
	@JsonIgnore
	private Byte constellation;
	
	@ApiModelProperty(value = "星座：0-未知；按顺序1-12依次表示白羊座、金牛座、双子座、巨蟹座、狮子座、处女座、天秤座、天蝎座、射手座、摩羯座、水瓶座、双鱼座")
	private String constellationShow;
	
	@ApiModelProperty(value = "生日(yyyy-MM-dd)")
	private Short height;
	
	@ApiModelProperty(value = "年收入：0-未知；1-10W以下、2-10W-20W、3-20W-30W、4-30W-50W、5-50W-100W、6-100W以上")
	private Byte yearIncome;
	
	@ApiModelProperty(value = "住房情况：0-空；1-和家人同住、2-已购房有贷款、3-已购房无贷款、4-租房、5-打算婚后买房、6-住单位宿舍")
	private Byte houseStatus;
	
	@ApiModelProperty(value = "购车情况：0-空；1 - 已购车;2 - 未购车;")
	private Byte carStatus;
	
	@ApiModelProperty(value = "资产水平：0-未知、1-所在城市基本生活水平、2-所在城市小康生活水平、3-所在城市家境优渥")
	private Byte assetLevel;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public Byte getCreditPass() {
		return creditPass;
	}

	public void setCreditPass(Byte creditPass) {
		this.creditPass = creditPass;
	}

	public Integer getPersonalDataIntegrity() {
		return personalDataIntegrity;
	}

	public void setPersonalDataIntegrity(Integer personalDataIntegrity) {
		this.personalDataIntegrity = personalDataIntegrity;
	}

	public String getBirthdayShow() {
		return birthdayShow;
	}

	public void setBirthdayShow(String birthdayShow) {
		this.birthdayShow = birthdayShow;
	}

	public Integer getBirthday() {
		return birthday;
	}

	public void setBirthday(Integer birthday) throws Exception {
		this.birthday = birthday;
		if(birthday != null && birthday != 0) {
			this.birthdayShow = DateUtil.format(DateUtil.getDate(birthday), DateUtil.DATE_YYYY_MM_DD);
		}
	}

	public Byte getConstellation() {
		return constellation;
	}

	public void setConstellation(Byte constellation) {
		this.constellation = constellation;
		if(constellation != null) {
			this.constellationShow = UserConst.Constellation.getConstellation(constellation).getMessage();
		}
	}
	
	public String getConstellationShow() {
		return constellationShow;
	}

	public void setConstellationShow(String constellationShow) {
		this.constellationShow = constellationShow;
	}

	public Short getHeight() {
		return height;
	}

	public void setHeight(Short height) {
		this.height = height;
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
	
}
