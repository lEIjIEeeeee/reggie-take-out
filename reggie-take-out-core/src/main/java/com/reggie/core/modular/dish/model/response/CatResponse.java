package com.reggie.core.modular.dish.model.response;

import com.reggie.core.modular.common.model.response.BaseResponse;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CatResponse extends BaseResponse {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @ApiModelProperty(value = "状态 1-启用 2-禁用")
    private Integer status;

    @ApiModelProperty(value = "排序")
    private Integer sort;

}
