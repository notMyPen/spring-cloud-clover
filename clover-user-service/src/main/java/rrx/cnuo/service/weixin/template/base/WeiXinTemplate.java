package rrx.cnuo.service.weixin.template.base;



/**
 * 微信模板
 * Title: WeiXinMessageTemplate.java
 * Description
 * @author 
 * @date 2015年7月23日
 * @version 1.0
 */
public class WeiXinTemplate {

	private String touser;//收方帐号（收到的OpenID） 
	private String template_id;//模板ID
	private String url;//消息链接 
	private String topcolor="#FF0000";//顶部颜色
	private Object data;//内容
	
	/**
	 * @param touser 收方帐号（收到的OpenID） 
	 * @param url 消息链接 
	 */
	public WeiXinTemplate(String touser, String url) {
		this.touser = touser;
		this.url = url;
	}
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTopcolor() {
		return topcolor;
	}
	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}
