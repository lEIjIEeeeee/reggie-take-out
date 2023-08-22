package com.reggie.core.modular.auth.model.entity;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
    * 菜单信息表
    */
@ApiModel(description="菜单信息表")
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_menu")
public class Menu {

    /**
     * 主键id，菜单id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value="主键id，菜单id")
    private String id;
    public static final String ID = "id";

    /**
     * 父级id
     */
    @TableField(value = "pid")
    @ApiModelProperty(value="父级id")
    private String pid;
    public static final String PID = "pid";

    /**
     * 菜单编码
     */
    @TableField(value = "code")
    @ApiModelProperty(value="菜单编码")
    private String code;
    public static final String CODE = "code";

    /**
     * 菜单父级编码
     */
    @TableField(value = "pcode")
    @ApiModelProperty(value="菜单父级编码")
    private String pcode;
    public static final String PCODE = "pcode";

    /**
     * 菜单父级编码合集
     */
    @TableField(value = "pcodes")
    @ApiModelProperty(value="菜单父级编码合集")
    private String pcodes;

    /**
     * 菜单名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="菜单名称")
    private String name;
    public static final String NAME = "name";

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
    public static final String MENU_FLAG = "menuFlag";

    /**
     * 系统分类（字典）
     */
    @TableField(value = "system_type")
    @ApiModelProperty(value="系统分类（字典）")
    private String systemType;

    /**
     * 权重
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="权重")
    private Integer sort;
    public static final String SORT = "sort";

    /**
     * 菜单层级
     */
    @TableField(value = "levels")
    @ApiModelProperty(value="菜单层级")
    private Integer levels;

    /**
     * 创建人id
     */
    @ApiModelProperty(value = "创建人id")
    @TableField(value = "create_id")
    private String createId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新人id
     */
    @ApiModelProperty(value = "更新人id")
    @TableField(value = "update_id")
    private String updateId;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time")
    private Date updateTime;

    public static final String TREE_ROOT_ID = "0";
    public static final String TREE_ROOT_PID = "-1";
    public static final String TREE_ROOT_NAME = "菜单树";

    public static Tree<String> buildTreeRoot() {
        return buildTreeRoot(null);
    }

    public static Tree<String> buildTreeRoot(List<Tree<String>> childrenList) {
        Tree<String> root = new Tree<>(getTreeNodeConfig());
        root.setId(TREE_ROOT_ID);
        root.setParentId(TREE_ROOT_PID);
        root.setName(TREE_ROOT_NAME);
        root.setChildren(childrenList);
        root.setWeight(1);
        root.putExtra(Menu.CODE, TREE_ROOT_ID);
        root.putExtra(Menu.PCODE, TREE_ROOT_PID);
        return root;
    }

    public static TreeNodeConfig getTreeNodeConfig() {
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setIdKey(ID);
        treeNodeConfig.setParentIdKey(PID);
        treeNodeConfig.setNameKey(NAME);
        treeNodeConfig.setWeightKey(SORT);
        return treeNodeConfig;
    }

}