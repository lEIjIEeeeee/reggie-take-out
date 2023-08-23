package com.reggie.core.modular.common.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DictQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "字典类型id")
    @NotBlank(message = "字典类型id不能为空")
    private String dictTypeId;

    @ApiModelProperty(value = "字典名称或code搜索关键字")
    private String keywords;

}
