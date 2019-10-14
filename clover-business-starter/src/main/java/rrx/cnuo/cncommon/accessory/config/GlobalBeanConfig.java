package rrx.cnuo.cncommon.accessory.config;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import lombok.Data;
import rrx.cnuo.cncommon.vo.config.AliOssConfigBean;
import rrx.cnuo.cncommon.vo.config.BasicConfig;

/**
 * 全局配置
 * @author xuhongyu
 * @date 2019年8月9日
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "app.thread-pool")
public class GlobalBeanConfig {
	
	private Integer corePoolSize;
	private Integer maxPoolSize;
//	private Integer keepAliveTime;
	private Integer queueCapacity;
	
	@Bean
	public BasicConfig basicConfig(){
		return new BasicConfig();
	}
	
	@Bean
	public AliOssConfigBean aliOssConfigBean(){
		return new AliOssConfigBean();
	}
	
	/**
	 * 线程池提交任务时先会创建核心线程，如果核心线程不够了，则会将任务塞到队列中；如果队列也满了，则开始创建非核心线程处理任务； 如果非核心线程也不够用了，则新来的任务会进入CallerRunsPolicy处理
	 * @author xuhongyu
	 * @param corePoolSize 核心线程数
	 * @param maxPoolSize 线程池最大线程数(核心线程数+非核心线程数)
	 * @param keepAliveTime 非核心线程的闲置后的生命周期
	 * @param queueCapacity 队列容量
	 * @return
	 */
	@Bean
    public ThreadPoolTaskExecutor globalTaskExecutor() {
//		new ThreadPoolExecutor(corePoolSize, 
//				maxPoolSize, 
//				keepAliveTime, //非核心线程的闲置后的生命周期
//				TimeUnit.SECONDS, 
//				new LinkedBlockingQueue<>(queueCapacity), 
//				new ThreadPoolExecutor.CallerRunsPolicy());
		
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadGroupName("globalTaskExecutor");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
