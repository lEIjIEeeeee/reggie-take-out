package com.reggie.core.modular.dish.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatDetailDTO {

    @ApiModelProperty(value = "菜品id")
    private String id;

    @ApiModelProperty(value = "父级id")
    private String pid;

    @ApiModelProperty(value = "父级名称")
    private String pName;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "状态 1-启用 2-禁用")
    private Integer status;

    @ApiModelProperty(value = "排序")
    private Integer sort;

}
