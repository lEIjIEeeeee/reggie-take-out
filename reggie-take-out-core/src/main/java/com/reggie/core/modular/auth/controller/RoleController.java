package com.reggie.core.modular.auth.controller;

import com.reggie.common.model.HttpResult;
import com.reggie.core.modular.auth.helper.RoleHelper;
import com.reggie.core.modular.auth.model.dto.RoleDetailDTO;
import com.reggie.core.modular.auth.model.dto.RoleRelationMenusDTO;
import com.reggie.core.modular.auth.model.request.RoleQueryRequest;
import com.reggie.core.modular.auth.model.request.RoleRequest;
import com.reggie.core.modular.auth.model.request.SetUpRolePermissionsRequest;
import com.reggie.core.modular.auth.model.response.RoleResponse;
import com.reggie.core.modular.auth.service.RoleService;
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
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Api(tags = "权限模块-角色信息管理接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/authModule/role")
public class RoleController {

    private final RoleService roleService;

    private final RoleHelper roleHelper;

    @ApiOperation(value = "分页查询角色信息列表")
    @GetMapping("/listPage")
    public HttpResult<PageVO<RoleResponse>> listPage(@Validated(BaseQueryRequest.ListPage.class)
                                                             RoleQueryRequest request) {
        return HttpResult.success(CommonConvert.convertPageToPageVO(roleService.listPage(request), RoleResponse.class));
    }

    @ApiOperation(value = "查询角色详情")
    @GetMapping("/get")
    public HttpResult<RoleDetailDTO> get(@RequestParam @NotBlank String id) {
        RoleDetailDTO roleDetail = JavaBeanUtils.map(roleService.get(id), RoleDetailDTO.class);
        roleHelper.fillRoleDetailData(roleDetail);
        return HttpResult.success(roleDetail);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("/add")
    public HttpResult<Void> add(@RequestBody @Validated RoleRequest request) {
        roleService.add(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "编辑角色信息")
    @PostMapping("/edit")
    public HttpResult<Void> edit(@RequestBody @Validated(RoleRequest.Edit.class)
                                         RoleRequest request) {
        roleService.edit(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除角色")
    @PostMapping("/delete")
    public HttpResult<Void> delete(@RequestBody @NotEmpty List<String> idList) {
        roleService.delete(idList);
        return HttpResult.success();
    }

    @ApiOperation(value = "设置角色权限")
    @PostMapping("/setUpRolePermissions")
    public HttpResult<Void> setUpRolePermissions(@RequestBody @Validated SetUpRolePermissionsRequest request) {
        roleService.setUpRolePermissions(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "查询角色菜单权限设置信息")
    @GetMapping("/listMenuIdsByRole")
    public HttpResult<RoleRelationMenusDTO> listMenuIdsByRole(@RequestParam @NotBlank String id) {
        return HttpResult.success(roleService.listMenuIdsByRole(id));
    }

}
