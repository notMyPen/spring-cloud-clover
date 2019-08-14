package rrx.cnuo.cncommon.accessory.config;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Feign;
import okhttp3.ConnectionPool;

/**
 * 用okhttp替换feign默认的http(没有连接池)的自定义个性化设置
 * @author xuhongyu
 * @date 2019年8月9日
 */
@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class FeignOkHttpConfig {
	
	@Bean
	public okhttp3.OkHttpClient okHttpClient(){
		return new okhttp3.OkHttpClient.Builder()
			//设置连接超时时间
			.connectTimeout(60, TimeUnit.SECONDS)
			//设置读超时时间
			.readTimeout(60, TimeUnit.SECONDS)
			//设置写超时时间
			.writeTimeout(60, TimeUnit.SECONDS)
			//是否自动重连
			.retryOnConnectionFailure(true)
			.connectionPool(new ConnectionPool())
			.build();
	}
	
}
