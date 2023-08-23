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

@ApiModel(description="tbl_dict")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tbl_dict")
public class Dict extends BaseDO {

    /**
     * 字典类型id
     */
    @TableField(value = "dict_type_id")
    @ApiModelProperty(value="字典类型id")
    private String dictTypeId;

    /**
     * 字典code
     */
    @TableField(value = "code")
    @ApiModelProperty(value="字典code")
    private String code;

    /**
     * 名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="名称")
    private String name;

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

    /**
     * 扩展字段一
     */
    @TableField(value = "ext_1")
    @ApiModelProperty(value="扩展字段一")
    private String ext1;

    /**
     * 扩展字段二
     */
    @TableField(value = "ext_2")
    @ApiModelProperty(value="扩展字段二")
    private String ext2;

    /**
     * 扩展字段三
     */
    @TableField(value = "ext_3")
    @ApiModelProperty(value="扩展字段三")
    private String ext3;

    /**
     * 扩展字段四
     */
    @TableField(value = "ext_4")
    @ApiModelProperty(value="扩展字段四")
    private String ext4;

}