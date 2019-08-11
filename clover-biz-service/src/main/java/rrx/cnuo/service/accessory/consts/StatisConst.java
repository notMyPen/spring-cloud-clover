package rrx.cnuo.service.accessory.consts;

/**
 * 统计相关常量及枚举
 * @author xuhongyu
 * @date 2019年4月2日
 */
public class StatisConst {

	/**
	 * 用户统计表user_statis的name(key)类型
	 * @author xuhongyu
	 * @date 2019年4月1日
	 */
    public enum UserStatisName {
    	RELEASE_APPLY_LOAN_USER_CNT(1), //用户发布的所有产品中申请贷款人数
    	RELEASE_SEE_PHONE_USER_CNT(2), //用户发布的所有产品中查看电话人数
    	RELEASE_DEAL_USER_CNT(3), //用户发布的所有产品中成交人数
    	
    	FORWARD_APPLY_LOAN_USER_CNT(4), //用户帮忙转发所有借款标的里实际申请借款的人数
    	FORWARD_SEE_PHONE_USER_CNT(5), //用户帮忙转发所有借款标的里(申请借款中)实际查看电话的人数
    	FORWARD_DEAL_USER_CNT(6), //用户帮忙转发所有借款标的里(申请借款中)实际确认成交的人数
    	FORWARD_USER_INCOME(7); //用户收入(单位元)：推荐用户确定成交后获得的钱
    	
    	private short code;// 定义私有变量
    	// 构造函数，枚举类型只能为私有
    	private UserStatisName(int code){
    		this.code = (short) code;
    	}
    	public short getCode(){
    		return this.code;
    	}
    }
    
    /**
     * 系统统计表system_statis_item的业务类型(type)
     * @author xuhongyu
     * @date 2019年4月2日
     */
    public enum SystemStatisItemType{
    	PROD(1);//产品(标的)信息统计
    	
    	private short code;// 定义私有变量
    	// 构造函数，枚举类型只能为私有
    	private SystemStatisItemType(int code){
    		this.code = (short) code;
    	}
    	public short getCode(){
    		return this.code;
    	}
    }
    
    /**
     * 系统统计表system_statis_item的统计项key(statis_item_id)
     * @author xuhongyu
     * @date 2019年4月2日
     */
    public enum SystemStatisItemKey{
    	PROD_FORWARD_USER_CNT(1),//产品(标的)的转发人数
    	SEE_PHONE_USER_CNT(2),//产品的查看电话人数
    	DEAL_USER_CNT(3),//产品的成交人数
    	APPLY_LOAN_USER_CNT(4);//产品的申请贷款人数
    	
    	private short code;// 定义私有变量
    	// 构造函数，枚举类型只能为私有
    	private SystemStatisItemKey(int code){
    		this.code = (short) code;
    	}
    	public short getCode(){
    		return this.code;
    	}
    }
}
