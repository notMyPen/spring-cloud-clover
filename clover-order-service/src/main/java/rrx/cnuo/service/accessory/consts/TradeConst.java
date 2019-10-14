package rrx.cnuo.service.accessory.consts;

public class TradeConst {
	
	public static final long SYSTEM_USER = 11; // 信多多
    public static final long SYSTEM_BANK = 2; // 银行或第三方
    /**
	 * 现金流入ID|1:今借到, 2:信用借还, 3:险查查, 4:微合约, 5:麦蕊, 6:易云章,11:信多多, 98:信芽, 99:人人信, 100:外部银行, 101:微信, 102:支付宝, 10000以内留用
	 */
    public static final long XINYA = 98; // 支付中心的信呀账户
    public static final long YINHANGKA = 100; // 支付中心的外部银行账户
    public static final long WEIXIN = 101; // 支付中心的微信账户
    public static final long ALIPAY = 102; // 支付中心的支付宝账户
    
    public static class PAY_INFO { // 支付费信息    	
    	public static final int MAX_RECHARGE_AMT = 2000000;//充值最高金额(2万)
    	public static final int MIN_BOUNTY_AMT = 100;//充值/赏金最低金额
    	public static final int MAX_BOUNTY_AMT = 1000000;//赏金最高金额(1万)
    	public static final int MAX_LOAN_AMT = 1000000000;//最大借款金额(1000万)
    	public static final int SHOW_TELEPHONE_FEE = 100;//查看手机号费用
        public static final int N_WITHDRAW_FEE = 100; // 用户提现手续费,单位分
        public static final int N_RECHARGE_MIN = 50000;// 用户充值最小额度(不支付手续费)单位分
        public static final int N_RECHARGE_FEE = 250; // 用户充值手续费,单位分
        public static final double RECHARGE_FEE_RATE = 0.006; // 用户充值手续费率
    }

    /**
     * 支付业务类型：1-支付认证费；2-购买礼券；3-提现
     * @author xuhongyu
     * @date 2019年4月8日
     */
    public enum PayBusinessType {
        CREDIT_FEE((byte)1, "c恋人认证费", "payCreditFee"),
        BUY_CARD((byte)2, "c恋人购买礼券", "payBuyCard"),
        WITHDRAW((byte)3, "c恋人提现", "payWithdraw");
        
		private byte code; // 定义私有变量
        private String msg; // 定义私有变量
        private String beanName; // 当前类型支付实现类注入到SpringIOC容器中的beanName
        
		// 构造函数，枚举类型只能为私有
		private PayBusinessType(byte code, String msg, String beanName) {
			this.code = code;
			this.msg = msg;
			this.beanName = beanName;
		}
		// 构造函数，枚举类型只能为私有
		public byte getCode() {
			return code;
		}
		// 构造函数，枚举类型只能为私有
		public String getMessage() {
			return msg;
		}
		
		/**
		 * 当前类型支付实现类注入到SpringIOC容器中的beanName
		 * @author xuhongyu
		 * @return
		 */
		public String getBeanName() {
			return beanName;
		}
		
		public static PayBusinessType getPayBusinessType(Byte code) {
			for (PayBusinessType nTmUnit : PayBusinessType.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
		
		public static String getPayBusinessBeanName(Byte code) {
			for (PayBusinessType nTmUnit : PayBusinessType.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit.getBeanName();
				}
			}
			return null;
		}
		
		public static boolean isContain(Byte code) {
			for (PayBusinessType nTmUnit : PayBusinessType.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return true;
				}
			}
			return false;
		}
    }
    
    /**
     * 支付中心现金流动类型(跟今借到不同)：现金流类型：1:代收/代扣, 2:代付
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
     * 支付方式:0.余额  1.银行卡  2-线下 3.银联(收银台类) 4.微信(app类) 5.APP端的微信支付 6.APP支付宝
     * @author xuhongyu
     * @version 创建时间：2018年6月30日 下午4:06:04
     */
    public enum PayMethod {
    	YUE((byte)0),// 余额
        YINHANGKA((byte)1), // 银行卡
        OFF_LINE((byte)2), // 线下
        YINLIAN((byte)3), // 银联(收银台类)
        WECHAT((byte)4), // 微信
        APP_WECHAT((byte)5), // APP端的微信支付
        APP_ZFB((byte)6); // APP支付宝
    	
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
		
		public static boolean isContain(Byte code) {
			for (PayMethod nTmUnit : PayMethod.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return true;
				}
			}
			return false;
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
    	CANCEL(5),//订单作废(第三方过期或关闭)
    	ABANDON(6);//用户主动放弃支付(点击‘x’号)
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
    	RCV_RECHARGE((byte)1, "收到充值的钱"),
    	PAY_RECHARGE((byte)2, "支出充值的钱"),
    	RCV_RECHARGE_FEE((byte)3, "收到充值手续费"),
    	PAY_RECHARGE_FEE((byte)4, "支出充值手续费"),
    	
    	RCV_WITHDRAWALS((byte)5, "收到提现的钱"),
    	PAY_WITHDRAWALS((byte)6, "支出提现的钱"),
    	RCV_WITHDRAW_FEE((byte)7, "收到提现手续费"),
    	PAY_WITHDRAW_FEE((byte)8, "支出提现手续费"),
    	RCV_ADVANCE_FEE((byte)9, "收到垫付手续费"),
    	PAY_ADVANCE_FEE((byte)10, "支出垫付手续费"),
    	
        RCV_CREDIT_FEE((byte)11, "收到信用认证费"),
        PAY_CREDIT_FEE((byte)12, "支付信用认证费"),
        
        RCV_BUG_CARD((byte)13, "收到购买礼券费用"),
        PAY_BUG_CARD((byte)14, "支付购买礼券费用");
    	

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
     * 认证类型：1-基础认证 2-二级认证
     * @author xuhongyu
     * @date 2019年4月26日
     */
    public enum CreditType {
        BASIC_CREDIT((byte)1,"基础认证"),
        SECOND_CREDIT((byte)2,"二级认证");
    	
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		// 构造函数，枚举类型只能为私有
		private CreditType(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		public byte getCode() {
			return code;
		}
		public String getMessage() {
			return msg;
		}
		public static CreditType getCreditType(Byte code) {
			for (CreditType nTmUnit : CreditType.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
    }
    
    /**
     * 礼券购买类型
     * @author xuhongyu
     * @date 2019年9月4日
     */
    public enum BuyCardType {
        ONE((byte)1,1,900),//1张9元
        FIVE((byte)2,5,3990),//5张40元
    	TEN((byte)3,10,6990),//10张70元
    	TWENTY((byte)4,20,11990);//20张120元
    	
		private byte code; // 类型
		private Integer cardNum; // 礼券数
		private Integer amount; // 金额(分)
		
		private BuyCardType(byte code, Integer cardNum, Integer amount) {
			this.code = code;
			this.cardNum = cardNum;
			this.amount = amount;
		}
		public byte getCode() {
			return code;
		}
		public Integer getCardNum() {
			return cardNum;
		}
		public Integer getAmount() {
			return amount;
		}

		public static BuyCardType getBuyCardType(Byte code) {
			for (BuyCardType nTmUnit : BuyCardType.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
		
		/**
		 * 金额和购买礼券数是否匹配
		 * @author xuhongyu
		 * @param cardNum
		 * @param amount
		 * @return
		 */
		public static Boolean isMatch(Integer cardNum,Integer amount) {
			for (BuyCardType nTmUnit : BuyCardType.values()) {
				if (cardNum.equals(nTmUnit.getCardNum())) {
					if(amount.equals(nTmUnit.getAmount())) {
						return true;
					}else {
						return false;
					}
				}
			}
			return false;
		}
    }
}
