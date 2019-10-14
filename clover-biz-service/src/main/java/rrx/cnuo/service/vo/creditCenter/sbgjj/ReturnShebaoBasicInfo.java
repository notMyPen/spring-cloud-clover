package rrx.cnuo.service.vo.creditCenter.sbgjj;

import lombok.Data;

/**
 * 社保详情
 * @author xuhongyu
 * @date 2019年8月22日
 */
@Data
public class ReturnShebaoBasicInfo {

	private String name;
	private String nation;
	private String gender;
	private String hukou_type;
	private String certificate_number;
	private String home_address;
	private String company_name;
	private String mobile;
	private String begin_date;
	private String time_to_work;
}
