package rrx.cnuo.cncommon.utils;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import rrx.cnuo.cncommon.vo.config.WeChatMiniConfig;

public class StarterToolUtil {

//	private static final String HASHIDS_SALT = "xdd-api";
	
	//统计核心线程数
	private static int STATIS_CORE_POOL_SIZE = 3;
	
	// 线程池最大线程数(核心线程数+非核心线程数)
	private static int MAX_POOL_SIZE = 20;
	
	// 非核心线程的闲置后的生命周期
	private static int KEEP_ALIVE_TIME = 30 * 60;
	
	//队列容量
	private static int STATIS_QUEUE_CAPACITY = 128;
	
	//定时查询回盘核心线程数
//	private static int PAY_CORE_POOL_SIZE = 5;
	
	//自动成交核心线程数
	private static int AUTO_DEAL_CORE_POOL_SIZE = 1;
	
	/**
	 * 线程池提交任务时先会创建核心线程，如果核心线程不够了，则会将任务塞到队列中；如果队列也满了，则开始创建非核心线程处理任务； 如果非核心线程也不够用了，则新来的任务会进入CallerRunsPolicy处理
	 */
	public static final ThreadPoolExecutor statisThreadPool = new ThreadPoolExecutor(STATIS_CORE_POOL_SIZE, 
			MAX_POOL_SIZE, 
			KEEP_ALIVE_TIME, 
			TimeUnit.SECONDS, 
			new LinkedBlockingQueue<>(STATIS_QUEUE_CAPACITY), 
			new ThreadPoolExecutor.CallerRunsPolicy());
	
	/*public static final ThreadPoolExecutor payThreadPool = new ThreadPoolExecutor(PAY_CORE_POOL_SIZE, 
			MAX_POOL_SIZE, 
			KEEP_ALIVE_TIME, 
			TimeUnit.SECONDS, 
			new LinkedBlockingQueue<>(STATIS_QUEUE_CAPACITY), 
			new ThreadPoolExecutor.CallerRunsPolicy());*/
	
	public static final ThreadPoolExecutor autoDealThreadPool = new ThreadPoolExecutor(AUTO_DEAL_CORE_POOL_SIZE, 
			MAX_POOL_SIZE, 
			KEEP_ALIVE_TIME, 
			TimeUnit.SECONDS, 
			new LinkedBlockingQueue<>(STATIS_QUEUE_CAPACITY), 
			new ThreadPoolExecutor.CallerRunsPolicy());
	
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
    
    /**
     * 获取页面跳转url
     * @param targetUrl
     * @return
     * @throws Exception
     */
    public static String getRedirectUrl(String targetUrl,WeChatMiniConfig weChatMiniConfig) throws Exception {
        return "https://open.weixin.qq.com/connect/oauth2/authorize?" + "appid=" + weChatMiniConfig.getMiniAppId()
                + "&redirect_uri=" + weChatMiniConfig.getPageUrl() + "&response_type=code&scope=snsapi_userinfo&state=" + targetUrl + "#wechat_redirect";
    }
    
}