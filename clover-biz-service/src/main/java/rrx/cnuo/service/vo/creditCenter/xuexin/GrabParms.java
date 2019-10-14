package rrx.cnuo.service.vo.creditCenter.xuexin;

import rrx.cnuo.service.vo.creditCenter.HumpBasicParam;

/*******************************************************************************
 * Copyright 2018 agilestar, Inc. All Rights Reserved
 * agileCloud
 * credit.vo.parmVo
 * Created by bob on 18-7-3.
 * Description: 京东/学信数据抓取 参数
 *******************************************************************************/
public class GrabParms extends HumpBasicParam{

	private String telephone;  // 手机号String
	private String school; // 学校名称
    private String username; // 登录用户名
    private String password; // 登录密码
    private String reportId;    // 报告id
    private String idCard;      // 身份证号
    private String captcha;     // 验证码
    private Boolean underSpecial;    // 是否为专科  ture 专科
    
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	public Boolean getUnderSpecial() {
		return underSpecial;
	}
	public void setUnderSpecial(Boolean underSpecial) {
		this.underSpecial = underSpecial;
	}
}
