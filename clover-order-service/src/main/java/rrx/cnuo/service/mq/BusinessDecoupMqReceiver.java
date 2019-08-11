package rrx.cnuo.service.mq;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;
import rrx.cnuo.cncommon.accessory.config.MqConfig;
import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.util.RedisTool;
import rrx.cnuo.cncommon.vo.config.BasicConfig;
import rrx.cnuo.service.dao.TradeMapper;
import rrx.cnuo.service.po.Trade;

/**
 * 今借到业务解耦相关操作的队列消费者
 * @author xuhongyu
 * @date 2019年7月2日
 */
@Slf4j
@Component
public class BusinessDecoupMqReceiver {

    @Autowired private BasicConfig basicConfig;
    @Autowired private TradeMapper tradeMapper;
    @Autowired private RedisTool redis;
    
	@RabbitListener(queues = MqConfig.QUEUE_NAME_ORDER_DECOUPLING)
    public void process(JSONObject msgObj, Channel channel, Message message) throws Exception {
		try {
			if(!basicConfig.isProdEnvironment()){
				log.info("==========BusinessDecoupMqReceiver消费队列："+msgObj.toJSONString()+"========");
			}
			if(msgObj != null && msgObj.get(Const.MQ.MQ_HANDLER_TYPE_KEY) != null){
				Byte mqHandleType = msgObj.getByte(Const.MQ.MQ_HANDLER_TYPE_KEY);
				Const.MqHandleType type = Const.MqHandleType.getMqHandleTypeCode(mqHandleType);
				switch (type) {
				case TEST:
					test(msgObj,channel,message);
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			log.error("BusinessDecoupMqReceiver消费队列失败"+msgObj.toJSONString(), e);
		}
	}
	
	private void test(JSONObject msgObj, Channel channel, Message message) throws IOException {
		try {
			String test = msgObj.getString("test");
			System.out.println(test);
			
			String testKey = redis.getString("testKey");
			System.out.println(testKey);
			
			Trade record = new Trade();
			record.setId(1214L);
			record.setUid(1113L);
			tradeMapper.insertSelective(record);
		} catch (Exception e) {
			log.error("smsToGround", e);
		}finally {
			//通知 MQ 消息已被成功消费,可以ACK了
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		}
	}
}
