package rrx.cnuo.service.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("用户完成桃花牌任务的情况")
@Data
public class BoardTaskVo {

	@ApiModelProperty("是否绑定手机号")
	private Boolean hasBindPhone;
	@ApiModelProperty("是否生成桃花券(可以展示到首页)")
	private Boolean hasBoard;
	@ApiModelProperty("是否完成认证")
	private Boolean isVerified;
	@ApiModelProperty("分享给好友奖励是否已领")
	private Boolean shareToFriendAward;
	
}
