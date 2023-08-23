package com.reggie.core.modular.common.model.request;

import com.reggie.core.modular.common.enums.DictTypeEnum;
import com.reggie.core.modular.common.enums.EnableOrDisableEnum;
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
public class DictTypeQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "字典类型名称或code关键字搜索")
    private String keywords;

    @ApiModelProperty(value = "字典类型（系统字典、业务字典）")
    private DictTypeEnum dictType;

    @ApiModelProperty(value = "状态")
    private EnableOrDisableEnum status;

}
