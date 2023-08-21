package com.reggie.core.modular.auth.model.response;

import com.reggie.core.modular.common.model.response.BaseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserResponse extends BaseResponse {

    @ApiModelProperty(value = "登录账号")
    private String account;

    @ApiModelProperty(value = "账号昵称")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "角色名称")
    private String roleNames;

    @ApiModelProperty(value = "状态 0-正常 1-限制 2-冻结 3-删除")
    private Integer status;

    @ApiModelProperty(value = "最后登录时间")
    private Date lastLoginTime;

}
