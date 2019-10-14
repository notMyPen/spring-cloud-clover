package rrx.cnuo.service.accessory.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "app.credit-center")
public class CreditCenterCofingBean {

	private String creditBaseUrl;
	private String creditUserRegister;
	private String faceVerifyGetLiveToken;
	private String submitLoginParams;
	private String queryGrabStatus;
	private String submitCaptcha;
	private String getStudentCaptcha;
	private String getCheckNum;
	private String sendOssKeyToCredit;
	private String getFaceCheckResult;
	private String getStudentInfo;
	private String getInfo;
}
