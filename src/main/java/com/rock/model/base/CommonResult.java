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

    /**
     * build
     * @param code
     * @param info
     * @param data
     */
    public static <V> CommonResult<V> build(Integer code, String info, V data) {
        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setInfo(info);
        result.setData(data);
        return result;
    }

    /**
     * 默认的成功返回
     *
     * @param data
     * @param <V>
     * @return
     */
    public static <V> CommonResult<V> ok(V data) {
        return build(ExceptionConstants.success.getCode(), ExceptionConstants.success.getInfo(), data);
    }

    /**
     * 自定义的失败返回
     *
     * @param code
     * @param info
     * @param <V>
     * @return
     */
    private <V> CommonResult<V> fail(Integer code, String info) {
        return build(code, info, null);
    }
}

