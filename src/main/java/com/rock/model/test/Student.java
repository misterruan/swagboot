package com.rock.model.test;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by rock on 2019/8/9.
 */
@Data
public class Student {

    @ApiModelProperty("名字")
    @NotEmpty
    private String name;

    @ApiModelProperty("年龄")
    @NotNull
    private Integer age;
}
