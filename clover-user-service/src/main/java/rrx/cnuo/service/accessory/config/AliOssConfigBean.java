package rrx.cnuo.service.accessory.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "app.ali-oss")
public class AliOssConfigBean {

    private String aliyunAccesskeyId;
    private String aliyunAccesskeySecret;
    private String ossBucketUrl;
    private String ossWxappBucketName;
    private String picCdnUrl;
    private String picCndPath;
}
