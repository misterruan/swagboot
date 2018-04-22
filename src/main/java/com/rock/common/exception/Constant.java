package com.rock.common.exception;

/**
 * Created by dingyuanjun on 17/2/28.
 */
public class Constant {
    private Integer code;
    private String info;

    public Constant(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
