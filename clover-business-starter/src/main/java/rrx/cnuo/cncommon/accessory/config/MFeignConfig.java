package rrx.cnuo.cncommon.accessory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
import rrx.cnuo.cncommon.accessory.intercepter.FeignGetRequestParamInterceptor;

/**
 * Feign的配置类，可以配置其Encoder、Decoder、LogLevel、Contract
 * 这里配置了feign的打印日志等级
 * @author xuhongyu
 * @date 2019年6月27日
 */
@Configuration
public class MFeignConfig {

	@Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
	
	@Bean
	public FeignGetRequestParamInterceptor feignGetRequestParamInterceptor() {
		return new FeignGetRequestParamInterceptor();
	}
}
