package rrx.cnuo.service.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import rrx.cnuo.cncommon.utils.StarterToolUtil;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.service.MineService;
import rrx.cnuo.service.service.MsgService;
import rrx.cnuo.service.vo.request.UserBasicInfoVo;
import rrx.cnuo.service.vo.request.UserDetailInfoVo;
import rrx.cnuo.service.vo.request.UserSpouseSelectionVo;
import rrx.cnuo.service.vo.response.BoardTaskVo;
import rrx.cnuo.service.vo.response.UserCreditStatusVo;

/**
 * 当前登录用户操作
 * @author xuhongyu
 * @date 2019年8月24日
 */
@Api("当前登录用户操作相关接口")
@RestController("/mine")
@SuppressWarnings("rawtypes")
public class UserMineController {

	@Autowired private MineService mineService;
	@Autowired private MsgService msgService;
	
	@ApiOperation("上传图片，并返回图片id")
	@PostMapping("/uploadImg")
	public JsonResult<String> uploadImg(@RequestParam @ApiParam(value = "图片表单",required = true) MultipartFile img) throws Exception{
		return mineService.uploadImg(img);
	}
	
	@ApiOperation("获取短信验证码")
	@GetMapping(value = "/verifiCode")
    public JsonResult getVerifiCode(HttpServletRequest request,
    			@RequestParam @ApiParam(value = "手机号",required = true) String telephone,
    			@RequestParam @ApiParam(value = "短信类型：1.绑定手机号 3.余额支付 8.充值",required = true) Byte type) throws Exception {
		String ip = StarterToolUtil.getRemoteIP(request);
		return msgService.updateSendSmsMessage(ip,telephone, type, "");
    }
	
	@ApiOperation("保存用户formid")
	@PutMapping(value = "/formId")
    public JsonResult saveFormId(@RequestParam @ApiParam(value = "微信小程序formId",required = true) String formId) throws Exception {
        return msgService.saveFormId(formId);
    }
	
//	@ApiOperation("获取当前登录用户分享token")
//	@PutMapping(value = "/shareToken")
//	public JsonResult<String> getShareToken() throws Exception {
//		return mineService.getShareToken();
//	}
	
	@ApiOperation("当前登录用户基本信息保存")
	@PostMapping(value = "/basicInfo")
	public JsonResult saveBasicInfo(@RequestParam @ApiParam(value = "用户基本信息参数(每次只保存一项信息)",required = true) UserBasicInfoVo userBasicInfoVo) throws Exception {
		return mineService.saveBasicInfo(userBasicInfoVo);
	}
	
	@ApiOperation("当前登录用户详细信息保存")
	@PostMapping(value = "/detailInfo")
	public JsonResult saveUserDetailInfoVo(@RequestParam @ApiParam(value = "用户详细信息参数(每次只保存一项信息)",required = true) UserDetailInfoVo userDetailInfoVo) throws Exception {
		return mineService.saveUserDetailInfoVo(userDetailInfoVo);
	}
	
	@ApiOperation("当前登录用户择偶条件保存")
	@PostMapping(value = "/spouseSelection")
	public JsonResult saveUserSpouseSelectionVo(@RequestParam @ApiParam(value = "用户择偶条件参数(每次只保存一项信息)",required = true) UserSpouseSelectionVo userSpouseSelectionVo) throws Exception {
		return mineService.saveUserSpouseSelectionVo(userSpouseSelectionVo);
	}
	
	@ApiOperation("获取当前用户完成桃花牌任务的情况")
	@GetMapping(value = "/boardTask")
	public JsonResult<BoardTaskVo> getTask() throws Exception{
		return mineService.getBoardTask();
	}
	
	@ApiOperation("获取当前登录用户各项认证状态")
	@GetMapping(value = "/creditStatus")
	public JsonResult<UserCreditStatusVo> getCreditStatus() throws Exception{
		return mineService.getCreditStatus();
	}
}
