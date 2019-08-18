package rrx.cnuo.service.filters;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;
import rrx.cnuo.service.utils.JwtUtil;
import rrx.cnuo.service.vo.PermissionException;

/**
 * 所有的请求都会进过此filter，然后对JWT Token进行解析校验，并转换成系统内部的Token，并把路由的服务名也加入header，送往下一个路由服务里，方便进行鉴权
 * @author xuhongyu
 * @date 2019年8月12日
 */
@Component
@RefreshScope
public class AuthFilter implements GlobalFilter,Ordered{

	@Value("${exclude-paths-array}")
	private String excludePathsArray;
	
    @SuppressWarnings("deprecation")
	@Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    	Route gatewayUrl = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
    	URI uri = gatewayUrl.getUri();
    	ServerHttpRequest request = (ServerHttpRequest)exchange.getRequest();
    	//http://localhost:8867/admin/v2/api-docs不用校验
    	String path = request.getPath().toString();//http://localhost:8867/admin/v2/api-docs
    	ServerHttpRequest.Builder mutate = request.mutate();
    	if(!StringUtils.isEmpty(excludePathsArray)) {
    		List<String> excludePaths = Arrays.asList(excludePathsArray.split(","));
    		if(!excludePaths.contains(path)) {
    			HttpHeaders header = request.getHeaders();
    			String token = header.getFirst(JwtUtil.HEADER_AUTH);
    			Map<String,String> userMap = JwtUtil.validateToken(token);
    			//TODO 换成具体系统中判断用户是否存在的逻辑即可
    			if(userMap.get("user").equals("admin") || userMap.get("user").equals("spring") || userMap.get("user").equals("cloud")) {
    				mutate.header("x-user-id", userMap.get("id"));
    				mutate.header("x-user-name", userMap.get("user"));
    				mutate.header("x-user-serviceName", uri.getHost());
    			}else {
    				throw new PermissionException("user not exist, please check");
    			}
    		}
    	}
    	ServerHttpRequest buildReuqest =  mutate.build();
        return chain.filter(exchange.mutate().request(buildReuqest).build());
    }

	@Override
	public int getOrder() {
		return -400;
	}
}
