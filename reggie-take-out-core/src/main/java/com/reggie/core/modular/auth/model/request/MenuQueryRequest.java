package com.reggie.core.modular.auth.model.request;

import com.reggie.core.modular.common.enums.YesOrNoEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuQueryRequest {

    @ApiModelProperty(value = "搜索关键字：菜单名称或code")
    private String keywords;

    @ApiModelProperty(value = "菜单层级")
    private Integer levels;

    @ApiModelProperty(value = "是否为菜单")
    private YesOrNoEnum menuFlag;

}
