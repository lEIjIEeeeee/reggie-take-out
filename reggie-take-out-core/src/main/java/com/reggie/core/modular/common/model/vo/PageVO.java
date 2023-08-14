package com.reggie.core.modular.common.model.vo;

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
public class PageVO<T> {

    @ApiModelProperty(value = "页码")
    private Long pageNo;

    @ApiModelProperty(value = "页大小")
    private Long pageSize;

    @ApiModelProperty(value = "总数")
    private Long total;

    @ApiModelProperty(value = "数据列表")
    private List<T> dataList;

}
