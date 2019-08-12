package rrx.cnuo.cncommon.accessory.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import rrx.cnuo.cncommon.utils.RedisTool;

/**
 * redis配置，两个功能：<br>
 * 1，注入JedisPool（只是为了能用自定义redis分布式锁，如果不用可以删掉换成用spring自带的分布式锁）
 * 2，注入RedisTemplate
 * 3，注入JedisPool和RedisTemplate只是为redisTool类使用(其它类不能使用)，将redisTool类注入IOC容器供其它类使用且名字多样化
 * @author xuhongyu
 * @date 2019年8月9日
 */
@Component
@RefreshScope
public class RedisConfig {

	/*如果不用自定义redis分布式锁，这个地方可以删除不用*/
	@Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.timeout}")
    private int timeout;
    
    /**
     * 注入原生JedisPool，用于自定义分布式锁<br>
     * 需要早pom文件中单独引入原生redis.clients的jedis<br>
     * 分布式锁实现：https://blog.csdn.net/u013219624/article/details/83314484
     * @return
     */
    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        if (StringUtils.isEmpty(password)) {
            return new JedisPool(jedisPoolConfig, host, port, timeout);
        }
        return new JedisPool(jedisPoolConfig, host, port, timeout, password);
    }
    
//  @Bean
//  @SuppressWarnings("all")
//  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//      RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
//      template.setConnectionFactory(factory);
//      Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//      ObjectMapper om = new ObjectMapper();
//      om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//      om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//      jackson2JsonRedisSerializer.setObjectMapper(om);
//      StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//      // key采用String的序列化方式
//      template.setKeySerializer(stringRedisSerializer);
//      // hash的key也采用String的序列化方式
//      template.setHashKeySerializer(stringRedisSerializer);
//      // value序列化方式采用jackson
//      template.setValueSerializer(jackson2JsonRedisSerializer);
//      // hash的value序列化方式采用jackson
//      template.setHashValueSerializer(jackson2JsonRedisSerializer);
//      template.afterPropertiesSet();
//      return template;
//  }

	/**
	    * springboot2.x 使用LettuceConnectionFactory 代替 RedisConnectionFactory
	    * application.yml配置基本信息后,springboot2.x  RedisAutoConfiguration能够自动装配
	    * LettuceConnectionFactory 和 RedisConnectionFactory 及其 RedisTemplate
	    * @param redisConnectionFactory
	    * @return
	    */
	   @Bean
	   public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory redisConnectionFactory){
	       RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
	       redisTemplate.setKeySerializer(new StringRedisSerializer());
	       redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
	       redisTemplate.setHashKeySerializer(new StringRedisSerializer());
	       redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
	       redisTemplate.setConnectionFactory(redisConnectionFactory);
	       return redisTemplate;
	   }
	   
	   @Bean(name = {"redis", "redisTool", "redisService"})
	   public RedisTool redisTool(@Qualifier("redisTemplate") RedisTemplate<String, Object> redisTemplate,@Qualifier("jedisPool") JedisPool jedisPool) {
		   Jedis jedis = jedisPool.getResource();
		   return new RedisTool(redisTemplate,jedis);
	   }
}
