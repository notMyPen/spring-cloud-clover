package rrx.cnuo.cncommon.accessory.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import rrx.cnuo.cncommon.vo.config.BasicConfig;
import rrx.cnuo.cncommon.vo.config.WeChatAppConfig;
import rrx.cnuo.cncommon.vo.config.WeChatMiniConfig;

/**
 * 全局配置
 * @author xuhongyu
 * @date 2019年8月9日
 */
@Component
@RefreshScope	
public class GlobalBeanConfig {
	
	@Value("${app.basic.sendSMS}")
    private boolean sendSMS;
	@Value("${app.basic.payByService}")
	private boolean payByService;
	@Value("${app.basic.realReconciliations}")
	private boolean realReconciliations;
	@Value("${app.basic.prodEnvironment}")
	private boolean prodEnvironment;
//	@Value("${app.basic.aesEncryptKey}")
//	private String aesEncryptKey;
	@Value("${app.basic.snowflakeNode}")
	private Integer snowflakeNode;
	
	@Bean
	public BasicConfig basicConfig(){
		BasicConfig basicConfig = new BasicConfig();
//		basicConfig.setAesEncryptKey(aesEncryptKey);
		basicConfig.setPayByService(payByService);
		basicConfig.setProdEnvironment(prodEnvironment);
		basicConfig.setRealReconciliations(realReconciliations);
		basicConfig.setSnowflakeNode(snowflakeNode);
		basicConfig.setSendSMS(sendSMS);
		return basicConfig;
	}
	
	@Value("${app.wxapp.wechatAppid}")
	private String wechatAppid;
	@Value("${app.wxapp.wechatAppsecret}")
	private String wechatAppsecret;
	@Value("${app.wxapp.wechatToken}")
	private String wechatToken;
	@Value("${app.wxapp.wechatMsgToken}")
	private String wechatMsgToken;
	
	@Bean
	public WeChatAppConfig weChatAppConfig(){
		WeChatAppConfig weChatAppConfig = new WeChatAppConfig();
		weChatAppConfig.setWechatAppid(wechatAppid);
		weChatAppConfig.setWechatAppsecret(wechatAppsecret);
		weChatAppConfig.setWechatMsgToken(wechatMsgToken);
		weChatAppConfig.setWechatToken(wechatToken);
		return weChatAppConfig;
	}
	
	@Value("${app.wxmini.miniAppId}")
	private String miniAppId;
	@Value("${app.wxmini.miniAppSecret}")
	private String miniAppSecret;
	@Value("${app.wxmini.miniTemplateAuditNotifyId}")
	private String miniTemplateAuditNotifyId;
	@Value("${app.wxmini.miniTemplateTaskHandleNotifyId}")
	private String miniTemplateTaskHandleNotifyId;
	@Value("${app.wxmini.pageUrl}")
	private String pageUrl;
	
	@Bean
	public WeChatMiniConfig weChatMiniConfig(){
		WeChatMiniConfig weChatMiniConfig = new WeChatMiniConfig();
		weChatMiniConfig.setMiniAppId(miniAppId);
		weChatMiniConfig.setMiniAppSecret(miniAppSecret);
		weChatMiniConfig.setMiniTemplateAuditNotifyId(miniTemplateAuditNotifyId);
		weChatMiniConfig.setMiniTemplateTaskHandleNotifyId(miniTemplateTaskHandleNotifyId);
		weChatMiniConfig.setPageUrl(pageUrl);
		return weChatMiniConfig;
	}
	
	/*@Bean
	@ConfigurationProperties(prefix = "app.threadPool")
    public ThreadPoolTaskExecutor globalTaskExecutor(Integer corePoolSize,Integer maxPoolSize,Integer keepAliveTime,Integer queueCapacity) {
		new ThreadPoolExecutor(corePoolSize, 
				maxPoolSize, 
				keepAliveTime, //非核心线程的闲置后的生命周期
				TimeUnit.SECONDS, 
				new LinkedBlockingQueue<>(queueCapacity), 
				new ThreadPoolExecutor.CallerRunsPolicy());
		
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadGroupName("globalTaskExecutor");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }*/
}
