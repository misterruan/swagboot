package com.rock.common.aspect.redislock;

import com.rock.common.exception.ExceptionConstants;
import com.rock.common.exception.SwagBootCommonException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author baolingming
 * @since 2017-06-23
 */
@Slf4j
@Aspect
@Component
@Order(10) //切面优先级
public class RedisLockAspect {

    @Autowired
    private RedisLockClient lockClient;

    //默认的项目名称，如果没有配置则为default
    @Value("${app.name:default}")
    private String appName;

    @Around("@annotation(redisLock)")
    public Object arround(ProceedingJoinPoint pjp, RedisLock redisLock) throws Throwable {
        StopWatch stopwatch = new StopWatch();
        String cacheKey = CacheUtil.getCacheKey(pjp, redisLock.prefix(), appName, redisLock.suffix());
        boolean lockFlag = false;
        try {
            lockFlag = lockClient.getLock(cacheKey, redisLock.expireSec());
            stopwatch.start();
            log.info("get redisLock, success:{},key:{}", lockFlag, cacheKey);
            if (!lockFlag) {
                //没有获取到分布式锁
                throw new SwagBootCommonException(ExceptionConstants.errer1002.getCode(), ExceptionConstants.errer1002.getInfo());
            }
            Object o = pjp.proceed();
            return o;
        } finally {
            if (lockFlag) {
                boolean releaseFlag = lockClient.releaseLock(cacheKey);
                stopwatch.stop();
                log.info("release redisLock, success:{},key:{},use lock time:{}s", releaseFlag, cacheKey, stopwatch.getTotalTimeSeconds());
            }
        }
    }

}