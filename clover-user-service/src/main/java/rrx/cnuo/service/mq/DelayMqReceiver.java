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
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.cncommon.vo.config.BasicConfig;

/**
 * 延迟队列消费端
 * @author xuhongyu
 * @date 2019年7月2日
 */
@Component
@Slf4j
public class DelayMqReceiver {
	
	@Autowired private RedisTool redis;
	@Autowired private BasicConfig basicConfig;

	@RabbitListener(queues = MqConfig.QUEUE_NAME_DELAY_ORIGIN)
    public void process(JSONObject msgObj, Channel channel, Message message) throws Exception {
		try {
			if(!basicConfig.isProdEnvironment()){
				log.info("==========延迟消费队列："+msgObj.toJSONString()+"========");
			}
			if(msgObj != null && msgObj.get(Const.MQ.MQ_HANDLER_TYPE_KEY) != null){
				Byte mqHandleType = msgObj.getByte(Const.MQ.MQ_HANDLER_TYPE_KEY);
				Const.MqHandleType type = Const.MqHandleType.getMqHandleTypeCode(mqHandleType);
				switch (type) {
				case DELREDIS:
					delRedis(msgObj,channel,message);
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			// 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列(这里因本身就是容错处理故先不做处理等以后业务需要后再加)
			log.error("延迟消费队列失败"+msgObj.toJSONString(), e);
		}
	}
	
	/**
	 * 为了针对并发引起的redis脏数据，在延迟队列中清除redis(这里消费不做必然要求)
	 * @param msgObj
	 * @throws IOException 
	 */
	private void delRedis(JSONObject msgObj, Channel channel, Message message) throws IOException{
		try {
			String key = msgObj.getString(Const.MQ.DELAY_DELETE_REDIS_KEY);
			if(StringUtils.isNotBlank(key)){
				redis.expire(key, 0);
			}
		}finally {
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		}
	}
}
