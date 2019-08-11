package rrx.cnuo.service.mq;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;
import rrx.cnuo.cncommon.accessory.config.MqConfig;
import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.accessory.consts.Const.WeChatMsgEnum;
import rrx.cnuo.cncommon.vo.config.BasicConfig;
import rrx.cnuo.service.accessory.config.UserConfigBean;
import rrx.cnuo.service.po.UserPassport;
import rrx.cnuo.service.service.MsgService;
import rrx.cnuo.service.service.data.UserPassportDataService;

/**
 * 今借到业务解耦相关操作的队列消费者
 * @author xuhongyu
 * @date 2019年7月2日
 */
@Slf4j
@Component
public class BusinessDecoupMqReceiver {

    @Autowired private UserPassportDataService userPassportDataService;
    @Autowired private BasicConfig basicConfig;
    @Autowired private MsgService msgService;
    @Autowired private UserConfigBean userConfigBean;
    
	@RabbitListener(queues = MqConfig.QUEUE_NAME_USER_DECOUPLING)
    public void process(JSONObject msgObj, Channel channel, Message message) throws Exception {
		try {
			if(!basicConfig.isProdEnvironment()){
				log.info("==========BusinessDecoupMqReceiver消费队列："+msgObj.toJSONString()+"========");
			}
			if(msgObj != null && msgObj.get(Const.MQ.MQ_HANDLER_TYPE_KEY) != null){
				Byte mqHandleType = msgObj.getByte(Const.MQ.MQ_HANDLER_TYPE_KEY);
				Const.MqHandleType type = Const.MqHandleType.getMqHandleTypeCode(mqHandleType);
				switch (type) {
				case SEND_SMS_MSG:
					smsToGround(msgObj,channel,message);
					break;
				case SEND_WX_MSG:
					wxMsgToGround(msgObj,channel,message);
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			log.error("BusinessDecoupMqReceiver消费队列失败"+msgObj.toJSONString(), e);
		}
	}
	
	/**
	 * 微信消息落地(这里的消费不作必然要求)
	 * @author xuhongyu
	 * @param msgObj
	 * @param channel
	 * @param message
	 * void
	 */
	private void wxMsgToGround(JSONObject msgObj, Channel channel, Message message) throws IOException{
		try {
			Long uid = msgObj.getLong("uid");
			Byte msgType = msgObj.getByte("msgType");//消息类型
			String msgVariableVal = msgObj.getString("msgVariableVal");//消息内容中变量部分的值，不同值用#号分割并和模板中%s号顺序对应
			if(uid != null){
				//uid不为空，说明发送的是用户微信消息
				msgService.updateSendMiniWxMsg(uid, WeChatMsgEnum.getTypeByCode(msgType), msgVariableVal);
			}else{
				//uid为空，说明发送的是系统微信消息
//				String taskStatus = msgObj.getString("taskStatus");//业务状态
				String[] notifyOpenIds = userConfigBean.getReconciliationNotifyOpenids() == null ? null : userConfigBean.getReconciliationNotifyOpenids().split(",");
				if(notifyOpenIds != null && notifyOpenIds.length > 0 && StringUtils.isNotBlank(notifyOpenIds[0])){
					UserPassport userPassport = null;
					for(String openId : notifyOpenIds){
						userPassport = userPassportDataService.selectByOpenid(openId);
						if(userPassport != null){
							uid = userPassport.getUid();
							msgService.updateSendMiniWxMsg(uid, WeChatMsgEnum.getTypeByCode(msgType), msgVariableVal);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("smsToGround", e);
		}finally {
			//通知 MQ 消息已被成功消费,可以ACK了
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		}
	}

	/**
	 * 短信消息落地(这里的消费不作必然要求)
	 * @author xuhongyu
	 * @param msgObj
	 * @param channel
	 * @param message
	 * void
	 * @throws IOException 
	 */
	private void smsToGround(JSONObject msgObj, Channel channel, Message message) throws IOException {
		try {
//			String msgRouter = msgObj.getString("msgRouter");//短信类型
			String requestIp = msgObj.getString("requestIp");//用户ip
			String telephone = msgObj.getString("telephone");
			/*boolean voice = false;
			if(msgRouter.equals(MsgConst.MsgRouter.MSG_VOICE)){
				voice = true;//语音短信
        	}*/
	        
	        msgService.updateSendSmsMessage(requestIp, telephone, 0, "");
		} catch (Exception e) {
			log.error("smsToGround", e);
		}finally {
			//通知 MQ 消息已被成功消费,可以ACK了
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		}
	}
}
