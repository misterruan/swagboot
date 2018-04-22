package com.rock.model.jpa;

import com.rock.model.base.PageQueryRequest;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;

@Data
public class DsProductPageQueryRequest extends PageQueryRequest{

    @ApiModelProperty("分类名称")
    @NotBlank
    private String className;

    @ApiModelProperty("名字")
    private String spu;

}
