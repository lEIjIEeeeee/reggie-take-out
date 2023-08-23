package com.reggie.core.modular.common.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DictDetailResponse {

    @ApiModelProperty(value = "字典值id")
    private String id;

    @ApiModelProperty(value = "字典类型id")
    private String dictTypeId;

    @ApiModelProperty(value = "字典类型名称")
    private String dictTypeName;

    @ApiModelProperty(value = "字典值名称")
    private String name;

    @ApiModelProperty(value = "字典值code")
    private String code;

    @ApiModelProperty(value = "状态 ENABLE-启用 DISABLE-禁用")
    private String status;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "描述说明")
    private String desc;

}
