package com.rock.model.jpa;

import com.rock.model.base.PageQueryRequest;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Data
public class DsProductPageQueryRequest extends PageQueryRequest{

    @ApiModelProperty("分类名称")
    @NotEmpty
    private String className;

    @ApiModelProperty("名字")
    private String spu;

}
