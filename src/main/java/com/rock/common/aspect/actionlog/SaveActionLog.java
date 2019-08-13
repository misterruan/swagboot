package com.rock.common.aspect.actionlog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 添加请求操作日志
 * desc为当前行为标志
 * moduleId为当前业务模块Id
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SaveActionLog {
    String desc() default "";
    String moduleId() default "";
}
