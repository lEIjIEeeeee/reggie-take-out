package com.reggie.core.modular.dish.model.entity;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
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

import java.util.List;

@ApiModel(description="tbl_cat")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tbl_cat")
public class Cat extends BaseDO {

    /**
     * 父级id
     */
    @TableField(value = "pid")
    @ApiModelProperty(value="父级id")
    private String pid;
    public static final String PID = "pid";

    /**
     * 名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="名称")
    private String name;
    public static final String NAME = "name";

    /**
     * 状态 1-启用 2-禁用
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态 1-启用 2-禁用")
    private Integer status;

    /**
     * 层级
     */
    @TableField(value = "`level`")
    @ApiModelProperty(value="层级")
    private Integer level;
    public static final String LEVEL = "level";

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="排序")
    private Integer sort;
    public static final String SORT = "sort";

    public static final String TREE_ROOT_ID = "0";
    public static final String TREE_ROOT_PID = "-1";
    public static final String TREE_ROOT_NAME = "菜品分类树";

    public static Tree<String> buildTreeRoot() {
        return buildTreeRoot(null);
    }

    public static Tree<String> buildTreeRoot(List<Tree<String>> childrenList) {
        Tree<String> tree = new Tree<>(getTreeNodeConfig());
        tree.setId(TREE_ROOT_ID);
        tree.setParentId(TREE_ROOT_PID);
        tree.setName(TREE_ROOT_NAME);
        tree.setChildren(childrenList);
        tree.setWeight(1);
        tree.putExtra(LEVEL, TREE_ROOT_ID);
        return tree;
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