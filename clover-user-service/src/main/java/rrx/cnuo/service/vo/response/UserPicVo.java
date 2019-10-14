package rrx.cnuo.service.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户图片信息")
public class UserPicVo {

//	@JsonFormat(shape = Shape.STRING)
//	@ApiModelProperty(value = "图片id")
//	private Long id;
	
	@ApiModelProperty(value = "图片顺序")
	private Byte picOrder;
	
	@ApiModelProperty(value = "图片url")
	private String picUrl;
}
