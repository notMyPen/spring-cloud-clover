package rrx.cnuo.service.weixin.model;

import java.io.Serializable;

/**
 * 获取用户基本信息（包括UnionID机制）
 * Title: AccessTokenBean.java
 * Description
 * @author 
 * @date 2015年7月21日
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AccessTokenBean implements Serializable {
	private String access_token;//获取到的access_token
	private int expires_in;//过期时间

	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
}