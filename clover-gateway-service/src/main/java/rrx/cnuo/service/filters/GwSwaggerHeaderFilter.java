package rrx.cnuo.service.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import rrx.cnuo.service.config.GatewaySwaggerProvider;

/**
 * 在路由规则为user/test/{a}/{b}时，swagger界面会显示为test/{a}/{b}，缺少了user这个路由节点。<br>
 * 通过debug调试发现，swagger会根据X-Forwarded-Prefix这个Header来回去BasePath，因此需要将它添加到接口路径与Host之间才能正常工作。<br>
 * 但是gateway在做转发的时候并没有并没有将这个Header添加到request上，从而导致接口调试时出现404错误。<br>
 * 为了解决这个问题，需要在gateway中编写一个过滤器来添加这个header到request中。
 * @author xuhongyu
 * @date 2019年8月18日
 */
@Component
public class GwSwaggerHeaderFilter extends AbstractGatewayFilterFactory {
    private static final String HEADER_NAME = "X-Forwarded-Prefix";

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            if (!StringUtils.endsWithIgnoreCase(path, GatewaySwaggerProvider.API_URI)) {
                return chain.filter(exchange);
            }
            String basePath = path.substring(0, path.lastIndexOf(GatewaySwaggerProvider.API_URI));
            ServerHttpRequest newRequest = request.mutate().header(HEADER_NAME, basePath).build();
            ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
            return chain.filter(newExchange);
        };
    }
}
