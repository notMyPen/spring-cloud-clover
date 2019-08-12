package rrx.cnuo.cncommon.utils;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

import rrx.cnuo.cncommon.accessory.config.MqConfig;
import rrx.cnuo.cncommon.accessory.consts.Const;

/**
 * mq消息发送工具类
 * @author xuhongyu
 * @date 2019年7月2日
 */
//@Component
public class MqSendTool {

//	@Autowired
	private RedisTool redis;
	
	private RabbitTemplate rabbitTemplate;
    
    /**
     * 构造方法注入RabbitTemplate,定义异步回调方法
     */
//    @Autowired
    public MqSendTool(RabbitTemplate rabbitTemplate,RedisTool redis){
    	this.redis = redis;
    	
    	this.rabbitTemplate = rabbitTemplate;
    	
    	//异步回调方法，判断消息是否发送到broker并已持久化到磁盘中
    	this.rabbitTemplate.setConfirmCallback(new ConfirmCallback() {
			@Override
			public void confirm(CorrelationData correlationData, boolean ack, String cause) {
				if(correlationData != null){
					if(ack){
//						log.info("===========消息发送成功,删除落地的消息======");
						redis.removeFromMap(Const.REDIS_PREFIX.MQ_SEND_PREFIX, correlationData.getId());
					}else{
						//如果RabbitMQ因为自身内部错误导致消息丢失，就会发送一条nack消息
						System.out.println("----------消息发送失败,获取落地的消息体重新发送------");
						String msgObjStr = (String) redis.getFromMap(Const.REDIS_PREFIX.MQ_SEND_PREFIX, correlationData.getId());
						normalMqSender(correlationData.getId(),JSONObject.parseObject(msgObjStr));
					}
				}
			}
		});
    }
    
    /**
	 * 延迟队列消息发送
	 * @param time 延迟时间(单位毫秒)
	 * @param key 延迟消息内容
	 */
    public void delayMqSender(Long time,JSONObject msgObj){
    	try {
    		if(msgObj != null && msgObj.get(Const.MQ.MQ_HANDLER_TYPE_KEY) != null && time != null){
//    			log.info("发送mq消息："+msgObj.toJSONString());
    			rabbitTemplate.convertAndSend(MqConfig.EXCHANGE_DELAY, MqConfig.ROUTE_KEY_DELAY, msgObj, message -> {
    				message.getMessageProperties().setExpiration(time.toString());
    				return message;
    			});
    		}
        } catch (Exception e) {
            /*消息投递到RabbitMQ失败，一般会做容错处理，比如转发到其它交换机、落地redis或mysql(这里先不做处理等以后业务需要后再加)*/
//            log.error("延迟队列发送到RabbitMQ失败", e);
        }
    }
    
    /**
	 * 相关业务消息队列，利用发布确认机制(Confirm)保证消息发到broker
	 * @param correlationId mq消息唯一Id，防止重复发送
	 * @param msgObj
	 * void
	 */
    public void normalMqSender(String correlationId,JSONObject msgObj){
    	if(msgObj != null && msgObj.get(Const.MQ.MQ_HANDLER_TYPE_KEY) != null && !StringUtils.isEmpty(correlationId)){
//    		log.info("发送mq消息："+msgObj.toJSONString());
    		//TODO 先落地，确认发送成功后会删除，不成功会补发
    		redis.putToMap(Const.REDIS_PREFIX.MQ_SEND_PREFIX, correlationId, msgObj.toJSONString());
    		
    		//由于rabbit自身问题，即使在发送者setCorrelationIdString消费端也拿不到，所以这里直接存放到消息体中
    		msgObj.put(Const.MQ.MQ_CORRELATION_ID_KEY, correlationId);
    		
    		String routeKey = null;
    		CorrelationData correlationData = new CorrelationData(correlationId);
    		Byte mqHandleType = msgObj.getByte(Const.MQ.MQ_HANDLER_TYPE_KEY);
    		if(mqHandleType == Const.MqHandleType.SEND_WX_MSG.getCode() || 
    			mqHandleType == Const.MqHandleType.SEND_SMS_MSG.getCode() || 
    			mqHandleType == Const.MqHandleType.RECORD_LOGIN_TIME.getCode()){
    			routeKey = MqConfig.ROUTE_KEY_USER_DECOUPLING;
    		}else if(mqHandleType == Const.MqHandleType.SAVE_WECHAT_PAYMENT_INFO.getCode() || 
    				mqHandleType == Const.MqHandleType.TEST.getCode()){
    			routeKey = MqConfig.ROUTE_KEY_ORDER_DECOUPLING;
    		}else{
    			routeKey = MqConfig.ROUTE_KEY_BIZ_DECOUPLING;
    		}
    		rabbitTemplate.convertAndSend(MqConfig.EXCHANGE_NORMAL, routeKey, msgObj, correlationData);
    	}
    }
    
    /**
	 * 消息中心mq生产者(发短信、微信消息、邮件)
	 * @author xuhongyu
	 * @param msgObj
	 */
    public void smsWxMqSender(JSONObject msgObj){
    	if(msgObj.get("msg_type") != null && "weChat".equals(msgObj.getString("msg_type"))){
    		rabbitTemplate.convertAndSend(MqConfig.EXCHANGE_MSG, MqConfig.ROUTE_KEY_WECHAT, msgObj);
    	}else{
    		rabbitTemplate.convertAndSend(MqConfig.EXCHANGE_MSG, MqConfig.ROUTE_KEY_SMS, msgObj);
    	}
    }
}
