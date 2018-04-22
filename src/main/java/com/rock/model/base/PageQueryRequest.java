package com.rock.model.base;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class PageQueryRequest {

    public static final int DEFAULT_PAGE_SIZE = 10;
    private static final int DEFAULT_PAGE_INDEX = 0;

    @ApiParam(value = "页号(从0开始)")
    private Integer page = DEFAULT_PAGE_SIZE;

    @ApiParam(value = "分页大小(默认10)")
    private Integer size = DEFAULT_PAGE_INDEX;

    @ApiParam(value = "排序方式, 默认按照id降序(形式为 '字段名 升降序', 比如 id asc)")
    private String sort;

}
