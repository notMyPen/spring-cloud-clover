package rrx.cnuo.service.vo.passport.request;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 小程序授权登录参数
 * @author xuhongyu
 * @date 2019年8月23日
 */
@Data
@ApiModel(description = "登录授权参数vo")
public class OauthParam implements Serializable{

	private static final long serialVersionUID = 5962751044716043299L;
	
	@ApiModelProperty(value = "微信code",required = true)
	private String code;
	
	@ApiModelProperty(value = "微信用户基本信息json字符串",required = true)
	private String rawData;
	
	@ApiModelProperty(value = "授权后微信返回的签名，用于后台安全校验",required = true)
	private String signature;
	
	@ApiModelProperty(value = "用户安全校验的加密数据",required = true)
	private String encryptedData;
	
	@ApiModelProperty(value = "用户设备信息",required = true)
	private String deviceInfo;
	
	@ApiModelProperty(value = "用户设备id",required = true)
	private String deviceId;
	
	@ApiModelProperty(value = "iv",required = true)
	private String iv;
}
