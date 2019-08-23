package rrx.cnuo.service.service;

import javax.servlet.http.HttpServletResponse;

public interface WxMiniProgService {

	/**
	 * 获取小程序二维码图片
	 * @author xuhongyu
	 * @param scene
	 * @param page
	 * @param response
	 * @throws Exception
	 */
	void getMiniProgramCode(String scene, String pageUrl, HttpServletResponse response) throws Exception;

	/**
	 * 获取分享给好友的图片
	 * @author xuhongyu
	 * @param prodId
	 * @return
	 * @throws Exception
	 */
	byte[] getShareFriendsGraph(Long prodId) throws Exception;
	
	/**
	 * 获取转发到朋友圈的海报
	 * @author xuhongyu
	 * @param prodId
	 * @return
	 */
	byte[] genDynamicGraph(Long prodId) throws Exception;
}
