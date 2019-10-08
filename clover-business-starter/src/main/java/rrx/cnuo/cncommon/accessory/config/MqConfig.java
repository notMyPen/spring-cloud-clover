package rrx.cnuo.cncommon.accessory.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import rrx.cnuo.cncommon.utils.MqSendTool;
import rrx.cnuo.cncommon.utils.RedisTool;

/**
 * mq配置，里面配置了延迟队列(若想使用正常队列，需单独定义其它交换机)
 * rabbitMq的延迟队列是死信实现的：设置了 TTL 的消息或队列最终会成为Dead Letter。如果为队列设置了Dead Letter Exchange（DLX），
 * 那么这些Dead Letter就会被重新发送到Dead Letter Exchange中，然后通过Dead Letter Exchange路由到其他队列，即可实现延迟队列的功能
 * @author xuhongyu
 */
@Component
@RefreshScope
public class MqConfig {
	
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private String port;
    @Value("${spring.rabbitmq.virtualHost}")
    private String virtualHost;

    //正常的交换机,处理今借到一般业务
    public static final String EXCHANGE_NORMAL = "exchange.clover-normal";
    
    //用于诚诺用户服务业务解耦相关操作的队列
    public static final String ROUTE_KEY_USER_DECOUPLING = "route.clover-user.decoupling";
    public static final String QUEUE_NAME_USER_DECOUPLING = "queue.clover-user.decoupling";
    
    //用于诚诺订单服务业务解耦相关操作的队列
    public static final String ROUTE_KEY_ORDER_DECOUPLING = "route.clover-order.decoupling";
    public static final String QUEUE_NAME_ORDER_DECOUPLING = "queue.clover-order.decoupling";
    
    //用于诚诺biz服务业务解耦相关操作的队列
    public static final String ROUTE_KEY_BIZ_DECOUPLING = "route.clover-biz.decoupling";
    public static final String QUEUE_NAME_BIZ_DECOUPLING = "queue.clover-biz.decoupling";
    
    //连消息中心的交换机
    public static final String EXCHANGE_MSG = "exchange.msg";
    public static final String ROUTE_KEY_SMS = "route.sms";
    public static final String QUEUE_NAME_SMS = "queue.sms";
    public static final String ROUTE_KEY_WECHAT = "route.weChat";
    public static final String QUEUE_NAME_WECHAT = "queue.weChat";
    
    /*============上面定义正常队列，以下定义延迟队列(不同时间间隔用不同的队列防止积压)================*/
    public static final String EXCHANGE_DELAY_ORIGIN = "exchange.delay.clover-origin";//延迟交换机对应的源交换机
    public static final String ROUTE_KEY_DELAY_ORIGIN = "route.delay.clover-origin";
    public static final String QUEUE_NAME_DELAY_ORIGIN = "queue.delay.clover-origin";
    
    public static final String EXCHANGE_DELAY = "exchange.clover-delay";//DLX，dead letter发送到的 exchange,延时消息就是发送到该交换机的
    public static final String ROUTE_KEY_DELAY = "route.clover-delay";
    public static final String QUEUE_NAME_DELAY = "queue.clover-delay";//延迟队列 TTL 名称

    @Bean
    public TopicExchange msgSendExchange() {
        return new TopicExchange(EXCHANGE_MSG);//连消息中心交换机
    }
    
    @Bean
    public TopicExchange normalExchange() {
    	//今借到业务处理交换机，需要持久化
//    	return (TopicExchange) ExchangeBuilder.topicExchange(EXCHANGE_NORMAL).durable(true).build();
        return new TopicExchange(EXCHANGE_NORMAL);
    }
    
    @Bean
    public Queue queueUserDecoupling() {
    	return new Queue(QUEUE_NAME_USER_DECOUPLING, true);
    }
    
    @Bean
    public Queue queueOrderDecoupling() {
    	return new Queue(QUEUE_NAME_ORDER_DECOUPLING, true);
    }
    
    @Bean
    public Queue queueBizDecoupling() {
    	return new Queue(QUEUE_NAME_BIZ_DECOUPLING, true);
    }
    
    @Bean
    public Queue queueMsgSend() {
    	return new Queue(QUEUE_NAME_SMS, true);
    }
    
    @Bean
    public Queue queueWechatSend() {
    	return new Queue(QUEUE_NAME_WECHAT, true);
    }
    
    @Bean
    public TopicExchange delayOriginTopicExchange() {
        return new TopicExchange(EXCHANGE_DELAY_ORIGIN);
    }
    
    @Bean
    public DirectExchange delayDirectExchange() {
        return new DirectExchange(EXCHANGE_DELAY);
    }

    @Bean
    public Queue queueDelayOrigin() {
        return new Queue(QUEUE_NAME_DELAY_ORIGIN, true);
    }

    /**
     * 延迟队列配置
     * <p>
     * 1、params.put("x-message-ttl", 5 * 1000);
     * 第一种方式是直接设置 Queue 延迟时间 但如果直接给队列设置过期时间,这种做法不是很灵活,（当然二者是兼容的,默认是时间小的优先）
     * 2、rabbitTemplate.convertAndSend(book, message -> {
     * message.getMessageProperties().setExpiration(2 * 1000 + "");
     * return message;
     * });
     * 第二种就是每次发送消息动态设置延迟时间,这样我们可以灵活控制
     **/
    @Bean
    public Queue queueDelay() {
    	Map<String, Object> params = new HashMap<String, Object>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-dead-letter-exchange", EXCHANGE_DELAY_ORIGIN);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        params.put("x-dead-letter-routing-key", ROUTE_KEY_DELAY_ORIGIN);
//        params.put("x-message-ttl", 60 * 1000);
        return new Queue(QUEUE_NAME_DELAY, true, false, false, params);
    }

    @Bean
    public List<Binding> accountsBinding() {
        List<Binding> bindings = new ArrayList<Binding>();
        
        bindings.add(BindingBuilder.bind(queueDelayOrigin()).to(delayOriginTopicExchange()).with(ROUTE_KEY_DELAY_ORIGIN));//延迟队列的原始队列绑定
        bindings.add(BindingBuilder.bind(queueDelay()).to(delayDirectExchange()).with(ROUTE_KEY_DELAY));//延迟队列绑定
        
        bindings.add(BindingBuilder.bind(queueUserDecoupling()).to(normalExchange()).with(ROUTE_KEY_USER_DECOUPLING));//正常交换机-用于诚诺-用户服务业务解耦相关操作的队列
        bindings.add(BindingBuilder.bind(queueOrderDecoupling()).to(normalExchange()).with(ROUTE_KEY_ORDER_DECOUPLING));//正常交换机-用于诚诺-订单服务业务解耦相关操作的队列
        bindings.add(BindingBuilder.bind(queueBizDecoupling()).to(normalExchange()).with(ROUTE_KEY_BIZ_DECOUPLING));//正常交换机-用于诚诺-biz服务业务解耦相关操作的队列
        
        bindings.add(BindingBuilder.bind(queueMsgSend()).to(msgSendExchange()).with(ROUTE_KEY_SMS));//短信消息发送队列
        bindings.add(BindingBuilder.bind(queueWechatSend()).to(msgSendExchange()).with(ROUTE_KEY_WECHAT));//微信消息发送队列
        
        return bindings;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setAddresses(host + ":" + port);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPublisherConfirms(true);//开启发布确认机制
        connectionFactory.setRequestedHeartBeat(20);
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
    
    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
//        设置消费者线程数
        simpleRabbitListenerContainerFactory.setConcurrentConsumers(5);
//        设置最大消费者线程数
        simpleRabbitListenerContainerFactory.setMaxConcurrentConsumers(10);
        simpleRabbitListenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return simpleRabbitListenerContainerFactory;
    }
    
    /**
     * 如果需要生产者发送消息后进行confirm，这里必须是prototype原型模式:每次获取Bean的时候会有一个新的实例
     * 因为生产者发送消息后若想回调，需要对rabbitTemplate设置ConfirmCallback对象，由于不同的生产者需要对应不同的ConfirmCallback，
     * 如果rabbitTemplate设置为单例bean，则所有的rabbitTemplate实际的ConfirmCallback为最后一次申明的ConfirmCallback
     * @author xuhongyu
     * @return
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
	    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
	    //可以在这里绑定RabbitTemplate和指定的交换机，这样在调用rabbitTemplate.convertAndSend发送消息时就不用再绑定交换机了(但这样使RabbitTemplate不够灵活只能给一个交换机用)
	    //rabbitTemplate.setExchange(EXCHANGE_DEL_REDIS_DELAY);
	    //rabbitTemplate.setMandatory(true);//返回消息必须设置为true
        //rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());//数据转换为json存入消息队列
	    return rabbitTemplate;
	}
    
    @Autowired private RedisTool redis;
    
    @Bean(name = {"mqSender", "mqSendTool", "mqMsgService"})
    public MqSendTool mqSendTool(@Qualifier("rabbitTemplate") RabbitTemplate rabbitTemplate) {
    	return new MqSendTool(rabbitTemplate,redis);
    }
}
