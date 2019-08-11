package rrx.cnuo.service.vo.paycenter;

/**
 * 支付中心订单查询返回字段
 * @author xuhongyu
 * @version 创建时间：2018年7月9日 下午4:52:13
 */
public class ReturnOrderQueryVo extends ReturnPayBase{

	/**
	 * 商户订单号
	 */
	private Long orderNo;
	
	/**
	 * 订单状态
	 */
	private Integer orderStatus;
	
	/**
	 * 订单金额
	 */
	private Integer amount;
	
	/**
	 * 储备区域
	 */
	private String reserveZone;


	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
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
