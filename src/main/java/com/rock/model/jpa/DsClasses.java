package com.rock.model.jpa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by rock on 2018/2/24.
 */
@Entity
@Table(name = "ds_classes")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
//指定的字段允许反序列化, 但不会被序列化  for jackson
//  序列化: 用于查询结果的输出, 比如查询:query()
//  反序列化: 用于参数的 ,比如新增:create()
@JsonIgnoreProperties(value = {"updateSelective"}, allowSetters = true)
public class DsClasses extends AbstractEntity{

    @ApiModelProperty("分类名称")
    @Column(name = "class_name", nullable = false)
    private String className;

    @ApiModelProperty("父类ID")
    @Column(name = "p_id")
    private Long pId;

    @ApiModelProperty("图片地址")
    @Column(name = "image")
    private String image;

}

