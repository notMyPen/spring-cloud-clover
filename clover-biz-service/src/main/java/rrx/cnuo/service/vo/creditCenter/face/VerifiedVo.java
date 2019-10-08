package rrx.cnuo.service.vo.creditCenter.face;

import rrx.cnuo.service.vo.creditCenter.UnderlineBasicParam;

/**
 * 实名认证相关参数
 * @author xuhongyu
 * @date 2019年5月8日
 */
public class VerifiedVo extends UnderlineBasicParam{

	private boolean is_weixin ;
	private String idCardNo;
	private String userName;
	
	public boolean isIs_weixin() {
		return is_weixin;
	}
	public void setIs_weixin(boolean is_weixin) {
		this.is_weixin = is_weixin;
	}
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
