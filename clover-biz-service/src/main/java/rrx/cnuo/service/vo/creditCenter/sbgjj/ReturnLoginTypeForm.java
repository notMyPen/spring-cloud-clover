package rrx.cnuo.service.vo.creditCenter.sbgjj;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 社保公积金认证第二步返回，登录方式表单
 * @author xuhongyu
 * @date 2019年8月22日
 */
@Data
public class ReturnLoginTypeForm {

	private String formName;//登录方式描述
	private List<ReturnLoginType> fields = new ArrayList<>();//登录方式列表
}
