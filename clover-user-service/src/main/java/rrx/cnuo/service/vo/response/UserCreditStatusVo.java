package rrx.cnuo.service.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户认证状态返回vo")
@Data
public class UserCreditStatusVo {

	//TODO get方法转String类型
	@ApiModelProperty(value = "用户id")
	private Long uid;

	@ApiModelProperty(value = "是否进行身份证认证")
    private Boolean idcardVerifyStatus;

	@ApiModelProperty(value = "是否进行人脸认证")
    private Boolean faceVerifyStatus;

	@ApiModelProperty(value = "是否进行学信认证")
    private Boolean xuexinCreditStatus;

	@ApiModelProperty(value = "是否进行社保认证")
    private Boolean shebaoCreditStatus;

	@ApiModelProperty(value = "是否进行公积金认证")
    private Boolean gjjCreditStatus;

	@ApiModelProperty(value = "是否进行多头借贷认证")
    private Boolean duotouLendStatus;

	@ApiModelProperty(value = "是否进行高发失信认证")
    private Boolean dishonestCreditStatus;

	@ApiModelProperty(value = "是否进行婚姻认证")
    private Boolean marryStatus;

	@ApiModelProperty(value = "是否进行房屋认证")
    private Boolean houseCreditStatus;

	@ApiModelProperty(value = "是否进行购车认证")
    private Boolean carCreditStatus;

	@ApiModelProperty(value = "是否进行收入认证")
    private Boolean incomeCreditStatus;
}
