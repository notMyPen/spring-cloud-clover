package rrx.cnuo.cncommon.vo.config;

/**
 * 微信小程序相关配置
 * @author xuhongyu
 * @date 2019年7月8日
 */
public class WeChatMiniConfig {

	private String miniAppId;
    private String miniAppSecret;
    private String miniTemplateAuditNotifyId; //小程序审核通知消息模板id
    private String miniTemplateTaskHandleNotifyId; //小程序业务处理消息模板id
    private String pageUrl;
    
	public String getMiniAppId() {
		return miniAppId;
	}
	public void setMiniAppId(String miniAppId) {
		this.miniAppId = miniAppId;
	}
	public String getMiniAppSecret() {
		return miniAppSecret;
	}
	public void setMiniAppSecret(String miniAppSecret) {
		this.miniAppSecret = miniAppSecret;
	}
	public String getMiniTemplateAuditNotifyId() {
		return miniTemplateAuditNotifyId;
	}
	public void setMiniTemplateAuditNotifyId(String miniTemplateAuditNotifyId) {
		this.miniTemplateAuditNotifyId = miniTemplateAuditNotifyId;
	}
	public String getMiniTemplateTaskHandleNotifyId() {
		return miniTemplateTaskHandleNotifyId;
	}
	public void setMiniTemplateTaskHandleNotifyId(String miniTemplateTaskHandleNotifyId) {
		this.miniTemplateTaskHandleNotifyId = miniTemplateTaskHandleNotifyId;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
    
}
