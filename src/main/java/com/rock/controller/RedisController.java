package com.rock.controller;

import com.rock.common.util.JsonUtils;
import com.rock.model.base.CommonResult;
import com.rock.model.jpa.DsClasses;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rock on 2019/8/9.
 */
@RestController
@RequestMapping(value = "/redis")
@Api("Redis操作测试controller：JPA/JOOQ")
@Slf4j
public class RedisController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping(value = "/getValueByKey/{key}",  produces = { "application/json" }, method = { RequestMethod.GET })
    @ApiOperation(value = "根据key获取value")
    public CommonResult<String> getValueByKey(@PathVariable @ApiParam(value = "key", required = true)String key) {
        int i = 1/0;
        String value = redisTemplate.opsForValue().get(key);
        return CommonResult.ok(value);
    }
}
