package rrx.cnuo.cncommon.vo.config;

/**
 * 微信公众号相关配置
 * @author xuhongyu
 * @date 2019年7月8日
 */
public class WeChatAppConfig {

	private String wechatAppid;
    private String wechatAppsecret;
    private String wechatToken;
    private String wechatMsgToken;
    
	public String getWechatAppid() {
		return wechatAppid;
	}
	public void setWechatAppid(String wechatAppid) {
		this.wechatAppid = wechatAppid;
	}
	public String getWechatAppsecret() {
		return wechatAppsecret;
	}
	public void setWechatAppsecret(String wechatAppsecret) {
		this.wechatAppsecret = wechatAppsecret;
	}
	public String getWechatToken() {
		return wechatToken;
	}
	public void setWechatToken(String wechatToken) {
		this.wechatToken = wechatToken;
	}
	public String getWechatMsgToken() {
		return wechatMsgToken;
	}
	public void setWechatMsgToken(String wechatMsgToken) {
		this.wechatMsgToken = wechatMsgToken;
	}
    
}
