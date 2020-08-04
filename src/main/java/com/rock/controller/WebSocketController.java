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

import java.io.IOException;
import java.time.LocalTime;

/**
 * Created by rock on 2019/8/9.
 */
@RestController
@RequestMapping(value = "/websocket")
@Api("WebSocket操作测试controller：JPA/JOOQ")
@Slf4j
public class WebSocketController {


    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate objectRedisTemplate;

    @RequestMapping(value = "/sendMsg/{userId}",  produces = { "application/json" }, method = { RequestMethod.GET })
    @ApiOperation(value = "根据key获取value")
    public CommonResult getValueByKey(@PathVariable @ApiParam(value = "userId", required = true)String userId) {
        try {
            WebSocketServer.sendInfo("Hello"+ LocalTime.now(), userId); // 推送
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonResult.ok();
    }



}
