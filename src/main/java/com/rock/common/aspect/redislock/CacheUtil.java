/**
 * @Copyright (C) 2017 wanda.cn Inc. All Rights Reserved.
 */
package com.rock.common.aspect.redislock;

import com.rock.common.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
public class CacheUtil {


    private static final String SPLIT_TAG_IN_PARAMS = "_";


    /**
     * 序列化
     *
     * @param target
     * @return
     */
    public static String serialize(Object target) {
        try {
            return JsonUtils.toJson(target);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 反序列化
     *
     * @param jsonString
     * @param clazz
     * @param modelType
     * @return
     */
    public static Object deserialize(String jsonString, Class clazz, Class modelType) {

        Object result = null;
        try {
            if (clazz.isAssignableFrom(List.class)) {
                result = JsonUtils.jsonToBeans(jsonString, modelType);
                return result;
            }
            result = JsonUtils.toBean(jsonString, clazz);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 生成最终的redis使用的key
     *
     * @param jp
     * @param originPrefix:注解中配置的前缀
     * @param appName:本应用的应用名
     * @param oriSuffix:注解中配置的后缀
     * @return
     */
    public static String getCacheKey(JoinPoint jp, String originPrefix, String appName, String oriSuffix) {
        //前缀
        String prefix = getPrefix(jp, originPrefix, appName);
        //后缀(可能为空)
        String suffix = getSuffix(jp, oriSuffix);
        return StringUtils.isEmpty(suffix) ? prefix : prefix + SPLIT_TAG_IN_PARAMS + suffix;
    }

    /**
     * 获取前缀
     * 如果有配置使用配置的值，没有配置使用：应用名+全类名+方法名
     *
     * @param jp
     * @param prefix
     * @return
     */
    private static String getPrefix(JoinPoint jp, String prefix, String appName) {
        if (StringUtils.isNotBlank(prefix)) {
            return prefix;
        }
        return appName + "::" + jp.getSignature().getDeclaringTypeName() + "::" + jp.getSignature().getName();
    }


    /**
     * 获取后缀
     *
     * @param jp
     * @param oriSuffix
     * @return
     */
    private static String getSuffix(JoinPoint jp, String oriSuffix) {

        Object[] args = jp.getArgs();
        if (args.length == 0 || Arrays.stream(args).filter(a -> Objects.nonNull(a)).count() == 0) {
            //如果目标方法没有参数或者所有参数都是null
            return StringUtils.EMPTY;
        }
        if (StringUtils.isBlank(oriSuffix)) {
            return getKeyOfAllArgs(args);
        }
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        // 获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);

        // 使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        // SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        // 把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        try {
            //如果oriSuffix中SPEL表达式使用不正确，会导致此处解析抛异常,然后会导致后缀失效,锁降级为方法粒度锁
            return parser.parseExpression(oriSuffix).getValue(context, String.class);
        } catch (Exception e) {
            log.error("[CacheUtil.getSuffix parseExpression error],oriSuffix:{}", oriSuffix, e);
        }
        return StringUtils.EMPTY;
    }

    private static String getKeyOfAllArgs(Object[] args) {
        StringBuffer result = new StringBuffer();
        for (Object arg : args) {
            if (arg instanceof Map) {
                Map m = (Map) arg;
                String mapString = transMapToString(m, SPLIT_TAG_IN_PARAMS);
                result.append(mapString).append(SPLIT_TAG_IN_PARAMS);
            } else {
                result.append(arg == null ? null : arg.toString()).append(SPLIT_TAG_IN_PARAMS);
            }
        }
        if (result.length() > 0)
            result.deleteCharAt(result.length() - 1);

        return result.toString();
    }

    private static String transMapToString(Map map, String splitTag) {
        Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (Map.Entry) iterator.next();
            sb.append(entry.getKey().toString()).append(splitTag)
                    .append(null == entry.getValue() ? "" : entry.getValue().toString()).append(splitTag);
        }

        if (sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
