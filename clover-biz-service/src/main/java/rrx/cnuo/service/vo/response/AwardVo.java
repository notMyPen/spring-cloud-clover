package rrx.cnuo.service.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("礼券获取记录")
public class AwardVo {

	@ApiModelProperty("获取礼券的数量")
	private Integer cardNum;
	
	@ApiModelProperty("获取礼券类型：1-单独购买(翻牌子时)、2-购买礼券套餐、3-绑定手机号获取、4-生成桃花券获取、5-完成认证获取、6-分享给好友获取")
	private Byte obtainType;
	
	@ApiModelProperty("获取时间")
	private String obtainTime;
}
