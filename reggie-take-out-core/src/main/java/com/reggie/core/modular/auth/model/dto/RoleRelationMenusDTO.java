package com.reggie.core.modular.auth.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRelationMenusDTO {

    @ApiModelProperty(value = "菜单权限id列表")
    private List<String> menuIdList;

}
