package com.reggie.core.modular.auth.model.request;

import com.reggie.core.modular.common.enums.EnableOrDisableEnum;
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
public class ActiveOrFreezeUserRequest {

    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "用户id不能为空")
    private String id;

    @ApiModelProperty(value = "启用或禁用操作")
    @NotNull(message = "是否禁用选项操作不正确")
    private EnableOrDisableEnum operation;

}
