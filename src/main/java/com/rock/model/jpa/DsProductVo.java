package com.rock.model.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * 用于关联查询返回的Vo对象
 */
@Data
@Accessors(chain = true)
//指定的字段允许反序列化, 但不会被序列化  for jackson
//  序列化: 用于查询结果的输出, 比如查询:query()
//  反序列化: 用于参数的 ,比如新增:create()
public class DsProductVo {
    //-----DsSubProductBasic字段------
    @ApiModelProperty("DsSubProductBasic的ID")
    private Long skuId;

    @ApiModelProperty("sub1")
    private String sub1;

    @ApiModelProperty("sub2")
    private String sub2;

    //-----DsProductBasic字段------
    @ApiModelProperty("DsProductBasic的ID")
    private Long spuId;

    @ApiModelProperty("名字")
    private String spu;

    @ApiModelProperty("主图")
    private String images;

    @ApiModelProperty("详情图")
    private String detailImages;

    @ApiModelProperty("sub1Name")
    private String sub1Name;

    @ApiModelProperty("sub2Name")
    private String sub2Name;

    //-----DsClasses字段------
    @ApiModelProperty("分类ID")
    private Long classId;

    @ApiModelProperty("分类名称")
    private String className;
}

