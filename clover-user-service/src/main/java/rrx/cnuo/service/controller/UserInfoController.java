package rrx.cnuo.service.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.cncommon.utils.StarterToolUtil;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.service.MsgService;
import rrx.cnuo.service.service.UserService;
import rrx.cnuo.service.vo.msgcenter.ReturnSmsMassegeVo;

@RestController
public class UserInfoController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private MsgService msgService;
	
    /**
	 * 上传图片
	 * @param img //图片表单
	 * @return{
		    "status": 200,
		    "msg": ""
		    "data":{key:"" //七牛返回的id}
	   	}
	 * @throws Exception 
	 */
	/*@RequestMapping(value = "/uploadImgQiniu_", method = RequestMethod.POST)
	public JsonResult<JSONObject> uploadImgQiniu(MultipartFile img) throws Exception {
		return userService.uploadImgQiniu(img);
	}*/
	
	/**
	 * 上传图片
	 * @param img //图片表单
	 * @return{
		    "status": 200,
		    "msg": ""
		    "data":{key:"" //返回的id}
	   	}
	 * @throws Exception 
	 */
	@RequestMapping(value = "/uploadImgQiniu", method = RequestMethod.POST)
	public JsonResult<JSONObject> uploadImgAliOss(MultipartFile img) throws Exception {
		return userService.uploadImgAliOss(img);
	}
	
	/**
	 * 获取短信验证码
	 * @author xuhongyu
	 * @param uid
	 * @param telephone
	 * @param type 短信类型：1-用于申请贷款
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getVerifiCode", method = RequestMethod.GET)
    public JsonResult<ReturnSmsMassegeVo> getVerifiCode(HttpServletRequest request,@RequestParam String telephone,@RequestParam Byte type) throws Exception {
		String ip = StarterToolUtil.getRemoteIP(request);
		return msgService.updateSendSmsMessage(ip,telephone, type, "");
    }
	
}
