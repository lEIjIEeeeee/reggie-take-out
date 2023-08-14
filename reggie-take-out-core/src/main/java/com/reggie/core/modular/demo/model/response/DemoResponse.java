package com.reggie.core.modular.demo.model.response;

import com.reggie.core.modular.common.model.response.BaseResponse;
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
public class DemoResponse extends BaseResponse {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

}
