package rrx.cnuo.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.service.PassportService;
import rrx.cnuo.service.vo.passport.request.OauthParam;
import rrx.cnuo.service.vo.passport.response.UserInitOauthVo;

@Api("用户通行证相关接口")
@RestController("/passport")
public class PassportController {

	@Autowired
	private PassportService passportService;
	
	@ApiOperation("小程序授权后登录")
	@PostMapping(value = "/oauth")
	public JsonResult<UserInitOauthVo> oauth(@RequestBody @ApiParam(value = "登录授权参数vo", required = true) OauthParam oauthParam) throws Exception {
		JsonResult<UserInitOauthVo> result = passportService.updateOauth(oauthParam);
		if(result.isOk()) {
			passportService.updateAvatarUrlToAliOss(oauthParam.getRawData(),result.getData().getUid());
		}
		return result;
	}
}
