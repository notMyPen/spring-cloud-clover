package rrx.cnuo.service.vo.creditCenter;

import lombok.Data;

/**
 * 信用中心两种参数类型，驼峰类型参数基类
 * @author xuhongyu
 * @date 2019年9月6日
 */
@Data
public class HumpBasicParam {

	private String userId;
	private String systemType;
	private String contentType;//类型：gjj:公积金、sb:社保
	private String channelCode;
	
}
