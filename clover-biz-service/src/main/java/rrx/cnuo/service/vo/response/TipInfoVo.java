package rrx.cnuo.service.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("户未读消息数")
public class TipInfoVo {

	@ApiModelProperty("是否有新消息")
	private Boolean hasNewTip;
	
	@ApiModelProperty("未读喜欢我数")
	private Integer unReadLikeMeNum;
	
	@ApiModelProperty("未读浏览过我人数")
	private Integer unReadViewMeNum;
	
	@ApiModelProperty("未读翻我牌子(要我微信号)数")
	private Integer unReadTurnBoardNum;
}
