package com.reggie.core.modular.dish.model.request;

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
public class CatIdRequest {

    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不能为空")
    private String id;

}
