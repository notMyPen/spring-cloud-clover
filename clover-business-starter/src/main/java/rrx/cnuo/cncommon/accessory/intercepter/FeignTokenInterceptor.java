package rrx.cnuo.cncommon.accessory.intercepter;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import rrx.cnuo.cncommon.accessory.context.UserContextHolder;
import rrx.cnuo.cncommon.vo.LoginUser;

/**
 * Feign统一Token拦截器<br>
 * 在进行认证鉴权时，不管是jwt还是security，当使用Feign就会发现外部请求到A服务时A服务可以拿到Token，<br>
 * 然而当服务A使用Feign调用服务B时Token就会丢失，从而认证失败。解决方法就是在Feign调用之前向请求头里添加需要传递的Token
 * @author xuhongyu
 * @date 2019年8月10日
 */
@Component
public class FeignTokenInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
//    	ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request = attributes.getRequest();
//        Enumeration<String> headerNames = request.getHeaderNames();
//        if (headerNames != null) {
//            while (headerNames.hasMoreElements()) {
//                String name = headerNames.nextElement();
//                String values = request.getHeader(name);
//                requestTemplate.header(name, values);
//            }
//        }
    	
        LoginUser user = UserContextHolder.currentUser();
        requestTemplate.header("x-user-id", user.getUserId() + "");
        requestTemplate.header("x-user-name", user.getMiniOpenId());
        requestTemplate.header("x-user-serviceName", user.getCurrentServiceId());
    }

}
