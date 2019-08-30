package com.rock.controller;

import com.rock.common.aspect.redislock.RedisLock;
import com.rock.model.base.CommonResult;
import com.rock.model.test.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

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

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate objectRedisTemplate;

    @RequestMapping(value = "/test/getValueByKey/{key}",  produces = { "application/json" }, method = { RequestMethod.GET })
    @ApiOperation(value = "根据key获取value")
    public CommonResult getValueByKey(@PathVariable @ApiParam(value = "key", required = true)String key) {
//        redisTemplate.opsForValue().set("rock2","fffwefeff fwef ewfe ");
        return CommonResult.ok(null);
    }



    @RequestMapping(value = "/test/lock/prefix-suffix",  produces = { "application/json" }, method = { RequestMethod.GET })
    @ApiOperation(value = "布式锁:前缀+后缀")
    //使用的是使用SPEL表达式
    @RedisLock(prefix = "RedisController::test1" ,suffix = "'_lockKey'+ #a.concat('_').concat(#b)",expireSec = 240)
    public CommonResult test1(String a,String b) {
        return CommonResult.ok(null);
    }


    @RequestMapping(value = "/test/lock/prefix",  produces = { "application/json" }, method = { RequestMethod.GET })
    @ApiOperation(value = "布式锁:前缀")
    //使用的是使用SPEL表达式
    @RedisLock(prefix = "RedisController::test2",expireSec = 120)
    public CommonResult test2() {
        return CommonResult.ok(null);
    }


    @RequestMapping(value = "/test/lock/suffix",  produces = { "application/json" }, method = { RequestMethod.GET })
    @ApiOperation(value = "布式锁:后缀")
    //使用的是使用SPEL表达式
    @RedisLock(suffix = "'_lockKey'+ #a",expireSec = 120)
    public CommonResult test3(String a,String b) {
        return CommonResult.ok(null);
    }

    @RequestMapping(value = "/test/lock/default",  produces = { "application/json" }, method = { RequestMethod.POST })
    @ApiOperation(value = "布式锁:默认")
    //使用的是使用SPEL表达式
    @RedisLock
    //最终生成的redis key范例：swagboot::com.rock.controller.RedisController::test4_Student(name=xiao, age=10)
    public CommonResult test4(@RequestBody Student student) {
        return CommonResult.ok(null);
    }
}
