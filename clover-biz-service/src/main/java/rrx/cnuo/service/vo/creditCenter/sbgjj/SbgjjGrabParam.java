package rrx.cnuo.service.vo.creditCenter.sbgjj;

import lombok.Data;

/**
 * 社保公积金登录验证参数
 */
@Data
public class SbgjjGrabParam {

	private String channel_type;
	private String channel_code;
	private String report_id ;
	private LoginParam loginParams;
	
}
