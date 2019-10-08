package rrx.cnuo.service.vo.creditCenter.sbgjj;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class LoginParam {

	private String name;
	private String value;
	private List<FieldParam> fields = new ArrayList<FieldParam>();
}
