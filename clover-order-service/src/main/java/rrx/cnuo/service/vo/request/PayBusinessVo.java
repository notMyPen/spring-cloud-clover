package rrx.cnuo.service.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("支付时前端需要传的参数")
public class PayBusinessVo{

	@ApiModelProperty(value = "用户在前端想要支付或提现的钱(没有加或减任何系统费用)",required = true)
	private Integer amount;
	
	@ApiModelProperty(value = "支付业务类型：1-支付认证费；2-购买礼券；3-提现",required = true)
	private Byte payBusinessType;
	
	@ApiModelProperty(value = "支付方式 ：0.余额  1.银行卡  2-线下 3.银联(收银台类) 4.微信(app类)",required = true)
	private Byte payMethod;
	
	@ApiModelProperty(value = "购买礼券个数",required = false)
	private Integer buyCardNum;
	
	/* 以上是需要前端传进来的参数，下面是后端内部使用 */
	
	private Long userId;
	private String openId;//微信支付时传openId
	private Long tradeId;//交易表主键tradeId
//	private String payOrderNo;//支付订单号或协议支付绑卡流水号(第三方支付公司返回)
	private Integer rechargeWithdrawFee = 0;//充值/提现手续费
	private Integer totalAmount;//交易(已经加或减各种费用了)金额，以分为单位
	private Byte payChannelType;//支付通道：
	private Byte cashFlowType;//现金流动类型：0-代收/代扣；1-代付
	private String payToken;//支付令牌(第三方支付公司返回)
	private boolean makeTempAccount;//是否是临时做账(默认是false)
	private String reserveData;//预留字符串，用于银联支付或微信支付时用于回盘时做报盘表操作的业务数据（存成json字符串）
	
//	private String userIp;//用户ip（用于余额支付、提现等需要发送短信验证码时用）
//	private String payPassword;//支付密码
//	private boolean weixin;//是否是微信端(支付申请时必然是false)
//	private String authCode;//短信验证码
}
