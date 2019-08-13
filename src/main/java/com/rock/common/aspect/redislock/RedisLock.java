package com.rock.common.aspect.redislock;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RedisLock {

	/**
	 * 自定义前缀
	 * 如果不设置会默认填充:应用名+全类名+方法名
	 * @return
	 */
	String prefix() default "";

	/**
	 * 自定义后缀:
	 * 用于生成细粒度的key，比如用参数对象的id等作为后缀
	 * 如果不设置,会使用目标方法的参数对象转成字符串作为后缀
	 * 使用SPEL表达式解析
	 * @return
	 */
	String suffix() default "";

	/**
	 * 缓存多少秒,默认60秒c
	 */
	int expireSec() default 60;
}