package rrx.cnuo.service.weixin.template.base;

/**
 * Title: ValueColor.java
 * Description
 * @author 
 * @date 2015年7月23日
 * @version 1.0
 */
public class ValueColor {

	private String value;//值
	private String color="#173177";//颜色
	
	public ValueColor(String value) {
		super();
		this.value = value;
	}
	public ValueColor(String value, String color) {
		super();
		this.value = value;
		this.color = color;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
}
