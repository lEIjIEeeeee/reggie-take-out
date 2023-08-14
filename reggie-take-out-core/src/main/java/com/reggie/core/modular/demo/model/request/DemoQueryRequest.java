package com.reggie.core.modular.demo.model.request;

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
public class DemoQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

}
