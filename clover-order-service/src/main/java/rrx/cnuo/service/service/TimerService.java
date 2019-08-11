package rrx.cnuo.service.service;

public interface TimerService {
	
	/**
	 * 每天凌晨更新垫资额度：计算昨天的垫资统计，插入今天的垫资汇总
	 * @author xuhongyu
	 */
//	public void updateCapital() throws Exception;

	/**
	 * 更新今天的垫资汇总
	 * @author xuhongyu
	 */
//	public void updateCapitalAmt(String today) throws Exception;

	/**
	 * 清除八小时前三天内没有报盘的交易表记录
	 * @author xuhongyu
	 */
//	public void deleteUnTradeRecords();
	
	public int addTaskLog(String functionName);
	
	public void updateTaskLog(String functionName, int id, byte result, Exception e);

}
