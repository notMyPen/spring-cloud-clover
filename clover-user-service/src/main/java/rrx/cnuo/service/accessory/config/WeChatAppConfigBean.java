package rrx.cnuo.service.accessory.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 微信公众号相关配置
 * @author xuhongyu
 * @date 2019年7月8日
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "app.wxapp")
public class WeChatAppConfigBean {

	private String wechatAppid;
    private String wechatAppsecret;
    private String wechatToken;
    private String wechatMsgToken;
    
}
