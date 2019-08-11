package rrx.cnuo.service.accessory.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "app.pay-center")
public class PayCenterConfigBean {

    private String payBaseUrl; //支付中心url
    private String apiKey; //支付中心分配,验证请求合法性
    private String apiSecret; //支付中心分配,验证请求合法性
    private String merchantSign; //支付中心分配,商户标识
    private String notifySecret; //支付中心分配,通知配置
    private String wechatPayRequestTypeQuery; //微信支付通道路由
    private String payCollectionApply; //支付申请
    private String payCollectionConfirm; //支付确认
    private String payByBalance; //余额支付
    private String payOrderQuery; //订单查询
    private String payPaymentConfirm; //代付(提现、退款)
    private String payReconciliation; //对账单
    private String reconciliationDate; //对账日期
    private String payQueryAccountBalanceInfo; //获取用户余额
    private String payAddAccountBalanceInfo; //开户
    private String testString; //测试用
	
}
