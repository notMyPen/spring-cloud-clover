package rrx.cnuo.service.vo;

import lombok.Data;

/**
 * 用户账户明细Vo
 * @author xuhongyu
 * @date 2019年4月1日
 */
@Data
public class AccountListVo {

	/**
	 * 用户名
	 */
	private String partnerName;
	
	/**
	 * 金额变动类型
	 */
	private String acountTypeStr;
	
	private Integer amount;
	
	/**
	 * 交易时间(yyyy-MM-dd HH:mm:ss)
	 */
	private String createTime;
}
