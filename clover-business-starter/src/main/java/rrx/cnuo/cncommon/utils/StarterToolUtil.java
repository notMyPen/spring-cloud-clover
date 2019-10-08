package rrx.cnuo.cncommon.utils;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class StarterToolUtil {

//	private static final String HASHIDS_SALT = "xdd-api";
	
    /**
     * 校验ip地址是否合法
     * @author xuhongyu
     * @param request
     * @return
     */
    /*public static boolean checkIPValid(HttpServletRequest request) {
        try {
            if (!GlobalBeanConfig.isCheckIpValid()) {
                return true;
            }
            String ip = getRemoteIP(request);
            if (!GlobalBeanConfig.getAccessibleIps().contains(ip)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }*/

    
    public static String generatorStringId(RedisTool instance) {
        final String order_id_prefix = "CLOVER_PIC";
        Date dt = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//SSS
        String tm = df.format(dt);
        long temp = instance.increase(order_id_prefix);
        int random = (int) (Math.random() * 9 + 1);
        int tail = (int) (temp % 1000);

        int tail_000 = tail / 100;
        int tail_00 = (tail % 100) / 10;
        int tail_0 = tail % 10;
        String str = "" + tail_000 + random + tail_00 + tail_0;
        return tm + str;
    }
    
    /**
     * 获取远端ip
     * @param request
     * @return
     */
    public static String getRemoteIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null || request.getHeader("x-forwarded-for").equals("")) {
            return request.getRemoteAddr();
        }
        String xforward = request.getHeader("x-forwarded-for");
        String[] temp = xforward.split(",");
        String ip = null;
        for (int i = 0; i < temp.length; i++) {
            if (!temp[i].equalsIgnoreCase("unknown")) {
                ip = temp[i];
                break;
            }
        }
        return ip;
    }
    
    /**
     * 获取当前机器ip地址
     * @return
     * @throws Exception
     */
    public static String getCurrentIp() throws Exception {
        InetAddress addr = InetAddress.getLocalHost();
        return addr.getHostAddress();
    }
    
    /**
     * 获取远端的操作系统
     * @author xuhongyu
     * @param request
     * @return
     * @throws Exception
     */
    public static String getRemoteSystem(HttpServletRequest request) throws Exception {
        String Agent = request.getHeader("User-Agent");
        String system = null;
        if (Agent == null) {
            return system;
        }
        if (Agent.toLowerCase().contains("iphone")) {
            system = "iphone";
        }
        if (Agent.toLowerCase().contains("android")) {
            system = "android";
        }
        if (Agent.toLowerCase().contains("micromessenger")) {//微信客户端
            system = "weixin";
        }
        return system;
    }
}