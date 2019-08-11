package rrx.cnuo.service.mq;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;

import rrx.cnuo.cncommon.accessory.consts.Const;
import rrx.cnuo.cncommon.utils.RedisTool;
import rrx.cnuo.cncommon.vo.JsonResult;
import rrx.cnuo.service.dao.SystemMqConsumeFailMapper;
import rrx.cnuo.service.po.SystemMqConsumeFail;

/**
 * mq消息消费结果处理类
 * @author xuhongyu
 * @date 2019年1月18日
 */
public class ConsumeResultHandle {
	
	@Autowired
	protected SystemMqConsumeFailMapper systemMqConsumeFailMapper;
	
	@Autowired
	protected RedisTool redis;

	protected void handleMqByResult(JSONObject msgObj, Channel channel, Message message,int status,String failInfo) throws IOException{
		String correlationId = msgObj.getString(Const.MQ.MQ_CORRELATION_ID_KEY);
		if(status == JsonResult.SUCCESS){
			//消费成功了，如果之前有重试记录、错误落地mysql记录，删掉
			redis.removeFromMap(Const.REDIS_PREFIX.MQ_CONSUME_FAIL_CNT, correlationId);
			systemMqConsumeFailMapper.deleteByCorrelationId(correlationId);
			
			//通知 MQ 消息已被成功消费,可以ACK了
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		}else if(status == JsonResult.ERROR_CANT_SOLVE){
			// 无法通过程序自动处理的，先落地mysql
			consumeFailHandle(msgObj, channel, message, failInfo);
		}else{
			//失败后这个消息不做ACK，让其重复尝试三次,三次仍然不成功就当ECLOUD_ERROR_CANT_SOLVE处理
			String failCntStr = (String) redis.getFromMap(Const.REDIS_PREFIX.MQ_CONSUME_FAIL_CNT, correlationId);
			if(failCntStr != null && Integer.parseInt(failCntStr) >= Const.MQ.MQ_CONSUME_RETRY_CNT){
				// 重试三次还不能消费，先落地mysql
				consumeFailHandle(msgObj, channel, message, failInfo);
			}else{
				Integer failCnt = 1;
				if(failCntStr != null){
					failCnt = Integer.parseInt(failCntStr);
					failCnt ++;
				}
				redis.putToMap(Const.REDIS_PREFIX.MQ_CONSUME_FAIL_CNT, correlationId, failCnt.toString());
				channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
			}
		}
	}
	
	/**
	 * mq消费时:1,因程序问题确认不能消费且不能通过程序自动处理的. 2,因重试三次还不能消费的
	 * 先将消息暂存mysql,然后ack(等解决完bug后通过定时任务重新发送该消息)
	 * @author xuhongyu
	 * @param msgObj
	 * @param channel
	 * @param message
	 * @param failInfo
	 * @throws IOException
	 */
	private void consumeFailHandle(JSONObject msgObj, Channel channel, Message message,String failInfo) throws IOException{
		String correlationId = msgObj.getString(Const.MQ.MQ_CORRELATION_ID_KEY);
		SystemMqConsumeFail systemMqConsumeFail = new SystemMqConsumeFail();
		systemMqConsumeFail.setCorrelationId(correlationId);
		systemMqConsumeFail.setMsgBody(msgObj.toJSONString());
		systemMqConsumeFail.setFailInfo(failInfo);
		systemMqConsumeFailMapper.insertSelective(systemMqConsumeFail);
		
		//落地mysql了，之前由于重试消费的次数信息就没用了，如果有,删掉
		redis.removeFromMap(Const.REDIS_PREFIX.MQ_CONSUME_FAIL_CNT, correlationId);
		
		//通知 MQ 消息已被成功消费,可以ACK了
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
}
