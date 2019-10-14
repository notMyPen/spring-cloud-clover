package rrx.cnuo.service.vo.creditCenter;

import rrx.cnuo.service.vo.creditCenter.sbgjj.SbgjjGrabParam;
import rrx.cnuo.service.vo.creditCenter.xuexin.GrabParms;

/**
 * 数据墨盒登录信息参数
 * @author xuhongyu
 * @date 2019年5月8日
 */
public class SjmhLoginParams extends UnderlineBasicParam{
	private int fromStatus = 0;//"fromStatus":0 //来自那里，0是正常，1是来自急速借条
	
	private GrabParms loginXueXinParams;
	private SbgjjGrabParam loginSbgjjParams;
	
	public int getFromStatus() {
		return fromStatus;
	}
	public void setFromStatus(int fromStatus) {
		this.fromStatus = fromStatus;
	}
	public GrabParms getLoginXueXinParams() {
		return loginXueXinParams;
	}
	public void setLoginXueXinParams(GrabParms loginXueXinParams) {
		this.loginXueXinParams = loginXueXinParams;
	}
	public SbgjjGrabParam getLoginSbgjjParams() {
		return loginSbgjjParams;
	}
	public void setLoginSbgjjParams(SbgjjGrabParam loginSbgjjParams) {
		this.loginSbgjjParams = loginSbgjjParams;
	}
	
}
