package rrx.cnuo.cncommon.vo;

/**
 * 微信公众号消息
 * @author xuhongyu
 * @date 2019年4月30日
 */
public class MsgVo {
	private Long userId;
	private String openId;
	private String shortUrl;
	private String first;//首
	private String remark;//尾
	private String title;
	private String sendMsgType;
	private int saveMsgType;
	private String key1;
	private String key2;
	private String key3;
	private String key4;
	private String key5;
	private String value1;
	private String value2;
	private String value3;
	private String value4;
	private String value5;
	private Boolean flag = false;
	private String paramValue;
	private String contentValue;
	private Boolean isEncryptID = true;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSendMsgType() {
		return sendMsgType;
	}
	public void setSendMsgType(String sendMsgType) {
		this.sendMsgType = sendMsgType;
	}
	public int getSaveMsgType() {
		return saveMsgType;
	}
	public void setSaveMsgType(int saveMsgType) {
		this.saveMsgType = saveMsgType;
	}
	public String getKey1() {
		return key1;
	}
	public void setKey1(String key1) {
		this.key1 = key1;
	}
	public String getKey2() {
		return key2;
	}
	public void setKey2(String key2) {
		this.key2 = key2;
	}
	public String getKey3() {
		return key3;
	}
	public void setKey3(String key3) {
		this.key3 = key3;
	}
	public String getKey4() {
		return key4;
	}
	public void setKey4(String key4) {
		this.key4 = key4;
	}
	public String getKey5() {
		return key5;
	}
	public void setKey5(String key5) {
		this.key5 = key5;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getValue3() {
		return value3;
	}
	public void setValue3(String value3) {
		this.value3 = value3;
	}
	public String getValue4() {
		return value4;
	}
	public void setValue4(String value4) {
		this.value4 = value4;
	}
	public String getValue5() {
		return value5;
	}
	public void setValue5(String value5) {
		this.value5 = value5;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getContentValue() {
		return contentValue;
	}
	public void setContentValue(String contentValue) {
		this.contentValue = contentValue;
	}
	public Boolean getIsEncryptID() {
		return isEncryptID;
	}
	public void setIsEncryptID(Boolean isEncryptID) {
		this.isEncryptID = isEncryptID;
	}
	
}
