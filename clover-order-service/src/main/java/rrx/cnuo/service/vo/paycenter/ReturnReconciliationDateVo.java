package rrx.cnuo.service.vo.paycenter;

import java.util.ArrayList;
import java.util.List;

public class ReturnReconciliationDateVo {

	private int respCode;
	private List<String> dates = new ArrayList<String>();
	
	public int getRespCode() {
		return respCode;
	}
	public void setRespCode(int respCode) {
		this.respCode = respCode;
	}
	public List<String> getDates() {
		return dates;
	}
	public void setDates(List<String> dates) {
		this.dates = dates;
	}
}
