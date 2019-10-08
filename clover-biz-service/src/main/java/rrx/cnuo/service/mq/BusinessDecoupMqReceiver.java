package rrx.cnuo.service.mq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;
import rrx.cnuo.cncommon.accessory.config.MqConfig;
import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.vo.config.BasicConfig;
import rrx.cnuo.service.service.CreditClassFirstService;

/**
 * 今借到业务解耦相关操作的队列消费者
 * @author xuhongyu
 * @date 2019年7月2日
 */
@Slf4j
@Component
public class BusinessDecoupMqReceiver {

    @Autowired private BasicConfig basicConfig;
    @Autowired private CreditClassFirstService creditClassFirstService;
    
	@RabbitListener(queues = MqConfig.QUEUE_NAME_BIZ_DECOUPLING)
    public void process(JSONObject msgObj, Channel channel, Message message) throws Exception {
		try {
			if(!basicConfig.isProdEnvironment()){
				log.info("==========BusinessDecoupMqReceiver消费队列："+msgObj.toJSONString()+"========");
			}
			if(msgObj != null && msgObj.get(Const.MQ.MQ_HANDLER_TYPE_KEY) != null){
				Byte mqHandleType = msgObj.getByte(Const.MQ.MQ_HANDLER_TYPE_KEY);
				Const.MqHandleType type = Const.MqHandleType.getMqHandleTypeCode(mqHandleType);
				switch (type) {
				case REGIST_CREDIT_CENTER:
					registCredit(msgObj,channel,message);
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			log.error("BusinessDecoupMqReceiver消费队列失败"+msgObj.toJSONString(), e);
		}finally {
			//通知 MQ 消息已被成功消费,可以ACK了
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		}
	}
	
	private void registCredit(JSONObject msgObj, Channel channel, Message message) throws Exception {
		String telephone = msgObj.getString("telephone");
		Long uid = msgObj.getLong("uid");
		creditClassFirstService.registCredit(uid,telephone);
	}
}
