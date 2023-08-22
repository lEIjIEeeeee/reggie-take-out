package com.reggie.core.modular.auth.controller;

import com.reggie.common.model.HttpResult;
import com.reggie.core.modular.auth.helper.UserHelper;
import com.reggie.core.modular.auth.model.dto.UserDetailDTO;
import com.reggie.core.modular.auth.model.request.*;
import com.reggie.core.modular.auth.model.response.UserResponse;
import com.reggie.core.modular.auth.service.UserService;
import com.reggie.core.modular.common.convert.CommonConvert;
import com.reggie.core.modular.common.model.request.BaseQueryRequest;
import com.reggie.core.modular.common.model.vo.PageVO;
import com.reggie.core.util.JavaBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Api(tags = "用户模块-员工管理相关接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/authModule/user")
public class UserController {

    private final UserService userService;

    private final UserHelper userHelper;

    @ApiOperation(value = "分页查询用户信息列表")
    @GetMapping("/listPage")
    public HttpResult<PageVO<UserResponse>> listPage(@Validated(BaseQueryRequest.ListPage.class)
                                                             BaseQueryRequest request) {
        PageVO<UserResponse> userResponsePageVO = CommonConvert.convertPageToPageVO(userService.listPage(request), UserResponse.class);
        userHelper.fillRoleInfoData(userResponsePageVO.getDataList());
        return HttpResult.success(userResponsePageVO);
    }

    @ApiOperation(value = "查询用户详细信息")
    @GetMapping("/get")
    public HttpResult<UserDetailDTO> get(@RequestParam @NotBlank String id) {
        UserDetailDTO userDetailDTO = JavaBeanUtils.map(userService.get(id), UserDetailDTO.class);
        userHelper.fillUserDetailData(userDetailDTO);
        return HttpResult.success(userDetailDTO);
    }

    @ApiOperation(value = "新增用户")
    @PostMapping("/add")
    public HttpResult<Void> add(@RequestBody @Validated UserAddRequest request) {
        userService.add(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "修改用户信息")
    @PostMapping("/edit")
    public HttpResult<Void> edit(@RequestBody @Validated UserEditRequest request) {
        userService.edit(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除用户")
    @PostMapping("/delete")
    public HttpResult<Void> delete(@RequestBody @Validated UserIdRequest request) {
        userService.delete(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "设置用户角色")
    @PostMapping("/setUpRoles")
    public HttpResult<Void> setUpRoles(@RequestBody @Validated SetUpRolesRequest request) {
        userService.setUpRoles(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "启用或禁用用户状态")
    @PostMapping("/setUserIsEnable")
    public HttpResult<Void> setUserIsEnable(@RequestBody @Validated ActiveOrFreezeUserRequest request) {
        userService.setUserIsEnable(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "重置密码")
    @PostMapping("/resetPassword")
    public HttpResult<Void> resetPassword(@RequestBody @Validated UserIdRequest request) {
        userService.resetPassword(request);
        return HttpResult.success();
    }

}
