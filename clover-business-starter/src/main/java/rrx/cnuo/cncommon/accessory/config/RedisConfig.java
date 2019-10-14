package rrx.cnuo.cncommon.accessory.config;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import rrx.cnuo.cncommon.utils.RedisTool;

/**
 * redis配置，三个功能：<br>
 * 1，注入JedisPool（只是为了能用自定义redis分布式锁，如果不用可以删掉换成用spring自带的分布式锁）
 * 2，注入RedisTemplate
 * 3，注入JedisPool和RedisTemplate是为redisTool类使用，将redisTool类注入IOC容器供其它类使用且名字多样化
 * @author xuhongyu
 * @date 2019年8月9日
 */
@Component
@EnableCaching
@RefreshScope//Spring Config 自动刷新配置
public class RedisConfig extends CachingConfigurerSupport{

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
     * 需要在pom文件中单独引入原生redis.clients的jedis<br>
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
    
    /**
     * 在没有指定缓存Key的情况下，key生成策略
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append("#"+method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }
    
//  @Bean
//  public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//      // 初始化缓存管理器，在这里我们可以缓存的整体过期时间等
//      // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
//      //RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
//      //config = config.entryTtl(Duration.ofSeconds(60))    // 设置缓存的默认过期时间，也是使用Duration设置
//      //        .disableCachingNullValues();                // 不缓存空值
//      //RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory).cacheDefaults(config).build();
//      RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory).build();
//      return redisCacheManager;
//  }
	
    @Bean
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public RedisCacheManager redisCacheManager(RedisTemplate redisTemplate) {
        //spring cache注解序列化配置
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getKeySerializer()))        //key序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()))    //value序列化方式
                .disableCachingNullValues()         //不缓存null值
                .entryTtl(Duration.ofSeconds(60));  //默认缓存过期时间
 
        // 设置一个初始化的缓存名称set集合
        Set<String> cacheNames =  new HashSet<>();
        cacheNames.add("user");
 
        // 对每个缓存名称应用不同的配置，自定义过期时间
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put("user", redisCacheConfiguration.entryTtl(Duration.ofSeconds(120)));
 
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisTemplate.getConnectionFactory())
                .cacheDefaults(redisCacheConfiguration)
                .transactionAware()
                .initialCacheNames(cacheNames)  // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
                .withInitialCacheConfigurations(configMap)
                .build();
        return redisCacheManager;
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
	       redisTemplate.setConnectionFactory(redisConnectionFactory);
	       redisTemplate.setKeySerializer(new StringRedisSerializer());// key序列化
	       redisTemplate.setHashKeySerializer(new StringRedisSerializer());// Hash key序列化
	       
	       // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
	       Jackson2JsonRedisSerializer<JSON> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<JSON>(JSON.class);
//	       ObjectMapper mapper = new ObjectMapper();
//	       mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);//{"id":"1","name":"张三","age":18}
//	       mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);//json数据带类的名称 //["com.urthink.upfs.model.entity.User",{"id":"1","name":"张三","age":18}]
//	       serializer.setObjectMapper(mapper);
	       redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);// value序列化new GenericJackson2JsonRedisSerializer()
	       redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);// Hash value序列化new GenericJackson2JsonRedisSerializer()
	       redisTemplate.afterPropertiesSet();
	       return redisTemplate;
	   }
	   
	   @Bean(name = {"redis", "redisTool", "redisService"})
	   public RedisTool redisTool(@Qualifier("redisTemplate") RedisTemplate<String, Object> redisTemplate,@Qualifier("jedisPool") JedisPool jedisPool) {
		   Jedis jedis = jedisPool.getResource();
		   return new RedisTool(redisTemplate,jedis);
	   }
}
