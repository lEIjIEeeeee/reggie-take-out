package com.reggie.core.modular.auth.model.request;

import com.reggie.core.modular.auth.enums.MenuSystemTypeEnum;
import com.reggie.core.modular.common.enums.YesOrNoEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuEditRequest {

    @ApiModelProperty(value = "菜单id")
    @NotBlank(message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "菜单名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "菜单父级code")
    @NotBlank(message = "父级code不能为空")
    private String pcode;

    @ApiModelProperty(value = "是否为菜单")
    @NotNull(message = "是否为菜单标记不能为空")
    private YesOrNoEnum menuFlag;

    @ApiModelProperty(value = "系统分类")
    @NotNull(message = "系统分类标记不能为空")
    private MenuSystemTypeEnum systemType;

    @ApiModelProperty(value = "排序")
    private Integer sort;

}
