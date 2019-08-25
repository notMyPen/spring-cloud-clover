package rrx.cnuo.service.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("我的通行证返回字段vo")
public class UserPassportVo {

	@ApiModelProperty(value = "是否支付认证费",required = true)
	private Boolean creditFee;
	
	@ApiModelProperty(value = "用户头像",required = true)
    private String avatarUrl;

	@ApiModelProperty(value = "翻牌券个数",required = true)
    private Integer cardNum;

	@ApiModelProperty(value = "认证是否全部通过",required = true)
    private Boolean creditPass;

	@ApiModelProperty(value = "用户桃花牌(是否首页展示)状态：0-不展示、1-展示、2-下线",required = true)
    private Byte boardStatus;
	
	@ApiModelProperty(value = "是否是高质量用户",required = true)
    private Boolean highQuality;
	
	@ApiModelProperty(value = "个人资料完整度(%)",required = true)
    private Integer personalDataIntegrity;
    
    @ApiModelProperty(value = "择偶条件完整度(%)",required = true)
    private Integer spouseConditionIntegrity;

}