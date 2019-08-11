package rrx.cnuo.service.accessory.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "app.msg-center")
public class MsgCenterConfigBean {

    private String baseURL; //消息中心地址
    private String wechatSendUrl; //发微信消息
    private String smsSendUrl; //发短信消息
}
