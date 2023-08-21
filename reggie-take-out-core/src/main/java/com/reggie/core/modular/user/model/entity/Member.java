package com.reggie.core.modular.user.model.entity;

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

import java.util.Date;

/**
    * 用户信息表
    */
@ApiModel(description="用户信息表")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tbl_member")
public class Member extends BaseDO {

    /**
     * 登录账号
     */
    @TableField(value = "account")
    @ApiModelProperty(value="登录账号")
    private String account;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    @ApiModelProperty(value="头像")
    private String avatar;

    /**
     * 用户昵称
     */
    @TableField(value = "nick_name")
    @ApiModelProperty(value="用户昵称")
    private String nickName;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    @ApiModelProperty(value="手机号")
    private String phone;

    /**
     * 注册类型 0-游客 1-会员
     */
    @TableField(value = "register_type")
    @ApiModelProperty(value="注册类型 0-游客 1-会员")
    private Integer registerType;

    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    @ApiModelProperty(value="真实姓名")
    private String realName;

    /**
     * 性别 F-女 M-男（字典）
     */
    @TableField(value = "gender")
    @ApiModelProperty(value="性别 F-女 M-男（字典）")
    private String gender;

    /**
     * 生日
     */
    @TableField(value = "birthday")
    @ApiModelProperty(value="生日")
    private Date birthday;

    /**
     * 状态 0-正常 1-限制 2-冻结 3-删除
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态 0-正常 1-限制 2-冻结 3-删除")
    private Integer status;

    /**
     * 上一次登录时间
     */
    @TableField(value = "last_login_time")
    @ApiModelProperty(value="上一次登录时间")
    private Date lastLoginTime;

    /**
     * 微信openId
     */
    @TableField(value = "open_id")
    @ApiModelProperty(value="微信openId")
    private String openId;

}