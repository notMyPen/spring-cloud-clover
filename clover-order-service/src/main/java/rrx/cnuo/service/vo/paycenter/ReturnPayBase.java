package rrx.cnuo.service.vo.paycenter;

/**
 * 支付中心返回参数基类
 * @author xuhongyu
 * @version 创建时间：2018年7月31日 上午11:47:15
 */
public class ReturnPayBase {

	/**
	 * 返回码
	 */
	protected Integer respCode;
	
	/**
	 * 错误描述
	 */
	protected String respMsg;

	public Integer getRespCode() {
		return respCode;
	}

	public void setRespCode(Integer respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	
	
}
