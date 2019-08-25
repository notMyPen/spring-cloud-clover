package rrx.cnuo.service.vo.request;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import rrx.cnuo.cncommon.vo.PageVo;

@ApiModel("获取首页用户列表参数")
public class BoardQueryParam extends PageVo{

	@ApiModelProperty(value = "性别：0-不限；1-男；2-女")
	private Byte gender;
	
	@ApiModelProperty(value = "学历(多选)：学历：1-博士、2-硕士、3-本科、4-大专、5高中及以下")
	private List<Byte> academicList = new ArrayList<Byte>();
	
	@ApiModelProperty(value = "当前工作生活地省份id")
	private Integer nowProvinceId;
	
	@ApiModelProperty(value = "年龄上限")
	private Integer ageStart;
	
	@ApiModelProperty(value = "年龄下限")
	private Integer ageEnd;
	
	//以下是数据库查询字段，不对用户展示
	private Integer birthdayBgn;
	private Integer birthdayEnd;
	
	@JsonIgnore
	public Integer getBirthdayBgn() {
		return birthdayBgn;
	}
	
	public void setBirthdayBgn(Integer birthdayBgn) {
		this.birthdayBgn = birthdayBgn;
	}
	
	@JsonIgnore
	public Integer getBirthdayEnd() {
		return birthdayEnd;
	}
	
	public void setBirthdayEnd(Integer birthdayEnd) {
		this.birthdayEnd = birthdayEnd;
	}
	
	public Byte getGender() {
		return gender;
	}
	public void setGender(Byte gender) {
		this.gender = gender;
	}
	public List<Byte> getAcademicList() {
		return academicList;
	}
	public void setAcademicList(List<Byte> academicList) {
		this.academicList = academicList;
	}
	public Integer getNowProvinceId() {
		return nowProvinceId;
	}
	public void setNowProvinceId(Integer nowProvinceId) {
		this.nowProvinceId = nowProvinceId;
	}
	public Integer getAgeStart() {
		return ageStart;
	}
	public void setAgeStart(Integer ageStart) {
		this.ageStart = ageStart;
	}
	public Integer getAgeEnd() {
		return ageEnd;
	}
	public void setAgeEnd(Integer ageEnd) {
		this.ageEnd = ageEnd;
	}
	
}
