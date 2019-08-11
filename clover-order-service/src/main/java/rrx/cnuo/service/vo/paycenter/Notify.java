package rrx.cnuo.service.vo.paycenter;

public class Notify {

	private Long orderNo;
	
//	private Long tradeId;
	
	private String msg;
	
	private Integer orderStatus;
	
	private Integer amount;
	
	private String reserveZone;

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		/*if(StringUtils.isNotBlank(orderNo)){
			this.tradeId = Long.parseLong(orderNo);
		}*/
		this.orderNo = orderNo;
	}

	/*public Long getTradeId() {
		return tradeId;
	}

	public void setTradeId(Long tradeId) {
		if(tradeId != null){
			this.orderNo = tradeId.toString();
			this.tradeId = tradeId;
		}
	}*/

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getReserveZone() {
		return reserveZone;
	}

	public void setReserveZone(String reserveZone) {
		this.reserveZone = reserveZone;
	}
	
}
