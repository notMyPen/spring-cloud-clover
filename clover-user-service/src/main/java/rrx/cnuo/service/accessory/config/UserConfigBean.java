package rrx.cnuo.service.accessory.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "app.user")
public class UserConfigBean {
    
    private String reconciliationNotifyOpenids;//对账后，将账单异常的统计信息发送到如下微信号中
    private String testString;//测试用
}
