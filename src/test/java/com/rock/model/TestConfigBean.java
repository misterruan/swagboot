package com.rock.model;

import com.rock.model.jpa.DsClasses;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Created by rock on 2018/12/13.
 */
@Configuration //使用@Configuration，可以保证在外部注入TestConfigBean对象，用其调用createBean方法每次返回的都是同一个对象，即是单例的。
//@Component //使用@Component，不能保证外部注入的时候调用createBean()返回的是单例，每次调用会创建新对象。
public class TestConfigBean {


    @Bean //使用bean注释，可以在外部直接通过@Autowired  DsClasses dsClasses;注入已创建的bean
    public DsClasses createBean() {
        return new DsClasses().setClassName("a").setImage("b").setPId(1l);
    }
}
