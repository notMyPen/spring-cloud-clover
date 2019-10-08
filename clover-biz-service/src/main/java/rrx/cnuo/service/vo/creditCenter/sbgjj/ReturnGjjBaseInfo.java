package rrx.cnuo.service.vo.creditCenter.sbgjj;

import lombok.Data;

/**
 * 公积金详情
 * @author xuhongyu
 * @date 2019年8月22日
 */
@Data
public class ReturnGjjBaseInfo {

	private String cust_no;
	private String pay_status_desc;
	private String name;
	private String cert_no;
	private String cert_type;
	private String home_address;
	private String mobile;
	private String email;
	private String corp_name;
	private String monthly_corp_income;
	private String monthly_corp_proportion;
	private String monthly_cust_income;
	private String monthly_cust_proportion;
	private String monthly_total_income;
	private String base_number;
	private String balance;
	private String last_pay_date;
	private String begin_date;
}
