package com.rock.congfig;

import com.google.gson.JsonObject;
import com.rock.common.exception.ExceptionConstants;
import com.rock.common.exception.SwagBootCommonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局的异常捕获处理
 * @ControllerAdvice 注解定义全局异常处理类
 * @ExceptionHandler 注解声明异常处理方法
 */

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    //上传文件大小限制
    @Value("${spring.http.multipart.maxFileSize}")
    private String maxFileSizeLimit;

    //业务异常
    @ExceptionHandler(value = SwagBootCommonException.class)
    @ResponseBody
    public Object swagBootExceptionHandler(HttpServletRequest req, SwagBootCommonException e) throws Exception {
        log.warn("服务端异常", e);
        log.warn("RequestIP:[{}], RequestURI:[{}], QueryString:[{}] , Authorization:[{}]", req.getRemoteAddr(), req.getRequestURI(), req.getQueryString(), req.getHeader("Authorization"));
        JsonObject obj = new JsonObject();
        JsonObject errorData = new JsonObject();
        obj.addProperty("code", e.getExceptionCode());
        obj.addProperty("info", e.getExceptionMsg());
        obj.add("data", errorData);
        return obj.toString();
    }


    //处理controller中带有@Validate注解的参数校验
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public Object bindExceptionHandler(BindException e) {
        JsonObject obj = new JsonObject();
        obj.addProperty("code", ExceptionConstants.errer10005.getCode());
        obj.addProperty("info", ExceptionConstants.errer10005.getInfo());
        obj.add("data", new JsonObject());
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        StringBuilder moreInfo = new StringBuilder();
        if (CollectionUtils.isNotEmpty(errors)) {
            errors.stream().filter(error -> error instanceof FieldError).forEach(error ->
                    moreInfo.append(((FieldError) error).getField())
                            .append(":").append(error.getDefaultMessage())
                            .append(";"));
        }
        obj.addProperty("more info", moreInfo.toString());
        log.error("[Error]", e);
        return obj.toString();
    }


    //SQL执行失败
    @ResponseBody
    @ExceptionHandler(DataAccessException.class)
    public Object dataAccessexceptionHandler(DataAccessException e) {
        JsonObject obj = new JsonObject();
        obj.addProperty("code", ExceptionConstants.errer10006.getCode());
        obj.addProperty("info", ExceptionConstants.errer10006.getInfo());
        obj.add("data", new JsonObject());
        obj.addProperty("more info", e.getMessage());
        return obj.toString();
    }


    //文件大小异常
    @ResponseBody
    @ExceptionHandler(MultipartException.class)
    public Object multipartExceptionHandler(MultipartException e) {
        JsonObject obj = new JsonObject();
        obj.addProperty("code", ExceptionConstants.errer10007.getCode());
        obj.addProperty("info", ExceptionConstants.errer10007.getInfo());
        obj.add("data", new JsonObject());
        if (e instanceof MaxUploadSizeExceededException) {
            obj.addProperty("more info", "文件上传尺寸超过限制, 不能大于" + maxFileSizeLimit);
        }else{
            obj.addProperty("more info", e.getMessage());
        }
        return obj.toString();
    }


    //默认异常处理
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        log.warn("服务端异常", e);
        log.warn("RequestIP:[{}], RequestURI:[{}], QueryString:[{}] , Authorization:[{}]", req.getRemoteAddr(), req.getRequestURI(), req.getQueryString(), req.getHeader("Authorization"));
        JsonObject obj = new JsonObject();
        JsonObject errorData = new JsonObject();
        obj.addProperty("code", ExceptionConstants.errer1000.getCode());
        obj.addProperty("info", ExceptionConstants.errer1000.getInfo());
        obj.add("data", errorData);
        return obj.toString();
    }

}
