package rrx.cnuo.service.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("支付申请返回vo")
@Data
public class ReturnPayBusinessVo {

	@ApiModelProperty("商户订单号（交易id）")
	@JsonFormat(shape = Shape.STRING)
	private Long tradeId;
	
	@ApiModelProperty("支付订单号或协议支付绑卡流水号(第三方支付公司返回)")
	private String payOrderNo;
	
	@ApiModelProperty("支付令牌：1.当为银行卡支付时, 传回payToken字符串；2.当为银联支付时, 传回跳转html代码；3.当为微信支付时, 传回唤醒公众号支付组件的六要素;")
	private String payToken;
}
