package rrx.cnuo.cncommon.util;

import java.math.BigDecimal;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.relops.snowflake.Snowflake;

public class ClientToolUtil {

	private static final String HASHIDS_SALT = "clover-api";

    /**
     * 隐藏手机号中间4位
     */
    public static String hideTel(String str) throws Exception {
        StringBuilder result = new StringBuilder();
        result.append(str.substring(0, 3)).append("****").append(str.substring(7, str.length()));
        return result.toString();
    }
    
    /**
     * 用“*”替换字符串指定位置的substr
     * @author xuhongyu
     * @param str
     * @param start 脱敏开始角标
     * @param end 脱敏结束角标(包括该角标处字符)
     * @return
     * @throws Exception
     */
    public static String replaceWithStar(String str, int start, int end) throws Exception {
        char c = '*';
        if (str == null) {
            return null;
        }
        StringBuffer strbf = new StringBuffer(str);
        for (int i = start; i <= end; i++) {
            strbf.setCharAt(i, c);
        }
        return strbf.toString();
    }
    
    /**
     * 首字母转大写
     * @author xuhongyu
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s){
	  if(Character.isUpperCase(s.charAt(0)))
		 return s;
	  else
	     return new StringBuilder().append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}
    
    /**
     * 获取分布式主键id(非最终方案)
     * @author xuhongyu
     * @param node 节点
     * @return
     */
    public static long getDistributedId(int node){
    	Snowflake snowflake = new Snowflake(node);
    	Long s = snowflake.next();
		/*
		 * Long result = Long.parseLong(s.toString().substring(3)); if(result >=
		 * 9007199254740992L){ return
		 * Long.parseLong(result.toString().substring(1));//在从头截取调一位 }else{ return
		 * result; }
		 */
    	return s;
    }

    /**
     * 把图像数据转换为byte数组 
     */
    public static byte[] convertImageBase64(String imgStr) throws Exception {
        if (imgStr == null) // 图像数据为空
            return null;
        // 去掉 data:image/jpeg;base64,
        int nIndex = imgStr.indexOf(",");
        imgStr = imgStr.substring(nIndex + 1);
        Base64 decoder = new Base64();
        byte[] b = decoder.decode(imgStr);// Base64解码 decodeBuffer(imgStr)
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {// 调整异常数据
                b[i] += 256;
            }
        }
        return b;
    }

    /**
     * 校验是否含有特殊字符
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        boolean isEmoji = false;
        for (int i = 0; i < len; i++) {
            char hs = source.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (source.length() > 1) {
                    char ls = source.charAt(i + 1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
                    return true;
                }
                if (!isEmoji && source.length() > 1 && i < source.length() - 1) {
                    char ls = source.charAt(i + 1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return isEmoji;
    }

    
    /**
     * 生成登录token
     */
    public static String generateToken(String type, String value) throws Exception {
        return type + MD5.MD5Encode(value);
    }
    
    /**
     * md5加密算法
     */
    public static String eccrypt(String info) throws Exception {
        return MD5.MD5Encode(info);
    }

    /**
     * md5加密算法，设置编码格式(utf-8/gbk)
     */
    public static String eccrypt(String info, String charsetName) throws Exception {
        return MD5.MD5Encode(info, charsetName);
    }
    
    /**
     * 整型人民币分转换成元
     */
    public static String FEN2YUAN(Integer fen) throws Exception {
        if (fen == null) {
            return "";
        }
        return new BigDecimal(fen).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).toString();
    }
    
    /**
     * 判断字符串中是否仅包含英文字母、数字和汉字
     * @author xuhongyu
     * @param str
     * @return
     */
	public static boolean isLetterDigitOrChinese(String str) {
		String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";
		return str.matches(regex);
	}
	
	/**
	 * 判断字符串是否只包含数字
	 * @author xuhongyu
	 * @param str
	 * @return
	 */
	public static boolean isDigit(String str) {
		String regex = "^[0-9]+$";
		return str.matches(regex);
	}
	
	/**
     * 对id进行加密
     * @param id
     * @return
     */
    public static String encryptID(long id) throws Exception {
        return new Hashids(HASHIDS_SALT).encode(id);
    }

    /**
     * 对id进行解密
     * @param id
     * @return
     */
    public static long decryptID(String id) throws Exception {
        if (StringUtils.isBlank(id)) {
            return 0;
        }
        long[] llist = new Hashids(HASHIDS_SALT).decode(id);
        if(llist.length>0) {
        	return llist[0];
        }
        return 0;
    }
    
}