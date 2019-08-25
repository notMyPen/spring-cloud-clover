package rrx.cnuo.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import rrx.cnuo.cncommon.vo.DataGridResult;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.service.UserInfoService;
import rrx.cnuo.service.vo.request.BoardQueryParam;
import rrx.cnuo.service.vo.response.BoardBasicInfoVo;

@Api("用户信息相关接口")
@RestController("/info")
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;
	
	@ApiOperation("获取首页用户列表")
	@GetMapping(value = "/boardList")
    public JsonResult<DataGridResult<BoardBasicInfoVo>> getBoardList(@RequestParam @ApiParam(value = "首页用户列表分页查询参数") BoardQueryParam param) throws Exception {
		return userInfoService.getBoardList(param);
    }
}
