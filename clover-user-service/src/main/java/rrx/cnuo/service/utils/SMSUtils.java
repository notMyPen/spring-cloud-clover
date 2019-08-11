package rrx.cnuo.service.utils;

public class SMSUtils {
	
	public static final String sysSign = "今找到";
	
	/**
	 * 根据业务类型组装发送短信的内容
	 * @param mobileCode 短信验证码
	 * @param nType 0.注册验证码 1.申请贷款 2.修改交易密码 3.余额支付4.提现 5.找回密码 6.非注册获取验证码 7.原样发送8.充值9.重置密码10.好友找回
	 * @param args 短信内容参数
	 */
	public static String getSendSMSContent(int mobileCode, int nType, String... args) throws Exception {
		String msg=null;// 短信内容
		switch(nType){
			case 0:
				msg = "您的验证码是" + mobileCode 
						+ "，此验证码用于登录"+sysSign+"或注册，5分钟内有效，如非本人操作，请忽略";// 短信内容
				break;
			case 1:
				msg = "您正在"+sysSign+"上进行贷款申请，验证码为"+mobileCode
						+ "，请在5分钟内完成申请。验证码请勿泄漏，如非本人操作，请忽略";
				break;
			case 2:
				msg = "您的"+sysSign+"正在申请修改交易密码,验证码:"+mobileCode
						+",5分钟内有效.泄露验证码会影响您的资金安全.如非本人操作,请及时修改密码或速致电0472-2284646";// 短信内容
				break;
			case 3:
				if(args[0]!=null){
					msg = "您的"+sysSign+"正在发生"+args[0]+"元的交易，快捷支付验证码："+mobileCode
							+"，泄露验证码会影响您的资金安全。如非本人操作，请及时修改密码或请速致电0472-2284646";// 短信内容
				}else{
					msg = "您的"+sysSign+"正在发生交易，快捷支付验证码："+mobileCode
							+"，泄露验证码会影响您的资金安全。如非本人操作，请及时修改密码或请速致电0472-2284646";// 短信内容
				}
				break;
			case 4:
				if(args[0]!=null){
					msg = "您的"+sysSign+"正在发生"+args[0]+"元的提现，快捷支付验证码："+mobileCode
							+"，泄露验证码会影响您的资金安全。如非本人操作，请及时修改密码或请速致电0472-2284646";// 短信内容
				}else{
					msg = "您的"+sysSign+"正在发生提现，快捷支付验证码："+mobileCode
							+"，泄露验证码会影响您的资金安全。如非本人操作，请及时修改密码或请速致电0472-2284646";// 短信内容
				}
				break;
			case 5:
				msg = "您的"+sysSign+"账号正在进行找回密码，验证码"+mobileCode
						+ "，请在5分钟内完成找回操作。验证码请勿泄漏，如非本人操作，请忽略";
				break;
			case 6:
				msg = "您的手机验证码是" + mobileCode + "，5分钟有效。如非本人操作，请忽略。";
				break;
			case 7:
				msg = args[0];
				break;
			case 8:
				msg = "您的"+sysSign+"正在发生充值交易，支付验证码："+mobileCode
						+"，泄露验证码会影响您的资金安全。如非本人操作，请及时修改密码或请速致电0472-2284646";// 短信内容
				break;
			case 9:
				msg = mobileCode+"("+sysSign+"重置密码验证码),请在页面完成重置，5分钟有效。"
						+ "如非本人操作，请忽略.";// 短信内容
				break;
			case 10:
				msg = "验证码：" + mobileCode + "，您的好友" + args[0] +"正通过"+sysSign+"信用认证服务将您添加为紧急联系人，"
						+ "请在10分钟内告知您的好友，如有异议或不同意此操作，请忽略此短信。";
				break;
			case 11:
				msg = "此验证码仅用于找回账号，请勿泄露。您的手机验证码是" + mobileCode + "，5分钟有效。";
				break;
			default:
				return null;
		}
		return msg;
	}
	
	/**
	 * 获取一定长度的随机数当验证码
	 * @author xuhongyu
	 * @param isSendSMS 是否真发验证码 
	 * @return
	 * @throws Exception
	 */
	public static int getRadomCode(boolean isSendSMS) throws Exception {
		if(!isSendSMS){
			return 123456;
		}else{
			String numStr = "1000000000";
			int num = Integer.parseInt(numStr.substring(0, 6));
			return (int)((Math.random() * 9 + 1) * num);
		}
	}
}
