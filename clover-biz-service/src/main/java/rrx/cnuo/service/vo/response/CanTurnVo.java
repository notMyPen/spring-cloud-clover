package rrx.cnuo.service.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户礼券信息")
public class CanTurnVo {

	@ApiModelProperty("用户剩余券数")
	private Integer cardNum;
	
	@ApiModelProperty("翻一次牌子的价格(用券个数)")
	private Byte price;
	
	@ApiModelProperty("微信号")
	private String wxAccount;
}
