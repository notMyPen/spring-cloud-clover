package rrx.cnuo.cncommon.accessory.interceptor;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Spring MVC是支持将Get方法的各个参数封装从pojo的，但是Feign没有实现这样的功能，目前常见的解决办法有：<br>
 * 1，将get方法各参数作为单独的属性放到方法参数里(代码臃肿不优雅)<br>
 * 2，把get方法参数放到Map里(不规范)<br>
 * 3，使用get方法传递@RequestBody，但这么做未被restful规范<br>
 * 此拦截器提供一种最佳处理方式：通过实现Feign拦截器RequestInterceptor中的apply方法统一拦截转换处理Feign中Get方法传递的参数<br>
 * 注：如果使用venus-cloud-feign插件，就不用自己写这个了
 * @author xuhongyu
 * @date 2019年8月9日
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor {

	@Autowired private ObjectMapper objectMapper;
	
	@Override
	public void apply(RequestTemplate template) {
		// Feign不支持get方法转pojo，json body转query
		if("GET".equals(template.method()) && template.requestBody() != null){
			String bodyStrb = template.requestBody().asString();
			if(bodyStrb != null && !"Binary data".equals(bodyStrb)) {
				try {
					JsonNode jsonNode = objectMapper.readTree(bodyStrb);
					template.body(Request.Body.encoded(null, Charset.forName("UTF-8")));
					
					Map<String,Collection<String>> queries = new HashMap<>();
					buildQuery(jsonNode,"",queries);
					template.queries(queries);
				} catch (IOException e) {
					//提示:根据实践项目情况处理此处异常，这里不做扩展。
					e.printStackTrace();
				}
			}
		}

	}

	private void buildQuery(JsonNode jsonNode, String path, Map<String, Collection<String>> queries) {
        if (!jsonNode.isContainerNode()) {   // 叶子节点
            if (jsonNode.isNull()) {
                return;
            }
            Collection<String> values = queries.get(path);
            if (null == values) {
                values = new ArrayList<>();
                queries.put(path, values);
            }
            values.add(jsonNode.asText());
            return;
        }
        if (jsonNode.isArray()) {   // 数组节点
            Iterator<JsonNode> it = jsonNode.elements();
            while (it.hasNext()) {
                buildQuery(it.next(), path, queries);
            }
        } else {
            Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields();
            while (it.hasNext()) {
                Map.Entry<String, JsonNode> entry = it.next();
                if (StringUtils.hasText(path)) {
                    buildQuery(entry.getValue(), path + "." + entry.getKey(), queries);
                } else {  // 根节点
                    buildQuery(entry.getValue(), entry.getKey(), queries);
                }
            }
        }
    }
}
