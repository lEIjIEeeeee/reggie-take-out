package com.reggie.core.modular.auth.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.reggie.core.modular.common.model.entity.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
    * 菜单信息表
    */
@ApiModel(description="菜单信息表")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_menu")
public class Menu extends BaseDO {

    /**
     * 父级id
     */
    @TableField(value = "pid")
    @ApiModelProperty(value="父级id")
    private String pid;

    /**
     * 父级id集合
     */
    @TableField(value = "pids")
    @ApiModelProperty(value="父级id集合")
    private String pids;

    /**
     * 菜单编码
     */
    @TableField(value = "code")
    @ApiModelProperty(value="菜单编码")
    private String code;

    /**
     * 菜单名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="菜单名称")
    private String name;

    /**
     * 菜单url
     */
    @TableField(value = "url")
    @ApiModelProperty(value="菜单url")
    private String url;

    /**
     * 是否是菜单 0-否 1-是
     */
    @TableField(value = "menu_flag")
    @ApiModelProperty(value="是否是菜单 0-否 1-是")
    private Integer menuFlag;

    /**
     * 系统分类（字典）
     */
    @TableField(value = "system_type")
    @ApiModelProperty(value="系统分类（字典）")
    private String systemType;

    /**
     * 状态(字典) 0-启用 1-禁用
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态(字典) 0-启用 1-禁用")
    private Integer status;

    /**
     * 权重
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="权重")
    private Integer sort;

    /**
     * 菜单层级
     */
    @TableField(value = "levels")
    @ApiModelProperty(value="菜单层级")
    private Integer levels;

    /**
     * 备注信息
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="备注信息")
    private String remark;

}