package com.rock.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rock.model.jpa.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 满折类型促销
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "updateSelective"}, allowSetters = true)
public class PromotionTypeDiscount extends AbstractEntity {

    @ApiModelProperty("下限值")
    @NotNull(message = "下限值不能为空")
    private Integer lowerLimit;

    @ApiModelProperty("下限符号")
    @NotBlank(message = "下限符号不能为空")
    @Pattern(regexp =">|>=", message = "下限符号不符合规范")
    private String lowerLimitSymbol;

    @ApiModelProperty("上限值")
    @NotNull(message = "上限值不能为空")
    private Integer upperLimit;

    @ApiModelProperty("上限符号")
    @NotBlank(message = "上限符号不能为空")
    @Pattern(regexp ="<|<=", message = "上限符号不符合规范")
    private String upperLimitSymbol;

    @ApiModelProperty("折扣比率（0-100）")
    @NotNull(message = "折扣比率不能为空")
    private Integer discountRate;


}
