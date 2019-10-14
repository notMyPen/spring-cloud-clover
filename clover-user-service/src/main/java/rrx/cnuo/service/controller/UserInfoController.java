package rrx.cnuo.service.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import rrx.cnuo.cncommon.accessory.context.UserContextHolder;
import rrx.cnuo.cncommon.vo.DataGridResult;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.service.UserInfoService;
import rrx.cnuo.service.vo.request.BoardQueryParam;
import rrx.cnuo.service.vo.response.BoardBasicInfoPart1Vo;
import rrx.cnuo.service.vo.response.BoardDetailVo;

@Api("用户信息相关接口")
@RestController
@RequestMapping("/info")
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;
	
	@ApiOperation("获取首页用户列表")
	@GetMapping(value = "/boardList")
    public JsonResult<DataGridResult<BoardBasicInfoPart1Vo>> getBoardList(@RequestParam @ApiParam(value = "首页用户列表分页查询参数") BoardQueryParam param) throws Exception {
		return userInfoService.getBoardList(param);
    }
	
	@ApiOperation("用户个人详情")
	@GetMapping(value = "/detail")
	public JsonResult<BoardDetailVo> getDetail(@RequestParam(required = false) @ApiParam(value = "用户uid") String uid) throws Exception {
		Long userId = null;
		if(StringUtils.isNotBlank(uid)) {
			userId = Long.parseLong(uid);
		}else {
			userId = UserContextHolder.currentUser().getUserId();
		}
		return userInfoService.getDetail(userId);
	}
}
