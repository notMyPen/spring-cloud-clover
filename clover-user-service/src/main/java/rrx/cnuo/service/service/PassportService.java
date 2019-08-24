package rrx.cnuo.service.service;

import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.vo.passport.request.OauthParam;
import rrx.cnuo.service.vo.passport.response.UserInitOauthVo;

public interface PassportService {

	/**
	 * 微信小程序符授权后登录
	 * @author xuhongyu
	 * @param oauthParam
	 * @return
	 */
	JsonResult<UserInitOauthVo> updateOauth(OauthParam oauthParam) throws Exception;

	/**
	 * 将用户的微信头像上传到alioss中并存储key
	 * @author xuhongyu
	 * @param rawData
	 */
	void updateAvatarUrlToAliOss(String rawData,Long uid) throws Exception;
}
