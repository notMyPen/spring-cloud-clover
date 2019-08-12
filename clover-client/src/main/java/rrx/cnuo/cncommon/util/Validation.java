package rrx.cnuo.cncommon.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串验证
 * Title: Validation.java
 * Description
 * @author 
 * @date 2015年6月17日
 * @version 1.0
 */
public class Validation {
	
   	/**
 	 * 验证输入的固定电话号码是否符合
 	 * @param faxNum
 	 * @return 是否合法   true为座机  false：不为座机
 	 */
 	public static boolean isPhone(String faxNum) throws Exception
 	{
 		boolean tag = true;
 		final String regex = "0[0-9]{2,3}[2-9][0-9]{6,7}[0-9]{1,4}$";
 		final Pattern pattern = Pattern.compile(regex);
 		final Matcher mat = pattern.matcher(faxNum);
 		if (!mat.find())
 		{
 			tag = false;
 		}
 		return tag;
 	}
 	
 	/**
	 * 验证输入的邮箱格式是否符合
	 * @param email
	 * @return 是否合法
	 */
	public static boolean isEmail(String email) throws Exception
	{
		boolean tag = true;
		final String regex = "^(\\w+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		final Pattern pattern = Pattern.compile(regex);
		final Matcher mat = pattern.matcher(email);
		if (!mat.find())
		{
			tag = false;
		}
		return tag;
	}
	
	/**
	 * 验证输入的手机号码是否符合
	 * @param mobiles
	 * @return
	 * @author 
	 */
	public static boolean isMobileNO(String mobiles) throws Exception {
		if(StringUtils.isBlank(mobiles) || mobiles.length()!=11 || !mobiles.startsWith("1")){
			return false;
		}
        return true;       
    }
	
	/**
	 * 检查字符串是否全是数字
	 * @param  str  
	 * @return  如果全是数字返回true否则返回false
	 */
	public static boolean isDigit(String str) throws Exception {
		 char[] ch = str.toCharArray();
		 for (int i = 0; i < ch.length; i++) {
		   if (!Character.isDigit(ch[i])) {
			 return false;
		  }
	    }
		return  true;
	}  
	/**
 	 * 验证邮政编码是否符合
 	 * @param User_Zipcode
 	 * @return 是否合法
 	 */
 	public static boolean isZipcode(String User_Zipcode) throws Exception
 	{
 		boolean tag = true;
 		final String regex = "^\\d{6}$";
 		final Pattern pattern = Pattern.compile(regex);
 		final Matcher mat = pattern.matcher(User_Zipcode);
 		if (!mat.find())
 		{
 			tag = false;
 		}
 		return tag;
 	}
 
}
