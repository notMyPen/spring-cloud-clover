package rrx.cnuo.service.vo.msgcenter;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 短信信息vo
 * @author xuhongyu
 * @version 创建时间：2018年7月12日 上午11:16:05
 */
public class SmsMassegeVo {
	
	private String msgType;
	
	private String msgChannel;
	
	/**
	 * 短信类型，验证码短信上送"type_code"；催收短信上送"type_collection"
	 */
	private String msgSmsType;
	
	/**
	 * 短信消息路由，普通短信上送"msg_normal"；语音短信上送"msg_voice"。另注当msg_sms_type为"type_collection"时不需要上送该参数
	 */
	private String msgRouter;
	
	/**
	 * 发送手机号码，多个号码请用","间隔，例如"13333333333,13333333334,..."
	 */
	private String msgMobiles;
	
	/**
	 * 短信内容(不需前置签名)，短信路由为普通短信时，
		msg_content可自定义，示例msg_content:"xxx"；
		短信路由为语音短信时，msg_content只能包含验证码，
		示例msg_content:"666666"
	 */
	private String msgContent;
	
	private String msgCode;
	
	/**
	 * 初始化对象时，写入msg_type和msg_channel
	 */
	public SmsMassegeVo(){
		this.msgType = "sms";
		this.msgChannel = "XDD";
	}

	public String getMsgSmsType() {
		return msgSmsType;
	}

	public void setMsgSmsType(String msgSmsType) {
		this.msgSmsType = msgSmsType;
	}

	public String getMsgRouter() {
		return msgRouter;
	}

	public void setMsgRouter(String msgRouter) {
		this.msgRouter = msgRouter;
	}

	public String getMsgMobiles() {
		return msgMobiles;
	}

	public void setMsgMobiles(String msgMobiles) {
		this.msgMobiles = msgMobiles;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	
	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public String toJSONString() {
		JSONObject json = new JSONObject();
		json.put("msg_type", this.msgType);
		json.put("msg_channel", this.msgChannel);
		if(StringUtils.isNotBlank(this.msgSmsType)){
			json.put("msg_sms_type", this.msgSmsType);
		}
		if(StringUtils.isNotBlank(this.msgRouter)){
			json.put("msg_router", this.msgRouter);
		}
		if(StringUtils.isNotBlank(this.msgMobiles)){
			json.put("msg_mobiles", this.msgMobiles);
		}
		if(StringUtils.isNotBlank(this.msgContent)){
			json.put("msg_content", this.msgContent);
		}
		if(StringUtils.isNotBlank(this.msgCode)){
			json.put("msg_code", this.msgCode);
		}
		return json.toJSONString();
	}
	
}
