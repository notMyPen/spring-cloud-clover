package rrx.cnuo.cncommon.vo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 基础配置
 * @author xuhongyu
 * @date 2019年7月8日
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "app.basic")
public class BasicConfig {

	private boolean sendSMS;// 是否真发短信
	private boolean payByService;// 是否调用支付中心
	private boolean realReconciliations;// 是否真实对账
	private boolean prodEnvironment;// 是否是生产环境
//	private String aesEncryptKey; // aes加密算法秘钥
	private Integer snowflakeNode; // 雪花算法节点名
	private String systemName; //系统名称
	
}
