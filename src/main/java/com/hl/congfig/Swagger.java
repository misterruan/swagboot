package com.hl.congfig;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableAutoConfiguration
public class Swagger {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //对外提供服务的类所在包名
                .apis(RequestHandlerSelectors.basePackage("com.hl.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //大标题
                .title("Spring Boot中使用Swagger2构建RESTful APIs脚手架项目")
                //详细描述
                .description("更多Spring Boot相关文章请关注：http://blog.csdn.net/catoop/article/details/50668896")
                .termsOfServiceUrl("http://blog.csdn.net/catoop/article/details/50668896")
                //作者
                .contact("Rock")
                //版本
                .version("1.0")
                .build();
    }
    
}