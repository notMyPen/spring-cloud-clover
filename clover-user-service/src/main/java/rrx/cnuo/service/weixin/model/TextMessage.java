package rrx.cnuo.service.weixin.model;

/**
 * 微信文本消息封装类
 * @author xuhongyu
 * @date 2019年3月7日
 */
public class TextMessage extends Message{
	
	/**
	 * 文本消息内容
	 */
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String Content) {
		this.Content = Content;
	}
	
}
