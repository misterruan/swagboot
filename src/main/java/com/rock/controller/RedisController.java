package com.rock.controller;

import com.rock.common.aspect.redislock.RedisLock;
import com.rock.common.aspect.redislock.RedisLockClient;
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

    @Autowired
    private RedisLockClient lockClient;

    @RequestMapping(value = "/test/getValueByKey/{key}",  produces = { "application/json" }, method = { RequestMethod.GET })
    @ApiOperation(value = "根据key获取value")
    public CommonResult getValueByKey(@PathVariable @ApiParam(value = "key", required = true)String key) {
//        redisTemplate.opsForValue().set("rock2","fffwefeff fwef ewfe ");
//        objectRedisTemplate.opsForList().rightPush()
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


    @RequestMapping(value = "/test/lock/manual",  produces = { "application/json" }, method = { RequestMethod.GET })
    @ApiOperation(value = "布式锁:手动测试加解锁")
    public CommonResult test5() throws InterruptedException {
        boolean lock = lockClient.getLock("aaaaa", 2);
        boolean lock2 = lockClient.getLock("aaaaa", 2);
        Thread.sleep(3000);
        boolean lock3 = lockClient.getLock("aaaaa", 2);
        return CommonResult.ok("lock:"+lock+",lock2:"+lock2+",lock3:"+lock3);
    }




    @RequestMapping(value = "/test/lock/testSameLock1",  produces = { "application/json" }, method = { RequestMethod.GET })
    @ApiOperation(value = "布式锁:先请求testSameLock1,再请求testSameLock2模拟同一把分布式锁被占用情况")
    @RedisLock(prefix = "A" ,suffix = "B",expireSec = 10)
    public CommonResult testSameLock1() throws InterruptedException {
        try {
            //模拟耗时常见，并发请求，后请求链接返回系统错误值
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CommonResult.ok(null);
    }


    @RequestMapping(value = "/test/lock/testSameLock2",  produces = { "application/json" }, method = { RequestMethod.GET })
    @ApiOperation(value = "布式锁:先请求testSameLock1,再请求testSameLock2模拟同一把分布式锁被占用情况")
    @RedisLock(prefix = "A" ,suffix = "B",expireSec = 10)
    public CommonResult testSameLock2() throws InterruptedException {
        return CommonResult.ok(null);
    }


}
