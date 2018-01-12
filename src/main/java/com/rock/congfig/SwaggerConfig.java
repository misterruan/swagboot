package com.rock.congfig;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Profile;
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
//允许在 sit,uat环境开启文档, 在生产环境禁用
@Profile({"sit", "uat"})
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //对外提供服务的类所在包名
                .apis(RequestHandlerSelectors.basePackage("com.rock.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //大标题
                .title("SpringBoot脚手架项目")
                //详细描述
                .description("脚手架设计的技术点请参考Rock有道文章：https://note.youdao.com/share/?id=4af37857c9dc0f7146160bafc3cd9625&type=notebook#/")
                .termsOfServiceUrl("https://github.com/misterruan/swagboot")
                //作者
                .contact("Rock")
                //版本
                .version("1.0")
                .build();
    }
    
}