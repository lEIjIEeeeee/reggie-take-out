package com.reggie.core.modular.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
    * 字典类型信息表
    */
@ApiModel(description="字典类型信息表")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tbl_dict_type")
public class DictType extends BaseDO {

    /**
     * 字典类型code
     */
    @TableField(value = "code")
    @ApiModelProperty(value="字典类型code")
    private String code;

    /**
     * 名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="名称")
    private String name;

    /**
     * 类型 SYSTEM-系统字典 BIZ-业务字典
     */
    @TableField(value = "`type`")
    @ApiModelProperty(value="类型 SYSTEM-系统字典 BIZ-业务字典")
    private String type;

    /**
     * 状态 ENABLE-启用 DISABLE-禁用
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态 ENABLE-启用 DISABLE-禁用")
    private String status;

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="排序")
    private Integer sort;

    /**
     * 描述说明
     */
    @TableField(value = "`desc`")
    @ApiModelProperty(value="描述说明")
    private String desc;

}