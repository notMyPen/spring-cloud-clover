package rrx.cnuo.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.UserInitOauthVo;
import rrx.cnuo.service.service.PassportService;

@RestController("/passport")
public class PassportController {

	@Autowired
	private PassportService passportService;
	
	/**
     * 微信平台登录初始化:如果第一次使用，创建用户并保存openId
     * @param{
     *      "code",    // 微信返回的code码|String|必填
     *      "platform",    // 平台 类型：1-微信公众号 2-微信小程序|Number|必填
     * 	}
     * @return {
     * 		"status":,  // 状态码|Integer, 200:成功; 201:异常
     * 		"msg":,     // 描述|String
     *      "data": {
     *          "uid",    // 用户id|Long
     *          "openId", // 用户openid|String
     *      }
     * 	}
     */
	@RequestMapping(value = "/init", method = RequestMethod.GET)
    public JsonResult<UserInitOauthVo> init(@RequestParam String code,@RequestParam Byte platform) throws Exception {
        return passportService.updateInit(code,platform);
    }
}
