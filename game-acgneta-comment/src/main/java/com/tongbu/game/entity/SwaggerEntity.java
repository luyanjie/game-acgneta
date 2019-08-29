package com.tongbu.game.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Swagger 对象实例
 */
@Data
@ApiModel(value="Swagger测试对象模型")
public class SwaggerEntity implements Serializable {

    private static final long serialVersionUID = -4593951004754872360L;
    @ApiModelProperty(value="id" ,required=true)
    private Integer id;
    @ApiModelProperty(value="姓名" ,required=true)
    private String name;
}
