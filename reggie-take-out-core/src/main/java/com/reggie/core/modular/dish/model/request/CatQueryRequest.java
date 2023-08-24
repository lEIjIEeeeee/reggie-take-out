package com.reggie.core.modular.dish.model.request;

import com.reggie.core.modular.common.model.request.BaseQueryRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CatQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "父级类别id")
    private String id;

}
