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
@Table(name = "ds_sub_product_basic")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
//指定的字段允许反序列化, 但不会被序列化  for jackson
//  序列化: 用于查询结果的输出, 比如查询:query()
//  反序列化: 用于参数的 ,比如新增:create()
@JsonIgnoreProperties(value = {"updateSelective"}, allowSetters = true)
public class DsSubProductBasic extends AbstractEntity{


    @ApiModelProperty("商品ID")
    @Column(name = "product_id")
    private Long productId;

    @ApiModelProperty("sub1")
    @Column(name = "sub1")
    private String sub1;

    @ApiModelProperty("sub2")
    @Column(name = "sub2")
    private String sub2;

    @ApiModelProperty("SKU图片")
    @Column(name = "image_urls")
    private String imageUrls;

    @ApiModelProperty("条码")
    @Column(name = "bar_code")
    private String barCode;

    @JsonIgnore
    @Column(name = "delete_flag")
    private Integer deleteFlag;

}

