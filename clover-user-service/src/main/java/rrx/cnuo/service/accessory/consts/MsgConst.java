package rrx.cnuo.service.accessory.consts;

/**
 * 消息相关枚举
 * @author xuhongyu
 * @date 2019年8月28日
 */
public class MsgConst {

	/**
     * 发送短信的业务类型：1.申请贷款
     * @author xuhongyu
     * @date 2019年4月3日
     */
    public static class SendMsgBusinessType{
    	public final static byte APPLY_BID = 1;
    }
    
    /**
     * 短信消息路由，普通短信上送"msg_normal"；语音短信上送"msg_voice"（当msg_sms_type为"type_collection"时不需要上送该参数）
     * @author xuhongyu
     * @version 创建时间：2018年7月12日 下午1:39:25
     */
    public static class MsgRouter {
    	public final static String MSG_NORMAL = "msg_normal";
    	public final static String MSG_VOICE = "msg_voice";
    }
    
    /**
     * 短信类型，验证码短信上送"type_code"；催收短信上送"type_collection"
     * @author xuhongyu
     * @version 创建时间：2018年7月12日 下午1:36:41
     */
    public static class MsgSmsType {
        public final static String TYPE_CODE = "type_code";
        public final static String TYPE_COLLECTION = "type_collection";
    }
    
}