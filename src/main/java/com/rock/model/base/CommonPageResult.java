package com.rock.model.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rock.common.exception.ExceptionConstants;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rock on 2017/5/24.
 * 通用的分页返回对象
 */
@Data
public class CommonPageResult<T> {

    @JsonProperty("code")
    private Integer code = ExceptionConstants.success.getCode();;

    @JsonProperty("info")
    private String info = ExceptionConstants.success.getInfo();

    //总数量
    @JsonProperty("totalSize")
    private Long totalSize = null;

    //当前页码
    @JsonProperty("page")
    private Integer page = null;

    //每页条数
    @JsonProperty("size")
    private Integer size = null;

    @JsonProperty("data")
    private List<T> data = new ArrayList<T>();

    public CommonPageResult(Integer code, String info, Long totalSize, Integer page, Integer size, List<T> data) {
        this.code = code;
        this.info = info;
        this.totalSize = totalSize;
        this.page = page;
        this.size = size;
        this.data = data;
    }

    public CommonPageResult(Long totalSize, Integer page, Integer size, List<T> data) {
        this.totalSize = totalSize;
        this.page = page;
        this.size = size;
        this.data = data;
    }

    public CommonPageResult() {
    }
}
