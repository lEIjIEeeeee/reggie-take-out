package com.reggie.core.modular.common.model.request;

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
public class DictAddRequest {

    @ApiModelProperty(value = "所属字典类型id")
    @NotBlank(message = "字典类型id不能为空")
    private String dictTypeId;

    @ApiModelProperty(value = "字典值名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "字典值code")
    @NotBlank(message = "code不能为空")
    private String code;

    @ApiModelProperty(value = "状态")
    @NotNull(message = "状态不能为空")
    private EnableOrDisableEnum status;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "描述说明")
    private String desc;

}
