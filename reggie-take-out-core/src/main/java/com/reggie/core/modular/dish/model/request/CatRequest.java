package com.reggie.core.modular.dish.model.request;

import com.reggie.core.modular.common.enums.EnableOrDisableStatusEnum;
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
public class CatRequest {

    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不能为空", groups = { CatRequest.Edit.class })
    private String id;

    @ApiModelProperty(value = "父级id")
    @NotBlank(message = "父级id不能为空")
    private String pid;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "状态")
    @NotNull(message = "状态不能为空")
    private EnableOrDisableStatusEnum status;

    @ApiModelProperty(value = "")
    private Integer sort;

    /**
     * 修改校验组
     */
    public interface Edit {
    }

}
