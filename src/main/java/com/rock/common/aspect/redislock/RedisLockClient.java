package com.rock.common.aspect.redislock;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * redis 交互的client
 */

@Slf4j
@Service
public class RedisLockClient {

	@Autowired
	private RedisTemplate<String, String> stringRedisTemplate;

	private static final String DEFAULT_LOCK_VALUE = "1";


	/**
	 * 加锁
	 * @param key
	 * @param expireSec
	 * @return
	 */
	public boolean getLock(String key,int expireSec) {
		return stringRedisTemplate.opsForValue().setIfAbsent(key, DEFAULT_LOCK_VALUE, Duration.ofSeconds(expireSec));
	}


	/**
	 * 解锁
	 * @param key
	 * @return
	 */
	public boolean releaseLock(String key) {
		if(StringUtils.isNotEmpty(key)){
			return stringRedisTemplate.delete(key);
		}
		return false;
	}

}
