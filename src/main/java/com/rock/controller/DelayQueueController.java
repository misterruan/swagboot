package com.rock.controller;

import com.rock.model.base.CommonResult;
import com.rock.model.queue.NotifyContext;
import com.rock.service.common.MktGenerateNotifyDelayQueue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rock on 2019/8/9.
 */
@RestController
@RequestMapping(value = "/delayQueue")
@Api("DelayQueue测试controller：JPA/JOOQ")
@Slf4j
public class DelayQueueController {


    @Autowired
    private MktGenerateNotifyDelayQueue delayQueue;


    @RequestMapping(value = "/putMsg",  produces = { "application/json" }, method = { RequestMethod.GET })
    @ApiOperation(value = "往DelayQueue投递消息")
    public CommonResult getValueByKey(Long id,Integer delaySecond) {
        NotifyContext notifyContext = new NotifyContext();
        notifyContext.setDetailBeanId(id);
        notifyContext.setActiveTime(System.currentTimeMillis() + 1000 * delaySecond);
        MktGenerateNotifyDelayQueue.putIntoQueue(notifyContext);
        //投递成功后，消费线程会在激活时间从DelayQueue中获取到消息并处理
        return CommonResult.ok("ok");
    }

}
