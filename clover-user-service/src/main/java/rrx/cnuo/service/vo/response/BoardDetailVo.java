package rrx.cnuo.service.vo.response;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户详情")
public class BoardDetailVo extends BoardBasicInfoPart1Vo {

	@ApiModelProperty("用户图片列表")
	private List<UserPicVo> picList = new ArrayList<UserPicVo>();
	
	@ApiModelProperty("喜欢我的用户数")
	private Integer likedUserCnt;
	
	@ApiModelProperty("学校")
	private String college;
	
	@ApiModelProperty("高发失信认证结果：0-未认证(无结果)；1-无失信记录(安全)；2-有失信记录")
	private Byte dishonestCreditResult;
	
	@ApiModelProperty("多头借贷认证结果：0-未认证(无结果)；1-无借贷记录；2-有借贷记录")
	private Byte duotouLendResult;
	
	@ApiModelProperty("用户基本信息")
	private BoardBasicInfoPart2Vo userBasicInfo;
	
	@ApiModelProperty("用户择偶条件")
	private UserSpouseConditionVo spouseCondition;
	
	@ApiModelProperty("用户履历")
	private UserResume userResume;
	
	@ApiModelProperty("用户的家庭情况")
	private UserFamilySituation userFamilySituation;
	
	@ApiModelProperty("用户的兴趣")
	private UserHobby userHobby;
	
	@ApiModelProperty("用户的观念")
	private UserConcept userConcept;
	
	@ApiModelProperty("用户的生活方式")
	private LifeStyle lifeStyle;

	public List<UserPicVo> getPicList() {
		return picList;
	}

	public void setPicList(List<UserPicVo> picList) {
		this.picList = picList;
	}

	public Integer getLikedUserCnt() {
		return likedUserCnt;
	}

	public void setLikedUserCnt(Integer likedUserCnt) {
		this.likedUserCnt = likedUserCnt;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
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

	public BoardBasicInfoPart2Vo getUserBasicInfo() {
		return userBasicInfo;
	}

	public void setUserBasicInfo(BoardBasicInfoPart2Vo userBasicInfo) {
		this.userBasicInfo = userBasicInfo;
	}

	public UserSpouseConditionVo getSpouseCondition() {
		return spouseCondition;
	}

	public void setSpouseCondition(UserSpouseConditionVo spouseCondition) {
		this.spouseCondition = spouseCondition;
	}

	public UserResume getUserResume() {
		return userResume;
	}

	public void setUserResume(UserResume userResume) {
		this.userResume = userResume;
	}

	public UserFamilySituation getUserFamilySituation() {
		return userFamilySituation;
	}

	public void setUserFamilySituation(UserFamilySituation userFamilySituation) {
		this.userFamilySituation = userFamilySituation;
	}

	public UserHobby getUserHobby() {
		return userHobby;
	}

	public void setUserHobby(UserHobby userHobby) {
		this.userHobby = userHobby;
	}

	public UserConcept getUserConcept() {
		return userConcept;
	}

	public void setUserConcept(UserConcept userConcept) {
		this.userConcept = userConcept;
	}

	public LifeStyle getLifeStyle() {
		return lifeStyle;
	}

	public void setLifeStyle(LifeStyle lifeStyle) {
		this.lifeStyle = lifeStyle;
	}
	
}
