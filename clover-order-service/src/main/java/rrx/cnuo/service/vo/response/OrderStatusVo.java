package rrx.cnuo.service.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("查询订单状态返回的vo")
public class OrderStatusVo {

	@ApiModelProperty(value = "订单状态：1-已申请未确认；2-已确认未回盘；3-支付成功；4支付失败；5订单作废(第三方过期或关闭)；6-用户放弃支付",required = true)
	private Byte tradeStatus;
	
	@ApiModelProperty(value = "被翻牌子人的微信号(翻牌子支付时轮询时使用)",required = false)
	private String wxAccount;
}
