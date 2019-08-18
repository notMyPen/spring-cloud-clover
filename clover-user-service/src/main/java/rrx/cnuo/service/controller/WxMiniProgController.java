package rrx.cnuo.service.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rrx.cnuo.cncommon.accessory.context.UserContextHolder;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.cncommon.vo.UserWxInfoVo;
import rrx.cnuo.service.po.MsgFormId;
import rrx.cnuo.service.service.MsgFormIdService;
import rrx.cnuo.service.service.WxMiniProgService;

/**
 * 微信小程序相关操作
 * @author xuhongyu
 * @date 2019年6月26日
 */
@RestController("/wxMiniProg")
public class WxMiniProgController {

	@Autowired
	private WxMiniProgService wxMiniProgService;
	
	@Autowired
	private MsgFormIdService msgFormIdService;
	
	/**
     * 获取小程序二维码图片
     * @param scene 获取小程序码参数
     * @param pageUrl 小程序码跳转页面
     * @return {
     *     二维码图片
     * }
     */
    @RequestMapping(value = "/getMiniProgramCode", method = RequestMethod.GET)
    public void getMiniProgramCode(@RequestParam String scene, String pageUrl, HttpServletResponse response) throws Exception {
    	wxMiniProgService.getMiniProgramCode(scene, pageUrl, response);
    }
    
    /**
     * 保存用户formid
     * @param{
     *      "formId"  // formid|String|必填
     * 	}
     * @return {
     * 		"status":,  // 状态码|Integer, 200:成功; 201:异常
     * 		"msg":,     // 描述|String
     * 	}
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/saveFormId", method = RequestMethod.POST)
    public JsonResult saveFormId(@RequestBody MsgFormId reqVo) throws Exception {
    	reqVo.setUid(UserContextHolder.currentUser().getUserId());
        return msgFormIdService.saveFormId(reqVo);
    }
    
    /**
     * 微信小程序端，保存用户微信基本信息
     * @param{
     *      "nickName",    // 昵称|String|必填
     *      "avatarUrl"    // 头像|String|必填
     * 	}
     * @return {
     * 		"status":,  // 状态码|Integer, 200:成功; 201:异常
     * 		"msg":,     // 描述|String
     * 	}
     */
    @RequestMapping(value = "/saveMiniBaseInfo", method = RequestMethod.POST)
    public JsonResult<UserWxInfoVo> saveMiniBaseInfo(@RequestBody UserWxInfoVo infoVo) throws Exception {
    	return wxMiniProgService.saveMiniBaseInfo(infoVo);
    }
    
    /**
	 * 获取转发到朋友圈的海报
	 * @author xuhongyu
	 * @param prodId 标的id
	 * @throws Exception
	 */
	@RequestMapping(value = "/genDynamicGraph", produces = {MediaType.IMAGE_JPEG_VALUE})
	public byte[] genDynamicGraph(Long prodId) throws Exception {
		return wxMiniProgService.genDynamicGraph(prodId);
	}
	
	/**
	 * 获取分享给好友的图片
	 * @author xuhongyu
	 * @param prodId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getShareFriendsGraph", produces = {MediaType.IMAGE_JPEG_VALUE})
	public byte[] getShareFriendsGraph(Long prodId) throws Exception {
		return wxMiniProgService.getShareFriendsGraph(prodId);
	}
}
