package com.rock.congfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by user on 2017/3/26.
 * 用于测试@Value 注解读取properties中的属性，缓存以供查询
 * 仅供测试@Value，无实际意义
 */
@Component
public class AppConfigBean {
    @Value("${spring.test.config.name}")
    private String name;
    @Value("${spring.test.config.poolSize}")
    private Integer poolSize;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(Integer poolSize) {
        this.poolSize = poolSize;
    }
}
