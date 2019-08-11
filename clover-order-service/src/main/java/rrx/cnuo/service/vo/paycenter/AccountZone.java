package rrx.cnuo.service.vo.paycenter;

import java.util.ArrayList;
import java.util.List;

/**
 * {"accountZone":{"uid":"123","amount":20000,"comments":"今借到还款","fuid":"100",
 * 		"to":[{"uid":"456","amount":19900},{"uid":"1","amount":100}]}}
 * @author xuhongyu
 * @version 创建时间：2018年7月30日 下午8:06:45
 */
public class AccountZone {

	/**
	 * 现金流入ID|1:今借到, 2:信用借还, 3:险查查, 4:微合约, 5:麦蕊, 6:易云章, 98:信芽, 99:人人信, 100:外部银行, 101:微信, 10000以内留用
	 */
	private Long uid;
	
	/**
	 * 现金流出ID|(同uid)
	 */
	private Long fuid;
	
	/**
	 * 金额|(单位:分)
	 */
	private Integer amount;
	
	/**
	 * 注释
	 */
	private String comments;
	
	private List<AccountZoneTo> to = new ArrayList<AccountZoneTo>();

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getFuid() {
		return fuid;
	}

	public void setFuid(Long fuid) {
		this.fuid = fuid;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<AccountZoneTo> getTo() {
		return to;
	}

	public void setTo(List<AccountZoneTo> to) {
		this.to = to;
	}
	
}
