package rrx.cnuo.service.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import rrx.cnuo.service.accessory.consts.UserConst.CreditStatus;

@ApiModel(value = "用户认证状态返回vo")
public class UserCreditStatusVo {

	@JsonFormat(shape = Shape.STRING)
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

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Boolean getIdcardVerifyStatus() {
		return idcardVerifyStatus;
	}

	public void setIdcardVerifyStatus(Byte idcardVerifyStatus) {
		if(idcardVerifyStatus != null) {
			if(idcardVerifyStatus == CreditStatus.Succuss.getCode()) {
				this.idcardVerifyStatus = true;
			}else {
				this.idcardVerifyStatus = false;
			}
		}
	}

	public Boolean getFaceVerifyStatus() {
		return faceVerifyStatus;
	}

	public void setFaceVerifyStatus(Byte faceVerifyStatus) {
		if(faceVerifyStatus != null) {
			if(faceVerifyStatus == CreditStatus.Succuss.getCode()) {
				this.faceVerifyStatus = true;
			}else {
				this.faceVerifyStatus = false;
			}
		}
	}

	public Boolean getXuexinCreditStatus() {
		return xuexinCreditStatus;
	}

	public void setXuexinCreditStatus(Byte xuexinCreditStatus) {
		if(xuexinCreditStatus != null) {
			if(xuexinCreditStatus == CreditStatus.Succuss.getCode()) {
				this.xuexinCreditStatus = true;
			}else {
				this.xuexinCreditStatus = false;
			}
		}
	}

	public Boolean getShebaoCreditStatus() {
		return shebaoCreditStatus;
	}

	public void setShebaoCreditStatus(Byte shebaoCreditStatus) {
		if(shebaoCreditStatus != null) {
			if(shebaoCreditStatus == CreditStatus.Succuss.getCode()) {
				this.shebaoCreditStatus = true;
			}else {
				this.shebaoCreditStatus = false;
			}
		}
	}

	public Boolean getGjjCreditStatus() {
		return gjjCreditStatus;
	}

	public void setGjjCreditStatus(Byte gjjCreditStatus) {
		if(gjjCreditStatus != null) {
			if(gjjCreditStatus == CreditStatus.Succuss.getCode()) {
				this.gjjCreditStatus = true;
			}else {
				this.gjjCreditStatus = false;
			}
		}
	}

	public Boolean getDuotouLendStatus() {
		return duotouLendStatus;
	}

	public void setDuotouLendStatus(Byte duotouLendStatus) {
		if(duotouLendStatus != null) {
			if(duotouLendStatus == CreditStatus.Succuss.getCode()) {
				this.duotouLendStatus = true;
			}else {
				this.duotouLendStatus = false;
			}
		}
	}

	public Boolean getDishonestCreditStatus() {
		return dishonestCreditStatus;
	}

	public void setDishonestCreditStatus(Byte dishonestCreditStatus) {
		if(dishonestCreditStatus != null) {
			if(dishonestCreditStatus == CreditStatus.Succuss.getCode()) {
				this.dishonestCreditStatus = true;
			}else {
				this.dishonestCreditStatus = false;
			}
		}
	}

	public Boolean getMarryStatus() {
		return marryStatus;
	}

	public void setMarryStatus(Byte marryStatus) {
		if(marryStatus != null) {
			if(marryStatus == CreditStatus.Succuss.getCode()) {
				this.marryStatus = true;
			}else {
				this.marryStatus = false;
			}
		}
	}

	public Boolean getHouseCreditStatus() {
		return houseCreditStatus;
	}

	public void setHouseCreditStatus(Byte houseCreditStatus) {
		if(houseCreditStatus != null) {
			if(houseCreditStatus == CreditStatus.Succuss.getCode()) {
				this.houseCreditStatus = true;
			}else {
				this.houseCreditStatus = false;
			}
		}
	}

	public Boolean getCarCreditStatus() {
		return carCreditStatus;
	}

	public void setCarCreditStatus(Byte carCreditStatus) {
		if(carCreditStatus != null) {
			if(carCreditStatus == CreditStatus.Succuss.getCode()) {
				this.carCreditStatus = true;
			}else {
				this.carCreditStatus = false;
			}
		}
	}

	public Boolean getIncomeCreditStatus() {
		return incomeCreditStatus;
	}
	
	public void setIncomeCreditStatus(Byte incomeCreditStatus) {
		if(incomeCreditStatus != null) {
			if(incomeCreditStatus == CreditStatus.Succuss.getCode()) {
				this.incomeCreditStatus = true;
			}else {
				this.incomeCreditStatus = false;
			}
		}
	}
	
}
