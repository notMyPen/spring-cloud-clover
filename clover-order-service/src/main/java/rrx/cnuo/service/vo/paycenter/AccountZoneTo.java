package rrx.cnuo.service.vo.paycenter;

import rrx.cnuo.service.accessory.consts.TradeConst;

public class AccountZoneTo {

	/**
	 * 现金流入ID|1:今借到, 2:信用借还, 3:险查查, 4:微合约, 5:麦蕊, 6:易云章, 98:信芽, 99:人人信, 100:外部银行, 101:微信, 102:支付宝, 10000以内留用
	 */
	private Long uid;
	
	/**
	 * 金额|(单位:分)
	 */
	private Integer amount;
	
	/**
	 * 支付方式 ：0.余额  1.银行卡  2-线下 3.银联(收银台类) 4.微信(app类) 5.支付宝
	 */
	private Byte payMethod;
	
	/**
	 * @param payMethod 支付方式 ：0.余额  1.银行卡  2-线下 3.银联(收银台类) 4.微信(app类) 5.支付宝
	 */
	public AccountZoneTo(Byte payMethod){
		this.payMethod = payMethod;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		if(uid != null){
			if(uid == TradeConst.SYSTEM_BANK){
				if(this.payMethod == TradeConst.PayMethod.WECHAT.getCode() || this.payMethod == TradeConst.PayMethod.APP_WECHAT.getCode()){
					this.uid = new Long(TradeConst.WEIXIN);
				}else if(this.payMethod == TradeConst.PayMethod.APP_ZFB.getCode()){
					this.uid = new Long(TradeConst.ALIPAY);
				}else{
					this.uid = new Long(TradeConst.YINHANGKA);
				}
			}else{
				this.uid = uid;
			}
		}
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}
