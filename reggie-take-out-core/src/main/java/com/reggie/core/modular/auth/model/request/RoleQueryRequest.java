package com.reggie.core.modular.auth.model.request;

import com.reggie.core.modular.common.model.request.BaseQueryRequest;
import io.swagger.annotations.ApiModelProperty;
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
public class RoleQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "角色名称")
    private String roleName;

}
