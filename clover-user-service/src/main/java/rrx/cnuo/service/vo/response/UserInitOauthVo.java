package rrx.cnuo.service.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "登录授权后返回字段vo")
public class UserInitOauthVo{

	@JsonIgnore
	@JsonFormat(shape = Shape.STRING)
	private Long uid;
	
	@ApiModelProperty(value = "用户openId",required = true)
	private String miniOpenId;
	
	@ApiModelProperty(value = "用户登录凭证",required = true)
	private String ticket;
	
	@ApiModelProperty(value = "用户登录凭证有效期(单位秒)",required = true)
	private Integer expireTime;

}
