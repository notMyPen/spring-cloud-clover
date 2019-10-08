package rrx.cnuo.service.feignclient;

import org.springframework.cloud.openfeign.FeignClient;

import rrx.cnuo.service.feignclient.callback.OrderHystrixFeignFallback;

/**
 * 通过内部访问order-service中的资源<br>
 * name是指要请求的服务名称<br>
 * fallback 是指请求失败，进入断路器的类，和使用ribbon是一样的<br>
 * configuration 是feign的一些配置，例如编码器等<br>
 * path表示对方的ContextPath(如果有)<br>
 * decode404表示如果发生404错误true时调用decoder进行解码，否则报FeignException错<br>
 * fallbackFactory工厂类，用于生产fallback类实例，通过这个属性我们可以实现每个接口的通用容错逻辑，减少重复代码
 * @author xuhongyu
 * @date 2019年6月27日
 */
@FeignClient(name = "order-service",decode404 = true,fallback = OrderHystrixFeignFallback.class/*,path = "/api"*/)
public interface OrderFeignService {

}
