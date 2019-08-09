package com.rock.congfig;

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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        log.error("[Error]", e);
        return CommonResult.build(e.getExceptionCode(), e.getExceptionMsg(), null);
    }


    //处理controller中带有@Validate注解的参数校验
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public CommonResult bindExceptionHandler(BindException e) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        StringBuilder moreInfo = new StringBuilder();
        if (CollectionUtils.isNotEmpty(errors)) {
            errors.stream().filter(error -> error instanceof FieldError).forEach(error ->
                    moreInfo.append(((FieldError) error).getField())
                            .append(":").append(error.getDefaultMessage())
                            .append(";"));
        }
        String detailErrorInfo = ExceptionConstants.errer10005.getInfo() + ":" + moreInfo.toString();
        return CommonResult.build(ExceptionConstants.errer10005.getCode(), detailErrorInfo, null);
    }


    //SQL执行失败
    @ResponseBody
    @ExceptionHandler(DataAccessException.class)
    public CommonResult dataAccessexceptionHandler(DataAccessException e) {
        log.error("[Error]", e);
        return CommonResult.build(ExceptionConstants.errer10006.getCode(), ExceptionConstants.errer10006.getInfo(), null);
    }


    //文件大小异常
    @ResponseBody
    @ExceptionHandler(MultipartException.class)
    public CommonResult multipartExceptionHandler(MultipartException e) {
        String info = null;
        if (e instanceof MaxUploadSizeExceededException) {
            info = ExceptionConstants.errer10007.getInfo() + ":文件上传尺寸超过限制, 不能大于" + maxFileSizeLimit;
        } else {
            info = ExceptionConstants.errer10007.getInfo();
        }
        log.error("[Error]", e);
        return CommonResult.build(ExceptionConstants.errer10007.getCode(), info, null);
    }


    //默认异常处理
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResult defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        log.warn("RequestIP:[{}], RequestURI:[{}], QueryString:[{}] , Authorization:[{}]", req.getRemoteAddr(), req.getRequestURI(), req.getQueryString(), req.getHeader("Authorization"));
        log.error("[Error]", e);
        return CommonResult.build(ExceptionConstants.errer1000.getCode(), ExceptionConstants.errer1000.getInfo(), null);
    }

}
