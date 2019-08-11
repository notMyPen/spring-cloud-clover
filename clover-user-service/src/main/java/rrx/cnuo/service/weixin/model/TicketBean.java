package rrx.cnuo.service.weixin.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 获取用户基本信息（包括UnionID机制）
 * Title: TicketBean.java
 * Description
 * @author 
 * @date 2015年7月21日
 * @version 1.0
 */
@SuppressWarnings("serial")
public class TicketBean implements Serializable {
	@JsonProperty
	private String errcode;
	@JsonProperty
	private String errmsg;
	@JsonProperty
	private String ticket;
	@JsonProperty
	private int expires_in;// 过期时间
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
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
}