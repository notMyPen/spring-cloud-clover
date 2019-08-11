package rrx.cnuo.service.weixin.model;

import java.util.List;

/**
 * 批量获取用户基本信息
 * Title: WeiXinUserinfoList.java
 * Description
 * @author 
 * @date 2015年7月26日
 * @version 1.0
 */
public class WeiXinUserinfoList {

	private List<WeiXinUserinfo> user_info_list;

	public List<WeiXinUserinfo> getUser_info_list() {
		return user_info_list;
	}

	public void setUser_info_list(List<WeiXinUserinfo> user_info_list) {
		this.user_info_list = user_info_list;
	}
	
}
