package com.rock.common.exception;

/**
 * Created by rock on 2017/4/20.
 * 项目的通用异常，一般命名规则为：项目名+Common/Base+Exception
 */
public class SwagBootCommonException extends RuntimeException {

    //异常对应的返回码
    private Integer exceptionCode;
    //异常对应的描述信息
    private String exceptionMsg;


    public SwagBootCommonException() {
        super();
    }

    public SwagBootCommonException(Integer exceptionCode, String exceptionMsg) {
        //super(exceptionMsg)这样log打印的时候才能打印具体的message信息
        super(exceptionMsg);
        this.exceptionCode = exceptionCode;
        this.exceptionMsg = exceptionMsg;
    }

    public Integer getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(Integer exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
}
