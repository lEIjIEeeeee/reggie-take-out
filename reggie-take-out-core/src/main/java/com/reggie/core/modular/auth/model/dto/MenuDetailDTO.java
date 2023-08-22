package com.reggie.core.modular.auth.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuDetailDTO {

    @ApiModelProperty(value = "菜单id")
    private String id;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单code")
    private String code;

    @ApiModelProperty(value = "父级菜单名称")
    private String pname;

    @ApiModelProperty(value = "是否为菜单标记")
    private Integer menuFlag;

    @ApiModelProperty(value = "系统分类")
    private String systemType;

    @ApiModelProperty(value = "排序")
    private Integer sort;

}
