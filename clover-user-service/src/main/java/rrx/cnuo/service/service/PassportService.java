package rrx.cnuo.service.service;

import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.UserInitOauthVo;

public interface PassportService {

	/**
	 * 登录初始化:根据微信code获取openid，如果openid未注册过注册，返回微信用户信息
	 * @author xuhongyu
	 * @param code 微信code
	 * @param platform 平台 类型：1-微信公众号 2-微信小程序
	 * @return
	 */
	JsonResult<UserInitOauthVo> updateInit(String code,Byte platform) throws Exception;
}
