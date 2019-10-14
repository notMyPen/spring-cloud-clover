package rrx.cnuo.cncommon.util;

import java.math.BigDecimal;
import java.net.InetAddress;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.relops.snowflake.Snowflake;

public class ClientToolUtil {

	private static final String HASHIDS_SALT = "clover-api";

	/**
	 * 获取本地ip地址
	 * @author xuhongyu
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentIp() throws Exception {
		InetAddress addr = InetAddress.getLocalHost();
		return addr.getHostAddress();
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