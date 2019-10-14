package rrx.cnuo.service.vo.creditCenter.sbgjj;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 社保公积金认证第一步返回的省信息
 * @author xuhongyu
 * @date 2019年8月22日
 */
@Data
public class ReturnProvenceVo {

	private String groupName;//省名称
	private List<ReturnCityVo> channelDTOS = new ArrayList<>();//省下的地级市列表
}
