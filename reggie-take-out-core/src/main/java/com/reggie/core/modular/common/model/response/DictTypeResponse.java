package com.reggie.core.modular.common.model.response;

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
public class DictTypeResponse extends BaseResponse {

    @ApiModelProperty(value = "字典类型code")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "类型 SYSTEM-系统字典 BIZ-业务字典")
    private String type;

    @ApiModelProperty(value = "状态 ENABLE-启用 DISABLE-禁用")
    private String status;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "描述说明")
    private String desc;

}
