package com.rock.common.aspect;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rock.common.util.IPUtils;
import com.rock.common2.util.JsonUtils;
import com.rock.model.base.ActionLog;
import com.rock.repository.base.ActionLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Aspect
@Component
@Order(1) //数字越小在aop中执行先后顺序越高
@Slf4j
/**
 * 用户操作日志记录切面
 */
public class ActionLogAspect {

    @Autowired
    private ActionLogRepository actionLogRepository;


    @Pointcut("@annotation(com.rock.common.aspect.SaveActionLog)")
    public void annotationAspect() { }



    @Around("annotationAspect()")
    public Object saveActionLog(ProceedingJoinPoint point) throws Throwable {
        //获得当前访问的class
        Class<?> className = point.getTarget().getClass();
        //获得访问的方法名
        String methodName = point.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // Todo 如果集成了user session，可以记录登陆user信息
//        Context user = ContextHolder.getContext();
        ActionLog actionLog = new ActionLog();
        //仅记录日志,直接放行
        Object result = point.proceed();
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);
            // 判断是否存在@ActionLog注解
            if (method != null && method.isAnnotationPresent(SaveActionLog.class)) {
                SaveActionLog saveActionLog = method.getAnnotation(SaveActionLog.class);
                actionLog.setDescription(saveActionLog.desc());
                actionLog.setModuleId(saveActionLog.moduleId());
            }
            JSONArray jsonArray = new JSONArray();
            //目标方法所有参数对象转成json
            for (int i = 0; i < point.getArgs().length; i++) {
                //过滤目标方法中HttpServletRequest和HttpServletResponse参数
                if(!(point.getArgs()[i] instanceof HttpServletResponse || point.getArgs()[i] instanceof HttpServletRequest)){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("argument["+i+"]",JsonUtils.toJsonWithCatchException(point.getArgs()[i]));
                    jsonArray.add(jsonObject);
                }
            }
            actionLog.setMethodName(method.getName());
            actionLog.setParams(jsonArray.toJSONString());
            actionLog.setResponseData(JsonUtils.toJsonWithCatchException(result));
            actionLog.setClassName(className.getName());
            actionLog.setClientIp(IPUtils.getCliectIp(request));
            actionLog.setUrl(request.getRequestURL().toString());
//            actionLog.setOperator(user.getUsername());
            actionLogRepository.save(actionLog);
        } catch (Exception e) {
            log.error( "saveActionLog meet error" , new Object[] { e });
        }
        return result;
    }



}
