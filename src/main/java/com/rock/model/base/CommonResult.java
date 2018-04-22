package com.rock.model.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rock.common.exception.ExceptionConstants;
import lombok.Data;

/**
 * Created by rock on 2017/5/16.
 * 通用的返回结果类型
 */
@Data
public class CommonResult<T> {

    @JsonProperty("code")
    //默认success code
    private Integer code = ExceptionConstants.success.getCode();

    @JsonProperty("info")
    //默认success info
    private String info = ExceptionConstants.success.getInfo();

    @JsonProperty("data")
    private T data = null;


}

