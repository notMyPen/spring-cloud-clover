package rrx.cnuo.service.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rrx.cnuo.service.service.WxMiniProgService;

/**
 * 微信小程序相关操作
 * @author xuhongyu
 * @date 2019年6月26日
 */
@RestController
@RequestMapping("/wxMiniProg")
public class WxMiniProgController {

	@Autowired
	private WxMiniProgService wxMiniProgService;
	
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
