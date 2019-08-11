package rrx.cnuo.service.utils;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.cncommon.util.DateUtil;
import rrx.cnuo.cncommon.util.http.HttpsClient;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.cncommon.vo.config.WeChatMiniConfig;

@Component
public class SendMsgUtil {

    private static final String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=";

    private static void sendMiniTemplateMSG(String message, String url) {
    	try {
    		HttpsClient.doPost(url, message);
    	} catch (Exception ignored) {}
    }

    /**
     * 推送审核通知消息
     * @param openId       //用户openId
     * @param msg //审核说明
     * @param form_id      //表单Id
     * @param page         消息跳转路由
     */
    public static void sendAuditNotify(RedisTool redisTool, String openId, String msg, String page, String form_id,WeChatMiniConfig weChatMiniConfig) throws Exception {
        String token = AccessToken.getToken(redisTool, weChatMiniConfig.getMiniAppId(), weChatMiniConfig.getMiniAppSecret());
        JSONObject keyword1 = new JSONObject();
        keyword1.put("value", msg);
        JSONObject keyword2 = new JSONObject();
        keyword2.put("value", DateUtil.format(new Date(), DateUtil.TIME_YMD_HMS));
        
        JSONObject data = new JSONObject();
        data.put("keyword1", keyword1);
        data.put("keyword2", keyword2);
        JSONObject param = new JSONObject();
        param.put("touser", openId);
        param.put("template_id", weChatMiniConfig.getMiniTemplateAuditNotifyId());
        if (StringUtils.isNotBlank(page)) {
            param.put("page", page);
        }
        param.put("data", data);
        param.put("form_id", form_id);
        sendMiniTemplateMSG(param.toJSONString(), url + token);
    }

    /**
     * 推送收益到账通知消息
     * @param openId      //用户openId
     * @param productName //产品名称
     * @param amount      //收益金额
     * @param msg        //消息内容
     * @param form_id     //表单Id
     * @param page        消息跳转路由
     */
    public static void sendIncomeToAccountNotify(RedisTool redisService, String openId,String amount, String page, String msg, 
    		String form_id,WeChatMiniConfig weChatMiniConfig) throws Exception {
        String token = AccessToken.getToken(redisService, weChatMiniConfig.getMiniAppId(), weChatMiniConfig.getMiniAppSecret());
        JSONObject keyword1 = new JSONObject();
        keyword1.put("value", "今找到");
        JSONObject keyword2 = new JSONObject();
        keyword2.put("value", amount);
        JSONObject keyword3 = new JSONObject();
        keyword3.put("value", msg);
        JSONObject keyword4 = new JSONObject();
        keyword4.put("value", DateUtil.format(new Date()));
        
        JSONObject data = new JSONObject();
        data.put("keyword1", keyword1);
        data.put("keyword2", keyword2);
        data.put("keyword3", keyword3);
        data.put("keyword4", keyword4);
        JSONObject param = new JSONObject();
        param.put("touser", openId);
//        param.put("template_id", weChatMiniConfig.getMiniTemplateIncomeToAccountNotifyId());//TODO 
        if (StringUtils.isNotBlank(page)) {
            param.put("page", page);
        }
        param.put("data", data);
        param.put("form_id", form_id);
        sendMiniTemplateMSG(param.toJSONString(), url + token);
    }

    /**
     * 业务处理通知消息
     * @param openId      //用户openId
     * @param busType  //业务类型
     * @param taskStatus  //业务状态
     * @param taskContent //业务内容
     * @param form_id     //表单Id
     * @param page        消息跳转路由
     */
    public static void taskHandleNotify(RedisTool redisTool, String openId,String busType, String taskStatus, String taskContent, String page, String form_id,WeChatMiniConfig weChatMiniConfig) throws Exception {
        String token = AccessToken.getToken(redisTool, weChatMiniConfig.getMiniAppId(), weChatMiniConfig.getMiniAppSecret());
        JSONObject keyword1 = new JSONObject();
        keyword1.put("value", "今找到系统运维状态通知");
        JSONObject keyword2 = new JSONObject();
        keyword2.put("value", busType);
        JSONObject keyword3 = new JSONObject();
        keyword3.put("value", taskStatus);
        JSONObject keyword4 = new JSONObject();
        keyword4.put("value", taskContent);
        
        JSONObject data = new JSONObject();
        data.put("keyword1", keyword1);
        data.put("keyword2", keyword2);
        data.put("keyword3", keyword3);
        data.put("keyword4", keyword4);
        JSONObject param = new JSONObject();
        param.put("touser", openId);
        param.put("template_id", weChatMiniConfig.getMiniTemplateTaskHandleNotifyId());
        if (StringUtils.isNotBlank(page)) {
            param.put("page", page);
        }
        param.put("data", data);
        param.put("form_id", form_id);
        sendMiniTemplateMSG(param.toJSONString(), url + token);
    }
}
