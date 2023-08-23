package com.reggie.core.modular.common.model.request;

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
public class DictTypeIdRequest {

    @ApiModelProperty(value = "字典类型id")
    @NotBlank(message = "id不能为空")
    private String id;

}
