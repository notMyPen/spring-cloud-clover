package rrx.cnuo.service.vo;

import rrx.cnuo.cncommon.vo.PayBusinessVo;

public class RechargeVo extends PayBusinessVo{

	/**
	 * 出借人在发布产品时必须保证余额大于两倍赏金，否则提示去充值，这种情况充值时传产品id，成功后自动完成产品发布
	 */
	private Long prodId;

	public Long getProdId() {
		return prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}
}
