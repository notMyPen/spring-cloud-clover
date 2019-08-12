package rrx.cnuo.cncommon.accessory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import rrx.cnuo.cncommon.utils.SpringUtil;

/**
 * 将SpringUtil放到ioc容器中
 * @author xuhongyu
 * @date 2019年7月9日
 */
@Configuration
public class SpringBeanAutoConfig {

    @Bean("springUtil")
    public SpringUtil springUtil(){
        return new SpringUtil();
    }
}
