package rrx.cnuo.service.service;


import rrx.cnuo.cncommon.accessory.consts.Const.WeChatMsgEnum;
import rrx.cnuo.cncommon.vo.JsonResult;

/**
 * 发送微信消息或短信消息
 * @author xuhongyu
 * @date 2019年4月3日
 */
@SuppressWarnings("rawtypes")
public interface MsgService {
	
	
	/**
	 * 发送短信消息
	 * @author xuhongyu
	 * @param requestIp
	 * @param telephone
	 * @param nType 0.注册验证码 1.申请贷款 2.修改交易密码 3.余额支付4.提现 5.找回密码 6.非注册获取验证码 7.原样发送8.充值9.重置密码10.好友找回
	 * @param args 短信相关内容
	 * @return
	 * @throws Exception
	 */
	JsonResult updateSendSmsMessage(String requestIp, String telephone, int nType,String... args) throws Exception;

	/**
	 * 发送微信小程序消息
	 * @author xuhongyu
	 * @param uid
	 * @param noticeLenderReview 发送的类型，不同类型对应不同消息模板
	 * @param msgVariableVal 消息内容中变量部分的值，不同值用#号分割并和模板中%s号顺序对应
	 */
	boolean updateSendMiniWxMsg(Long uid, WeChatMsgEnum noticeLenderReview, String msgVariableVal) throws Exception;
	
	/**
	 * 删除过期的formid
	 * @author xuhongyu
	 * @throws Exception
	 */
	public void removeExpiredFormid() throws Exception;
	
	/**
	 * 添加formid
	 * @author xuhongyu
	 * @param formId
	 * @return
	 * @throws Exception
	 */
	public JsonResult saveFormId(String formId) throws Exception;
}
