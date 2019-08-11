package rrx.cnuo.service.weixin.model;

/**
 * 微信消息基类
 * 具体参考：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140453
 * @author xuhongyu
 * @date 2019年3月7日
 */
public class Message {
	
	/**
	 * 开发者微信号
	 */
	private String ToUserName;
	
	/**
	 * 发送方帐号（一个OpenID）
	 */
	private String FromUserName;
	
	/**
	 * 消息创建时间 （整型）
	 */
	private Integer CreateTime;
	
	/**
	 * 消息类型:文本-text;图片-image;语音-voice;视频-video;小视频-shortvideo;地理位置-location;链接-link
	 */
	private String MsgType;
	
	/**
	 * 消息id，64位整型
	 */
	private Long MsgId;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public Integer getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Integer createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public Long getMsgId() {
		return MsgId;
	}

	public void setMsgId(Long msgId) {
		MsgId = msgId;
	}

}
