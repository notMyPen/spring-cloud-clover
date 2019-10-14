package rrx.cnuo.service.vo.creditCenter.sbgjj;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 社保公积金认证第二步返回，登录方式
 * @author xuhongyu
 * @date 2019年8月22日
 */
@Data
public class ReturnLoginType {

	private String field;//字段参数名
	private String fieldName;//字段名
	private String fieldDesc;//字段描述
	private List<ReturnOption> optionValue = new ArrayList<>();//选择框
}
