package com.reggie.core.modular.auth.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuIdRequest {

    @ApiModelProperty(value = "菜单id")
    @NotBlank(message = "菜单id不能为空")
    private String id;

}
