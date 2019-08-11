package rrx.cnuo.service.weixin.model;

/**
 * 长链接转短链接（返回值）
 * Title: Long2Short.java
 * Description
 * @author 
 * @date 2015年7月26日
 * @version 1.0
 */
public class Long2Short {

	private String errcode;//错误码。 
	private String errmsg;//错误信息。 
	private String short_url;//短链接。 
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getShort_url() {
		return short_url;
	}
	public void setShort_url(String short_url) {
		this.short_url = short_url;
	}
	
}
