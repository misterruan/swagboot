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
 */
@Entity
@Table(name = "ds_product_basic")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
//指定的字段允许反序列化, 但不会被序列化  for jackson
//  序列化: 用于查询结果的输出, 比如查询:query()
//  反序列化: 用于参数的 ,比如新增:create()
@JsonIgnoreProperties(value = {"updateSelective"}, allowSetters = true)
public class DsProductBasic extends AbstractEntity{

    @ApiModelProperty("名字")
    @Column(name = "spu", nullable = false)
    private String spu;

    @ApiModelProperty("分类ID")
    @Column(name = "class_id")
    private Long classId;

    @ApiModelProperty("主图")
    @Column(name = "image")
    private String image;

    @ApiModelProperty("详情图")
    @Column(name = "detail_images")
    private String detailImages;

    @ApiModelProperty("sub1Name")
    @Column(name = "sub1_name")
    private String sub1Name;

    @ApiModelProperty("sub2Name")
    @Column(name = "sub2_name")
    private String sub2Name;

    @JsonIgnore
    @Column(name = "delete_flag")
    private Integer deleteFlag;

}

