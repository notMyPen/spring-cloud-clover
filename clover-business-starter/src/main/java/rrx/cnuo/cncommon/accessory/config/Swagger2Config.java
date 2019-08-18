package rrx.cnuo.cncommon.accessory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置
 * @author xuhongyu
 * @date 2019年8月18日
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

	@Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
//                .apis(RequestHandlerSelectors.basePackage("rrx.cnuo.service"))//按照接口类所在的包的位置
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))//扫描带@ApiOperation注解的接口类
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
        		.title("C恋人后端Api文档")
                .description("C恋人：取Credit(信用认证) Lover之意，是一款诚实且具有信用的金融级认证婚恋产品。")
                .termsOfServiceUrl("http://www.renren.com")
                .contact(new Contact("renrenxin", "http://xxx", "xxxx@qq.com"))
                .version("1.0")
                .build();
    }
}
