package rrx.cnuo.service.accessory.consts;

/**
 * 桃花牌相关枚举
 * @author xuhongyu
 * @date 2019年8月29日
 */
public class BoardConst {

	public class REDIS_PREFIX {
		public static final String UNREAD_LIKEME_CNT = "data:unread_likeme_cnt:";
		public static final String UNREAD_VIEWME_CNT = "data:unread_viewme_cnt:";
		public static final String UNREAD_TURNBOARD_CNT = "data:unread_turnboard_cnt:";
	}
	
	/**
	 * 比心状态：0-不喜欢、1-喜欢
	 * @author xuhongyu
	 * @date 2019年8月29日
	 */
	public enum BixinStatus {
		UNLIKE((byte)0),
        LIKE((byte)1);
    	
		private byte code; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private BixinStatus(Byte code) {
			this.code = code;
		}

		public byte getCode() {
			return code;
		}
    }
	
	/**
	 * 认证类型
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum CreditType{
		CREDIT(0,"信用认证"),
		BASIC(1,"信用认证"),
		ZHIMA(2,"芝麻信用"),
//		Dependence(3,"长伴依赖"),
		XUEXIN(4,"学信"),
		ZHENGXIN(5,"人行征信"),
		JOB(6,"工作认证"),
		INCOME(7,"收入认证"),
		CAR(8,"车辆认证"),
		HOUSE(9,"房屋认证"),
		JINGDONG(11,"京东认证"),
		SHEBAO(12,"社保认证"),
		GJJ(13,"公积金认证"),
		LOCATION(15,"定位"),
		FACE_INFO(16,"人脸识别"),
		TWO_ELEMENT_INFO(19,"二要素认证"),
		DISHONEST_INFO(20,"高法失信认证"),
		ALIDATA_INFO(21,"阿里电商（支付宝和淘宝--天创认证支付宝成功，淘宝也会成功）");
		
		private Integer code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private CreditType(Integer code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public Integer getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static CreditType getCreditType(Integer code) {
			for (CreditType nTmUnit : CreditType.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
	
	/**
	 * 保存用户各项认证状态（状态分类：0.未认证 1.待完善 2.认证中 3.认证成功 4.认证失败 5.已过期）
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum CreditStatus{
		NO_CREDIT(0,"未认证"),
		TO_FINISH(1,"待完善"),
		IN_CREDITING(2,"认证中"),
		IS_SUCCESS(3,"认证成功"),
		IS_FAIL(4,"认证失败"),
		IN_EXPIRE(5,"已过期");
		
		private Integer code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private CreditStatus(Integer code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public Integer getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public static CreditStatus getCreditStatuse(Integer code) {
			for (CreditStatus nTmUnit : CreditStatus.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
}
