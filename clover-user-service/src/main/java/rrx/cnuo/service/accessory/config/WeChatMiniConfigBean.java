package rrx.cnuo.service.accessory.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 微信小程序相关配置
 * @author xuhongyu
 * @date 2019年7月8日
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "app.wxmini")
public class WeChatMiniConfigBean {

	private String miniAppId;
    private String miniAppSecret;
    private String miniTemplateAuditNotifyId; //小程序审核通知消息模板id
    private String miniTemplateTaskHandleNotifyId; //小程序业务处理消息模板id
    private String pageUrl;
    
}
