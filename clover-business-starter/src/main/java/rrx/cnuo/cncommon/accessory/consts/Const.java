package rrx.cnuo.cncommon.accessory.consts;

public class Const {
    
    public class REDIS_PREFIX {
        public static final int USER_BASEINFO_EXPIRE = 864000;
        public static final int ACCESS_TOKEN_EXPIRE = 7000;
        public static final int USER_INFO_SECONDS = 3600*24*3;// 用户个人信息失效时间，单位(秒)
        public static final int BID_AUTO_DEAL_LEFT_TIME = 3600*24*3;// 用标的申请自动完成交易剩余时间(72小时)，单位(秒)
        
        public static final String REDIS_USER_PASSPORT = "data:user_passport:"; //用户user_passport表信息
        public static final String REDIS_USER_ACCOUNT = "data:user_account:"; //用户user_account表信息
        public static final String REDIS_USER_BASIC_INFO = "data:user_basic_info:"; //用户user_basic_info表信息
        public static final String REDIS_USER_DETAIL_INFO = "data:user_detail_info:"; //用户user_detail_info表信息
        public static final String REDIS_USER_SPOUSE_SELECTION = "data:user_spouse_selection:"; //用户user_spouse_selection表信息
        public static final String REDIS_USER_CREDIT_STATUS = "data:user_credit_status:"; //用户user_credit_status表信息
        public static final String REDIS_BOARD_STATIS = "data:board_statis:"; //用户board_statis表信息
        public static final String TICKET_FILE = "ticket:";
        
        
        public static final String MQ_SEND_PREFIX = "mq_msgs";// mq发送消息暂存到redis的prefix
        public static final String MQ_CONSUME_FAIL_CNT = "mq_consume_fail_cnt";// mq消息消费失败后重试次数(最多三次，之后暂存mysql等解决bug后定时任务重发)
        
        public static final String REDIS_LOCK_KEY_PREFIX = "lock:";//redis锁的key值前缀
        public static final String BILL_LIMIT_DOWNLOAD_CNT = "billLimitDownloanCnt";//限制某异常对账日最多重复下载对账单的次数
        
        public static final String REDIS_TEL_NUM = "tel_num:";// 手机号发送次数
        public static final String REDIS_CODE_INPUT_NUM = "code_cnt:";// 短信token前缀
        public static final int REDIS_TEL_INPUT_NUM = 100;// 密码/验证码尝试输入次数
        public static final int SMS_CONTINUITY_SECONDS = 600;// 同一个手机号连续发送验证码失效时间，单位(秒)
        public static final String REDIS_TEL = "xdd_tel";// 短信token前缀
        public static final int REDIS_SMS_SEND_NUM = 10;// 手机号短时间内发送短信验证码次数上限
        public static final String REDIS_SMS_IP_NUM = "sms_ip_num:";// IP手机号发送次数
        public static final int SMS_IP_LIMIT_PER_HOUR = 200; //短信验证码同一个ip一个小时发送次数上限
        public static final int SMS_EXPIRE_TIME_SECONDS = 300;// 短信验证码存入redis时间
        public static final int IP_LIMIT_EXPIRE_TIME_SECONDS = 3600;// 用户单ip获取验证码or语音验证码限制时间
        
        public static final String REDIS_TIMER = "timer:";
        
        public static final long FORMID_EXPIRE = (7 * 24 * 60 * 60 * 1000); // formid过期时间
        public static final long CONFIRM_EXPIRE = (24 * 60 * 60 * 1000); // formid确认过期时间
    }
    
    public static class MQ {
		public static final String MQ_HANDLER_TYPE_KEY = "mqHandleTypeKey";// mq操作类型的key名称
		public static final String MQ_CORRELATION_ID_KEY = "correlationId";// 需要确保mq发送到broker的业务发送消息时需要
		public static final int MQ_CONSUME_RETRY_CNT = 5;// mq重试消费最大次数
		public static final long DELAY_DELETE_REDIS_TIME = 1400;// 延迟失效redis的时间(单位毫秒)

		//一下定义通过队列来操作的操作名称
	    public static final String DELAY_DELETE_REDIS_KEY = "redisKey";// 延迟删除redis操作的key名称
	}
    
    /**
	 * 想通过mq来完成的操作类型
	 * @author xuhongyu
	 */
	public enum MqHandleType {
		DELREDIS((byte)0), // 删除redis
//		SEND_SMS_MSG((byte)1), // 发短信消息
		SEND_WX_MSG((byte)2), // 发微信消息
		REGIST_CREDIT_CENTER((byte)3), //注册信用中心
		
		RECORD_LOGIN_INFO((byte)6), // 记录用户登录信息(业务解耦相关操作)
		SAVE_WECHAT_PAYMENT_INFO((byte)7), // 记录微信支付回调信息(业务解耦相关操作)
		TEST((byte)100); // 用于测试
		private Byte code; // 定义私有变量
		// 构造函数，枚举类型只能为私有

		private MqHandleType(Byte code) {
			this.code = code;
		}

		// 构造函数，枚举类型只能为私有
		public byte getCode() {
			return code;
		}

		public static MqHandleType getMqHandleTypeCode(Byte code) {
			for (MqHandleType nTmUnit : MqHandleType.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * 礼券奖励类型：0-购买时的奖励；1-转发奖励；2-认证奖励；3-首次审核奖励；4-生日奖励；5-连续登陆奖励; 6-管理员奖励
	 * @author xuhongyu
	 * @date 2019年9月4日
	 */
	public enum AwardType {
        BUY(0,1),
        FORWORD(1,1),
        CREDIT(2,5),
        CHECK_FIRST(3,1),
    	BIRTHDAY(4,1),
    	LOGIN_CONTINUE(5,1),
		ADMIN_AWARD(6,0);
		
        private byte code;// 定义私有变量
        private int awardNum;// 奖励的券个数
        
        // 构造函数，枚举类型只能为私有
        private AwardType(int code,int awardNum) {
            this.code = (byte)code;
            this.awardNum = awardNum;
        }
        public byte getCode(){
            return this.code;  
        }
		public int getAwardNum() {
			return awardNum;
		}
		public static AwardType getAwardType(byte code) {
			for (AwardType nTmUnit : AwardType.values()) {
				if (code==nTmUnit.getCode()) {
					return nTmUnit;
				}
			}
			return null;
		}
		public static Boolean isContain(Byte code) {
			for (AwardType status : AwardType.values()) {
				if (code.equals(status.getCode())) {
					return true;
				}
			}
			return false;
		}
    }
    
    /**
     * 微信消息类型<br>
     * 消息模板中如果有变量用%s表示(msgValue参数中的值用#号分割并和模板中%s号顺序对应)
     * @author xuhongyu
     * @date 2019年7月2日
     */
    public enum WeChatMsgEnum {
    	CREDIT_FEE_PAIED(1, "认证费缴费通知","认证费支付成功，赶快来完成信用认证，开启您的征婚之旅吧！","pages/wallet/index"),
    	CREDIT_COMPLETE(2, "认证完成通知","信用认证已全部完成，系统另赠送%s张礼券！小贴士：在翻心仪之人的牌子之前可以查看对方的认证信息，让择友更加准确安全。",""),
	    BUYCARD_PAIED(3, "礼券购买通知","您购买的礼券已到账，系统另赠送%s张礼券，祝您早日找到那个TA！","pages/index/index"),
	    ERROR_WARNING(4, "重要接口报错警告","",""),
	    YUNWEI_NOTIFY(5, "系统运行状态通知","",""),
//	    RECEIVE_MONEY_NOTIFY(6, "到账提醒","",""),
//	    REPORT_NOTIFY(7, "举报通知","",""),
//	    REPORT_DEAL_NOTIFY(8, "举报处理通知","",""),
	    RECENCILIATION_RESULT(9, "系统对账结果","","");

	    private byte code;
	    private String title;
	    private String msgTemplate;
	    private String page;

	    WeChatMsgEnum(int code, String title, String msgTemplate,String page) {
	        this.code = (byte) code;
	        this.title = title;
	        this.msgTemplate = msgTemplate;
	        this.page = page;
	    }

	    public byte getCode() {
	        return code;
	    }

	    public String getTitle() {
	        return title;
	    }
	    
	    public String getMsgTemplate() {
			return msgTemplate;
		}

		public String getPage() {
			return page;
		}
		
	    public static WeChatMsgEnum getTypeByCode(byte code) {
			for (WeChatMsgEnum nTmUnit : WeChatMsgEnum.values()) {
				if (code==nTmUnit.getCode()) {
					return nTmUnit;
				}
			}
			return null;
		}
		public static String getTitleByCode(byte code) {
			for (WeChatMsgEnum nTmUnit : WeChatMsgEnum.values()) {
				if (code==nTmUnit.getCode()) {
					return nTmUnit.getTitle();
				}
			}
			return null;
		}
		public static String getMsgTemplateByCode(byte code) {
			for (WeChatMsgEnum nTmUnit : WeChatMsgEnum.values()) {
				if (code==nTmUnit.getCode()) {
					return nTmUnit.getMsgTemplate();
				}
			}
			return null;
		}
		public static String getPageByCode(byte code) {
			for (WeChatMsgEnum nTmUnit : WeChatMsgEnum.values()) {
				if (code==nTmUnit.getCode()) {
					return nTmUnit.getPage();
				}
			}
			return null;
		}
	}
}
