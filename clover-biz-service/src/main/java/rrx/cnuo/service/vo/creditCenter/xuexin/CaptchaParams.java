package rrx.cnuo.service.vo.creditCenter.xuexin;

import rrx.cnuo.service.vo.creditCenter.UnderlineBasicParam;

/**
 * 数据魔盒提交登录验证码
 * @author xuhongyu
 * @date 2019年5月31日
 */
public class CaptchaParams extends UnderlineBasicParam{

	private String captcha2;//学信图形验证码
	private String captcha1;//学信图形验证码
	
	public String getCaptcha2() {
		return captcha2;
	}
	public void setCaptcha2(String captcha2) {
		this.captcha2 = captcha2;
	}
	public String getCaptcha1() {
		return captcha1;
	}
	public void setCaptcha1(String captcha1) {
		this.captcha1 = captcha1;
	}
	
}
