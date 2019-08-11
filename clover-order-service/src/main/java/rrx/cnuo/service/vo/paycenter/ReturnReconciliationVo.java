package rrx.cnuo.service.vo.paycenter;

import java.util.List;

/**
 * 支付中心对账返回字段
 * @author xuhongyu
 * @version 创建时间：2018年7月9日 下午4:48:46
 */
public class ReturnReconciliationVo extends ReturnPayBase{

	/**
	 * 从第三方获取到的对账日内所有订单结果记录
	 */
	private List<Record> records;

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

}
