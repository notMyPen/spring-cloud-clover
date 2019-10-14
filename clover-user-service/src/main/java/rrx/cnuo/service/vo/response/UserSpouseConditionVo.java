package rrx.cnuo.service.vo.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import rrx.cnuo.service.accessory.consts.UserConst;

@ApiModel("择偶条件（用户的理想对象）")
public class UserSpouseConditionVo {

	@ApiModelProperty(value = "对TA的年龄的要求上限：18岁-60岁/0-不限")
	private Byte ageBgn;

	@ApiModelProperty(value = "对TA的年龄的要求下限：18岁-60岁/0-不限")
    private Byte ageEnd;

	@ApiModelProperty(value = "对TA的身高的要求(单位cm)上限：140cm - 200cm /0-不限")
    private Short heightBgn;

	@ApiModelProperty(value = "对TA的身高的要求(单位cm)下限：140cm - 200cm /0-不限")
    private Short heightEnd;

    @ApiModelProperty(value = "对TA的身材的要求")
    private List<String> figureShowList = new ArrayList<String>();
    
    @JsonIgnore
    private String figureList;

    @ApiModelProperty(value = "对TA的故乡的要求")
    private List<String> homeProvinceList = new ArrayList<String>();
    
    @ApiModelProperty(value = "对Ta的工作生活地省份的要求")
    private List<String> workProvinceList = new ArrayList<String>();

    @ApiModelProperty(value = "对Ta的婚姻状态的要求：0-不限；1-未婚；2-离异；3-丧偶")
    private Byte maritalStatus;

    @ApiModelProperty(value = "希望Ta有无子女的要求")
    private List<String> haveChildrenShowList = new ArrayList<String>();
    
    @JsonIgnore
    private String haveChildrenList;

    @ApiModelProperty(value = "对Ta学历要求的要求")
    private List<String> academicShowList = new ArrayList<String>();
    
    @JsonIgnore
    private String academicList;

    @ApiModelProperty(value = "对Ta毕业学校类型的要求")
    private List<String> schoolTypeShowList = new ArrayList<String>();
    
    @JsonIgnore
    private String schoolTypeList;

    @ApiModelProperty(value = "对Ta所在公司类型的要求")
    private List<String> companyTypeShowList = new ArrayList<String>();
    
    @JsonIgnore
    private String companyTypeList;

    @ApiModelProperty(value = "对Ta所在行业类型的要求")
    private List<String> industryTypeShowList = new ArrayList<String>();
    
    @JsonIgnore
    private String industryTypeList;

    @ApiModelProperty(value = "对Ta职场职级的要求")
    private List<String> rankTypeShowList = new ArrayList<String>();
    
    @JsonIgnore
    private String rankTypeList;

    @ApiModelProperty(value = "对Ta年收入的要求")
    private List<String> yearIncomeShowList = new ArrayList<String>();
    
    @JsonIgnore
    private String yearIncomeList;

    @ApiModelProperty(value = "对Ta住房情况的要求")
    private List<String> houseStatusShowList = new ArrayList<String>();
    
    @JsonIgnore
    private String houseStatusList;

    @ApiModelProperty(value = "对Ta购车情况的要求：0-不限；1 - 已购车;2 - 未购车;")
    private Byte carStatus;

    @ApiModelProperty(value = "对Ta资产水平的要求")
    private List<String> assetLevelShowList = new ArrayList<String>();
    
    @JsonIgnore
    private String assetLevelList;

    @ApiModelProperty(value = "对Ta和父母的关系的要求")
    private List<String> relationWithParentsShowList = new ArrayList<String>();
    
    @JsonIgnore
    private String relationWithParentsList;

    @ApiModelProperty(value = "对Ta希望TA是独生子女的要求")
    private List<String> onlyChildShowList = new ArrayList<String>();
    
    @JsonIgnore
    private String onlyChildList;

    @ApiModelProperty(value = "对Ta结婚计划的要求")
    private List<String> marryPlanShowList = new ArrayList<String>();
    
    @JsonIgnore
    private String marryPlanList;

    @ApiModelProperty(value = "Ta对孩子计划的要求")
    private List<String> childPlanShowList = new ArrayList<String>();
    
    @JsonIgnore
    private String childPlanList;

    @ApiModelProperty(value = "Ta对花钱的态度")
    private List<String> consumeAttitudeShowList = new ArrayList<String>();
    
    @JsonIgnore
    private String consumeAttitudeList;

    @ApiModelProperty(value = "对Ta宗教信仰的要求：0-不限、1-无宗教信仰、2-佛教、3-道教、4-基督教、5-伊斯兰教、6-印度教、7-萨满教、8-其他教派")
    private Byte faith;

    @ApiModelProperty(value = "对Ta吸烟喝酒态度的要求：0-不限、1-不吸烟不饮酒、2-不吸烟可以饮酒、3-可以吸烟不要饮酒")
    private Byte smokDrink;

    @ApiModelProperty(value = "对Ta家务活态度的要求：0-不限、1-家务小能手、2-分工合作、3-不太会但愿意为了对方学习")
    private Byte housework;

	public Byte getAgeBgn() {
		return ageBgn;
	}

	public void setAgeBgn(Byte ageBgn) {
		this.ageBgn = ageBgn;
	}

	public Byte getAgeEnd() {
		return ageEnd;
	}

	public void setAgeEnd(Byte ageEnd) {
		this.ageEnd = ageEnd;
	}

	public Short getHeightBgn() {
		return heightBgn;
	}

	public void setHeightBgn(Short heightBgn) {
		this.heightBgn = heightBgn;
	}

	public Short getHeightEnd() {
		return heightEnd;
	}

	public void setHeightEnd(Short heightEnd) {
		this.heightEnd = heightEnd;
	}

	public List<String> getFigureShowList() {
		return figureShowList;
	}

	public void setFigureShowList(List<String> figureShowList) {
		this.figureShowList = figureShowList;
	}

	public String getFigureList() {
		return figureList;
	}

	public void setFigureList(String figureList) {
		this.figureList = figureList;
		if(StringUtils.isNotBlank(figureList)) {
			JSONArray jsonArr = JSON.parseArray(figureList);
			for(int i=0;i<jsonArr.size();i++) {
				this.figureShowList.add(UserConst.Figure.getFigure(jsonArr.getByte(i)).getMessage());
			}
		}
	}

	public List<String> getHomeProvinceList() {
		return homeProvinceList;
	}

	public void setHomeProvinceList(List<String> homeProvinceList) {
		this.homeProvinceList = homeProvinceList;
	}

	public List<String> getWorkProvinceList() {
		return workProvinceList;
	}

	public void setWorkProvinceList(List<String> workProvinceList) {
		this.workProvinceList = workProvinceList;
	}

	public Byte getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(Byte maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public List<String> getHaveChildrenShowList() {
		return haveChildrenShowList;
	}

	public void setHaveChildrenShowList(List<String> haveChildrenShowList) {
		this.haveChildrenShowList = haveChildrenShowList;
	}

	public String getHaveChildrenList() {
		return haveChildrenList;
	}

	public void setHaveChildrenList(String haveChildrenList) {
		this.haveChildrenList = haveChildrenList;
		if(StringUtils.isNotBlank(haveChildrenList)) {
			JSONArray jsonArr = JSON.parseArray(haveChildrenList);
			for(int i=0;i<jsonArr.size();i++) {
				this.haveChildrenShowList.add(UserConst.HaveChildren.getHaveChildren(jsonArr.getByte(i)).getMessage());
			}
		}
	}

	public List<String> getAcademicShowList() {
		return academicShowList;
	}

	public void setAcademicShowList(List<String> academicShowList) {
		this.academicShowList = academicShowList;
	}

	public String getAcademicList() {
		return academicList;
	}

	public void setAcademicList(String academicList) {
		this.academicList = academicList;
		if(StringUtils.isNotBlank(academicList)) {
			JSONArray jsonArr = JSON.parseArray(academicList);
			for(int i=0;i<jsonArr.size();i++) {
				this.academicShowList.add(UserConst.Academic.getAcademic(jsonArr.getByte(i)).getMessage());
			}
		}
	}

	public List<String> getSchoolTypeShowList() {
		return schoolTypeShowList;
	}

	public void setSchoolTypeShowList(List<String> schoolTypeShowList) {
		this.schoolTypeShowList = schoolTypeShowList;
	}

	public String getSchoolTypeList() {
		return schoolTypeList;
	}

	public void setSchoolTypeList(String schoolTypeList) {
		this.schoolTypeList = schoolTypeList;
		if(StringUtils.isNotBlank(schoolTypeList)) {
			JSONArray jsonArr = JSON.parseArray(schoolTypeList);
			for(int i=0;i<jsonArr.size();i++) {
				this.schoolTypeShowList.add(UserConst.SchoolType.getSchoolType(jsonArr.getByte(i)).getMessage());
			}
		}
	}

	public List<String> getCompanyTypeShowList() {
		return companyTypeShowList;
	}

	public void setCompanyTypeShowList(List<String> companyTypeShowList) {
		this.companyTypeShowList = companyTypeShowList;
	}

	public String getCompanyTypeList() {
		return companyTypeList;
	}

	public void setCompanyTypeList(String companyTypeList) {
		this.companyTypeList = companyTypeList;
		if(StringUtils.isNotBlank(companyTypeList)) {
			JSONArray jsonArr = JSON.parseArray(companyTypeList);
			for(int i=0;i<jsonArr.size();i++) {
				this.companyTypeShowList.add(UserConst.CompanyType.getCompanyType(jsonArr.getByte(i)).getMessage());
			}
		}
	}

	public List<String> getIndustryTypeShowList() {
		return industryTypeShowList;
	}

	public void setIndustryTypeShowList(List<String> industryTypeShowList) {
		this.industryTypeShowList = industryTypeShowList;
	}

	public String getIndustryTypeList() {
		return industryTypeList;
	}

	public void setIndustryTypeList(String industryTypeList) {
		this.industryTypeList = industryTypeList;
		if(StringUtils.isNotBlank(industryTypeList)) {
			JSONArray jsonArr = JSON.parseArray(industryTypeList);
			for(int i=0;i<jsonArr.size();i++) {
				this.industryTypeShowList.add(UserConst.IndustryType.getIndustryType(jsonArr.getByte(i)).getMessage());
			}
		}
	}

	public List<String> getRankTypeShowList() {
		return rankTypeShowList;
	}

	public void setRankTypeShowList(List<String> rankTypeShowList) {
		this.rankTypeShowList = rankTypeShowList;
	}

	public String getRankTypeList() {
		return rankTypeList;
	}

	public void setRankTypeList(String rankTypeList) {
		this.rankTypeList = rankTypeList;
		if(StringUtils.isNotBlank(rankTypeList)) {
			JSONArray jsonArr = JSON.parseArray(rankTypeList);
			for(int i=0;i<jsonArr.size();i++) {
				this.rankTypeShowList.add(UserConst.RankType.getRankType(jsonArr.getByte(i)).getMessage());
			}
		}
	}

	public List<String> getYearIncomeShowList() {
		return yearIncomeShowList;
	}

	public void setYearIncomeShowList(List<String> yearIncomeShowList) {
		this.yearIncomeShowList = yearIncomeShowList;
	}

	public String getYearIncomeList() {
		return yearIncomeList;
	}

	public void setYearIncomeList(String yearIncomeList) {
		this.yearIncomeList = yearIncomeList;
		if(StringUtils.isNotBlank(yearIncomeList)) {
			JSONArray jsonArr = JSON.parseArray(yearIncomeList);
			for(int i=0;i<jsonArr.size();i++) {
				this.yearIncomeShowList.add(UserConst.YearIncome.getYearIncome(jsonArr.getByte(i)).getMessage());
			}
		}
	}

	public List<String> getHouseStatusShowList() {
		return houseStatusShowList;
	}

	public void setHouseStatusShowList(List<String> houseStatusShowList) {
		this.houseStatusShowList = houseStatusShowList;
	}

	public String getHouseStatusList() {
		return houseStatusList;
	}

	public void setHouseStatusList(String houseStatusList) {
		this.houseStatusList = houseStatusList;
		if(StringUtils.isNotBlank(houseStatusList)) {
			JSONArray jsonArr = JSON.parseArray(houseStatusList);
			for(int i=0;i<jsonArr.size();i++) {
				this.houseStatusShowList.add(UserConst.HouseStatus.getHouseStatus(jsonArr.getByte(i)).getMessage());
			}
		}
	}

	public Byte getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(Byte carStatus) {
		this.carStatus = carStatus;
	}

	public List<String> getAssetLevelShowList() {
		return assetLevelShowList;
	}

	public void setAssetLevelShowList(List<String> assetLevelShowList) {
		this.assetLevelShowList = assetLevelShowList;
	}

	public String getAssetLevelList() {
		return assetLevelList;
	}

	public void setAssetLevelList(String assetLevelList) {
		this.assetLevelList = assetLevelList;
		if(StringUtils.isNotBlank(assetLevelList)) {
			JSONArray jsonArr = JSON.parseArray(assetLevelList);
			for(int i=0;i<jsonArr.size();i++) {
				this.assetLevelShowList.add(UserConst.AssetLevel.getAssetLevel(jsonArr.getByte(i)).getMessage());
			}
		}
	}

	public List<String> getRelationWithParentsShowList() {
		return relationWithParentsShowList;
	}

	public void setRelationWithParentsShowList(List<String> relationWithParentsShowList) {
		this.relationWithParentsShowList = relationWithParentsShowList;
	}

	public String getRelationWithParentsList() {
		return relationWithParentsList;
	}

	public void setRelationWithParentsList(String relationWithParentsList) {
		this.relationWithParentsList = relationWithParentsList;
		if(StringUtils.isNotBlank(relationWithParentsList)) {
			JSONArray jsonArr = JSON.parseArray(relationWithParentsList);
			for(int i=0;i<jsonArr.size();i++) {
				this.relationWithParentsShowList.add(UserConst.RelationWithParents.getRelationWithParents(jsonArr.getByte(i)).getMessage());
			}
		}
	}

	public List<String> getOnlyChildShowList() {
		return onlyChildShowList;
	}

	public void setOnlyChildShowList(List<String> onlyChildShowList) {
		this.onlyChildShowList = onlyChildShowList;
	}

	public String getOnlyChildList() {
		return onlyChildList;
	}

	public void setOnlyChildList(String onlyChildList) {
		this.onlyChildList = onlyChildList;
		if(StringUtils.isNotBlank(onlyChildList)) {
			JSONArray jsonArr = JSON.parseArray(onlyChildList);
			for(int i=0;i<jsonArr.size();i++) {
				this.onlyChildShowList.add(UserConst.OnlyChild.getOnlyChild(jsonArr.getByte(i)).getMessage());
			}
		}
	}

	public List<String> getMarryPlanShowList() {
		return marryPlanShowList;
	}

	public void setMarryPlanShowList(List<String> marryPlanShowList) {
		this.marryPlanShowList = marryPlanShowList;
	}

	public String getMarryPlanList() {
		return marryPlanList;
	}

	public void setMarryPlanList(String marryPlanList) {
		this.marryPlanList = marryPlanList;
		if(StringUtils.isNotBlank(marryPlanList)) {
			JSONArray jsonArr = JSON.parseArray(marryPlanList);
			for(int i=0;i<jsonArr.size();i++) {
				this.marryPlanShowList.add(UserConst.MarryPlan.getMarryPlan(jsonArr.getByte(i)).getMessage());
			}
		}
	}

	public List<String> getChildPlanShowList() {
		return childPlanShowList;
	}

	public void setChildPlanShowList(List<String> childPlanShowList) {
		this.childPlanShowList = childPlanShowList;
	}

	public String getChildPlanList() {
		return childPlanList;
	}

	public void setChildPlanList(String childPlanList) {
		this.childPlanList = childPlanList;
		if(StringUtils.isNotBlank(childPlanList)) {
			JSONArray jsonArr = JSON.parseArray(childPlanList);
			for(int i=0;i<jsonArr.size();i++) {
				this.childPlanShowList.add(UserConst.ChildPlan.getChildPlan(jsonArr.getByte(i)).getMessage());
			}
		}
	}

	public List<String> getConsumeAttitudeShowList() {
		return consumeAttitudeShowList;
	}

	public void setConsumeAttitudeShowList(List<String> consumeAttitudeShowList) {
		this.consumeAttitudeShowList = consumeAttitudeShowList;
	}

	public String getConsumeAttitudeList() {
		return consumeAttitudeList;
	}

	public void setConsumeAttitudeList(String consumeAttitudeList) {
		this.consumeAttitudeList = consumeAttitudeList;
		if(StringUtils.isNotBlank(consumeAttitudeList)) {
			JSONArray jsonArr = JSON.parseArray(consumeAttitudeList);
			for(int i=0;i<jsonArr.size();i++) {
				this.consumeAttitudeShowList.add(UserConst.ConsumeAttitude.getConsumeAttitude(jsonArr.getByte(i)).getMessage());
			}
		}
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
    
}
