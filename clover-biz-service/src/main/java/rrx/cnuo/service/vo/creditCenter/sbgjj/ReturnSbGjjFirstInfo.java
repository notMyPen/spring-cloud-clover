package rrx.cnuo.service.vo.creditCenter.sbgjj;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 社保公积金认证第一步返回信息
 * @author xuhongyu
 * @date 2019年8月22日
 */
@Data
public class ReturnSbGjjFirstInfo {

	private List<ReturnProvenceVo> cityList = new ArrayList<>();
	private String routeName;//认证通道
}
