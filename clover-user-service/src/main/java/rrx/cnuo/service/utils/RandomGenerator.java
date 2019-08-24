package rrx.cnuo.service.utils;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机字符串工具类（废弃可删除）
 * @author xuhongyu
 * @date 2019年6月18日
 */
public class RandomGenerator {
    private static String charCollection = "6g1rYKSs4hg6a83er4gs6rYUWEh5r7e28hk6pg9UQkd0PNs";
//    private static Random random = new Random();

    /**
     * 获指定长度的随机字符串
     * @author xuhongyu
     * @param len
     * @return
     */
    public static String generateRandomString( int len ) {
        if ( len <= 0 ) return "";
        StringBuilder sb = new StringBuilder(len);
        int size = charCollection.length();
        Random random = ThreadLocalRandom.current();
        for ( int i = 0 ; i < len; i++ ) {
            int index = random.nextInt(size);
            sb.append( charCollection.charAt(index) );
        }
        return sb.toString();
    }
    
    public static String generateVarifyCode() {
        return generateNumerString(4);
	}

    public static String generateNumerString(int len) {
        if ( len<=0 ) return "";
        String numberCollection = "1234567890";
        StringBuilder sb = new StringBuilder(len);
        int size = numberCollection.length();
        Random random = ThreadLocalRandom.current();
        for ( int i = 0 ; i < len; i++ ) {
            int index = random.nextInt(size);
            sb.append( numberCollection.charAt(index) );
        }
        return sb.toString();
    }
    
    /**
     * 需要保证cookie不能重复
     * @return
     */
    public static String getCookieString() {
        String tmp = UUID.randomUUID().toString().replace("-","");
        return tmp+generateRandomString(32);
    }

}
