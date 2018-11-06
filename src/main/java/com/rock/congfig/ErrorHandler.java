package com.rock.congfig;

import com.google.gson.JsonObject;
import com.rock.common.exception.ExceptionConstants;
import com.rock.common.exception.SwagBootCommonException;
import com.rock.model.base.CommonResult;
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
 *
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
    public CommonResult swagBootExceptionHandler(HttpServletRequest req, SwagBootCommonException e) throws Exception {
        log.warn("RequestIP:[{}], RequestURI:[{}], QueryString:[{}] , Authorization:[{}]", req.getRemoteAddr(), req.getRequestURI(), req.getQueryString(), req.getHeader("Authorization"));
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(e.getExceptionCode());
        commonResult.setInfo(e.getExceptionMsg());
        commonResult.setData(new JsonObject());
        log.error("[Error]", e);
        return commonResult;
    }


    //处理controller中带有@Validate注解的参数校验
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public CommonResult bindExceptionHandler(BindException e) {
        CommonResult commonResult = new CommonResult();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        StringBuilder moreInfo = new StringBuilder();
        if (CollectionUtils.isNotEmpty(errors)) {
            errors.stream().filter(error -> error instanceof FieldError).forEach(error ->
                    moreInfo.append(((FieldError) error).getField())
                            .append(":").append(error.getDefaultMessage())
                            .append(";"));
        }
        String detailErrorInfo = ExceptionConstants.errer10005.getInfo() + ":" + moreInfo.toString();
        commonResult.setCode(ExceptionConstants.errer10005.getCode());
        commonResult.setInfo(detailErrorInfo);
        commonResult.setData(null);
        return commonResult;

    }


    //SQL执行失败
    @ResponseBody
    @ExceptionHandler(DataAccessException.class)
    public CommonResult dataAccessexceptionHandler(DataAccessException e) {
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(ExceptionConstants.errer10006.getCode());
        commonResult.setInfo(ExceptionConstants.errer10006.getInfo());
        commonResult.setData(null);
        log.error("[Error]", e);
        return commonResult;
    }


    //文件大小异常
    @ResponseBody
    @ExceptionHandler(MultipartException.class)
    public CommonResult multipartExceptionHandler(MultipartException e) {
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(ExceptionConstants.errer10007.getCode());
        if (e instanceof MaxUploadSizeExceededException) {
            commonResult.setInfo(ExceptionConstants.errer10007.getInfo() + ":文件上传尺寸超过限制, 不能大于" + maxFileSizeLimit);
        } else {
            commonResult.setInfo(ExceptionConstants.errer10007.getInfo());
        }
        commonResult.setData(null);
        log.error("[Error]", e);
        return commonResult;
    }


    //默认异常处理
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResult defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        log.warn("RequestIP:[{}], RequestURI:[{}], QueryString:[{}] , Authorization:[{}]", req.getRemoteAddr(), req.getRequestURI(), req.getQueryString(), req.getHeader("Authorization"));
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(ExceptionConstants.errer1000.getCode());
        commonResult.setInfo(ExceptionConstants.errer1000.getInfo());
        commonResult.setData(null);
        log.error("[Error]", e);
        return commonResult;
    }

}
