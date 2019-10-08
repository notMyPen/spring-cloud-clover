package rrx.cnuo.service.accessory.consts;

/**
 * 用户操作类型及状态枚举
 * @author xuhongyu
 * @date 2019年8月28日
 */
public class HandlerConst {

	/**
	 * 审核状态(自动和手动)：0未提交信息，1待审核(未进行信用认证而需人工审核时)，2审核通过，3审核未通过
	 * @author xuhongyu
	 * @date 2019年8月28日
	 */
	public enum AuditStatus{
		NotSubmitted((byte)0,"未提交"),
		ToBeAudited((byte)1,"待审核"),
    	PASS((byte)2,"审核通过"),
		NOT_PASS((byte)3,"审核未通过");
    	
		private byte code; // 定义私有变量
		private String msg; // 定义私有变量
		
		// 构造函数，枚举类型只能为私有
		private AuditStatus(Byte code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public byte getCode() {
			return code;
		}
		
		public String getMessage() {
			return msg;
		}

		public static AuditStatus getAuditStatus(Byte code) {
			for (AuditStatus nTmUnit : AuditStatus.values()) {
				if (code.equals(nTmUnit.getCode())) {
					return nTmUnit;
				}
			}
			return null;
		}
	}
}
