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
public class RoleRequest {

    @ApiModelProperty(value = "主键id")
    @NotBlank(message = "id不能为空", groups = { RoleRequest.Edit.class })
    private String id;

    @ApiModelProperty(value = "父级id")
    @NotBlank(message = "pid不能为空")
    private String pid;

    @ApiModelProperty(value = "角色code")
    @NotBlank(message = "code不能为空")
    private String code;

    @ApiModelProperty(value = "角色名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 修改校验组
     */
    public interface Edit {
    }

}
