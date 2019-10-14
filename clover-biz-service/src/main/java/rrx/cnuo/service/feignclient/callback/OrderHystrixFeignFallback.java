package rrx.cnuo.service.feignclient.callback;

import org.springframework.stereotype.Component;

import rrx.cnuo.service.feignclient.OrderFeignService;

/**
 * 断路器回调方法<br>
 * 断路器要实现之前定义的UserFeignService接口，请求失败，进入断路器时，会回调这里的方法
 * @author xuhongyu
 * @date 2019年6月27日
 */
@Component
public class OrderHystrixFeignFallback implements OrderFeignService{
	
}
