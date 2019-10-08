package rrx.cnuo.service.vo.creditCenter.face;

import rrx.cnuo.service.vo.creditCenter.UnderlineBasicParam;

public class CheckNumParam extends UnderlineBasicParam{

	private String idCardNo;
	private String userName;
	
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
