package rrx.cnuo.cncommon.accessory.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Feign统一Token拦截器<br>
 * 在进行认证鉴权时，不管是jwt还是security，当使用Feign就会发现外部请求到A服务时A服务可以拿到Token，<br>
 * 然而当服务A使用Feign调用服务B时Token就会丢失，从而认证失败。解决方法就是在Feign调用的时候向请求头里添加需要传递的Token
 * @author xuhongyu
 * @date 2019年8月10日
 */
@Component
public class FeignTokenInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if(null==getHttpServletRequest()){
            //此处省略日志记录
            return;
        }
        //将获取Token对应的值往下面传（假如我们存到请求头里的key为oauthToken）
        requestTemplate.header("oauthToken", getHeaders(getHttpServletRequest()).get("oauthToken"));
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Feign拦截器拦截请求获取Token对应的值
     * @param request
     * @return
     */
    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
