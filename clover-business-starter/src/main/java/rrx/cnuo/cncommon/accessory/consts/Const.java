package rrx.cnuo.cncommon.accessory.consts;

public class Const {

	public static final long SYSTEM_USER = 11; // 信多多
    public static final long SYSTEM_BANK = 2; // 银行或第三方
    /**
	 * 现金流入ID|1:今借到, 2:信用借还, 3:险查查, 4:微合约, 5:麦蕊, 6:易云章,11:信多多, 98:信芽, 99:人人信, 100:外部银行, 101:微信, 102:支付宝, 10000以内留用
	 */
    public static final long XINYA = 98; // 支付中心的信呀账户
    public static final long YINHANGKA = 100; // 支付中心的外部银行账户
    public static final long WEIXIN = 101; // 支付中心的微信账户
    public static final long ALIPAY = 102; // 支付中心的支付宝账户
    public static final String CHARACTER = "UTF-8";
    public static final int USER_MAX_FORMID_CNT = 30;//用户最大存储formid个数
//    public static final String TICKET_KEY = "xdd-ticket";		//nginx会忽略掉 带有下划线的 header
//    public static final int IVPARAMETER_LENGTH = 16;//加密算法秘钥长度
    public static final int TICKET_EXPIRE = 60*60*10; //10个小时

    public static final double RECHARGE_FEE_RATE = 0.006; // 用户充值手续费率
    public static final int AGE_MIN = 18; //用户的最小年龄
    public static final int AGE_MAX = 50; //用户的最大年龄
    
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
        public static final String REDIS_USER_STATIS = "data:user_statis:"; //用户user_statis表信息
        public static final String REDIS_SYSTEM_STATIS_ITEM = "data:system_statis_item:"; //system_statis_item表信息
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
		public static final long DELAY_ADD_NETWORK_ARB_TIME = 1000;// 延迟申请网络仲裁的时间(单位毫秒)

		//一下定义通过队列来操作的操作名称
	    public static final String DELAY_DELETE_REDIS_KEY = "redisKey";// 延迟删除redis操作的key名称
	}
    
    /**
	 * 想通过mq来完成的操作类型
	 * @author xuhongyu
	 */
	public enum MqHandleType {
		DELREDIS((byte)0), // 删除redis
		SEND_SMS_MSG((byte)5), // 保存短信消息(业务解耦相关操作)
		SEND_WX_MSG((byte)6), // 保存微信消息(业务解耦相关操作)
		RECORD_LOGIN_TIME((byte)12), // 记录用户登录时间(业务解耦相关操作)
		SAVE_WECHAT_PAYMENT_INFO((byte)13), // 记录微信支付回调信息(业务解耦相关操作)
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
     * 支付业务类型：1-充值；2-提现；3-显示手机号；4-成交分钱；
     * @author xuhongyu
     * @date 2019年4月8日
     */
    public enum PayBusinessType {
        RECHARGE((byte)1, "recharge"), // 充值
        WITHDRAW((byte)2, "withdraw"), // 提现
        SHOW_TELEPHONE((byte)3, "showTelephone"), //显示手机号
        DEAL_DIVIDE_MONEY((byte)4, "dealDivideMoney"); // 成交分钱
        
		private byte code; // 定义私有变量
        private String msg; // 定义私有变量
        
		// 构造函数，枚举类型只能为私有
		private PayBusinessType(byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		// 构造函数，枚举类型只能为私有
		public byte getCode() {
			return code;
		}
		// 构造函数，枚举类型只能为私有
		public String getMessage() {
			return msg;
		}
		public static PayBusinessType getPayBusinessType(Byte code) {
			for (PayBusinessType nTmUnit : PayBusinessType.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
    }
    
    /**
     * 支付中心现金流动类型(跟今借到不同)：现金流类型|1:流入, 2:流出
     * @author xuhongyu
     * @version 创建时间：2018年7月7日 下午5:29:00
     */
    public enum CashFlowType {
        COLLECTION(1), // 代收/代扣
        PAYMENT(2); // 代付
        private byte code;// 定义私有变量
        // 构造函数，枚举类型只能为私有
        private CashFlowType( int code){
            this.code = (byte)code;
        }
        public byte getCode(){
            return this.code;
        }
    }
    
    /**
     * 支付方式:0.余额  1.银行卡  2-线下 3.银联(收银台类) 4.微信(app类) 5.支付宝
     * @author xuhongyu
     * @version 创建时间：2018年6月30日 下午4:06:04
     */
    public enum PayMethod {
        YUE((byte)0),// 余额
        WECHAT((byte)4), // 微信
        ALIPAY((byte)5); // 微信
    	
		private byte code; // 定义私有变量
		// 构造函数，枚举类型只能为私有

		private PayMethod(byte code) {
			this.code = code;
		}

		// 构造函数，枚举类型只能为私有
		public byte getCode() {
			return code;
		}

		public static PayMethod getPayMethod(Byte code) {
			for (PayMethod nTmUnit : PayMethod.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
    }
    
    /**
     * trade表状态
     * @author xuhongyu
     * @version 创建时间：2018年7月5日 下午7:37:46
     */
    public enum TradeStatus {
        APPLY(1),//已申请
        COMFIRM(2),//已确认(已报盘未回盘)
        SUCCESS(3), //支付成功
        FAIL(4),//支付失败
    	CANCEL(5);//订单作废(第三方过期或关闭)
        private byte code;// 定义私有变量
        // 构造函数，枚举类型只能为私有
        private TradeStatus( int code) {
            this.code = (byte)code;
        }
        public byte getCode(){
            return this.code;  
        }
		public static TradeStatus getTradeStatusbyCode(Byte code) {
			for (TradeStatus status : TradeStatus.values()) {
				if (code.equals(status.getCode())) {
					return status;
				}
			}
			return null;
		}
    }
    
    /**
     * 金额变动类型
     * @author xuhongyu
     * @date 2019年4月1日
     */
    public enum AccountType {
        SYS_RECHARGE((byte)1, "充值"),    // 充值
        SYS_WITHDRAWALS((byte)2, "提现"),	//  提现
        RCV_SHOW_TELEPHONE((byte)3, "收到查看手机号手续费"), // 收到查看手机号手续费
        PAY_SHOW_TELEPHONE((byte)4, "支付查看手机号手续费"), // 支付查看手机号手续费
        RCV_BOUNTY((byte)5, "收到赏金"), // 收到赏金
        PAY_BOUNTY((byte)6, "支付赏金"), // 支付赏金
    	
    	PAY_RECHARGE_FEE((byte)10, "充值手续费"),    // 支付系统充值手续费
        RCV_RECHARGE_FEE((byte)11, "充值手续费"),    // 收到系统充值手续费
        PAY_WITHDRAW_FEE((byte)12, "提现手续费"),    // 支付系统提现手续费
        RCV_WITHDRAW_FEE((byte)13, "提现手续费"),    // 收到系统提现手续费
    	PAY_ADVANCE_FEE((byte)14, "垫付手续费"),    // 支付系统垫付手续费
        RCV_ADVANCE_FEE((byte)15, "垫付手续费"); 	  // 收到系统垫付手续费

		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		// 构造函数，枚举类型只能为私有

		private AccountType(byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		// 构造函数，枚举类型只能为私有
		public byte getCode() {
			return code;
		}
		
		public String getMsg() {
			return msg;
		}
		public static AccountType getTypeByCode(byte code) {
			for (AccountType nTmUnit : AccountType.values()) {
				if (code==nTmUnit.getCode()) {
					return nTmUnit;
				}
			}
			return null;
		}
		public static String getMsgByCode(byte code) {
			for (AccountType nTmUnit : AccountType.values()) {
				if (code==nTmUnit.getCode()) {
					return nTmUnit.getMsg();
				}
			}
			return null;
		}
    }
    
    /**
     * 支付通道
     * @author xuhongyu
     * @date 2019年4月26日
     */
    public enum TradeType {
        BALANCE_PAY((byte)2,"余额支付"),//余额支付通道
        MINI_WX_PAY((byte)12,"小程序微信支付"), //小程序微信支付
    	WX_WITHDRAW((byte)15,"微信提现"); //微信提现
    	
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private TradeType(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}

		public static TradeType getTradeType(Byte code) {
			for (TradeType nTmUnit : TradeType.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
    }
    
    /**
     * 微信消息类型<br>
     * 消息模板中如果有变量用%s表示(msgValue参数中的值用#号分割并和模板中%s号顺序对应)
     * @author xuhongyu
     * @date 2019年7月2日
     */
    public enum WeChatMsgEnum {
	    CREDIT_NOTIFY(0, "认证通知","已经找到您要找的人，请对他进行身份审核确认。","pages/index/index"),
	    BUSSINESS_PROGRESS(1, "业务进程通知","%s：%s",""),
	    RECENCILIATION_RESULT(2, "系统对账结果","",""),
	    ERROR_WARNING(3, "重要接口报错警告","",""),
	    TRADE_NOTIFY(5, "交易提醒","您帮助找的人已经找到，赏金%s元已转入账户。","pages/wallet/index"),
	    REPORT_NOTIFY(8, "举报通知","",""),
	    RECEIVE_MONEY_NOTIFY(9, "到账提醒","",""),
	    YUNWEI_NOTIFY(10, "系统运行状态通知","",""),
	    REPORT_DEAL_NOTIFY(11, "举报处理通知","",""),
	    APPLY_RESULT_NOTIFY(17, "申请结果通知","",""),
	    WRITE_OFF_NOTIFY(18, "销账通知","","");

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
