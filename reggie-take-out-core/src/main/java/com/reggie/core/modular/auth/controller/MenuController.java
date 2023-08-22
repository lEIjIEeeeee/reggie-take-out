package com.reggie.core.modular.auth.controller;

import cn.hutool.core.lang.tree.Tree;
import com.reggie.common.model.HttpResult;
import com.reggie.core.modular.auth.helper.MenuHelper;
import com.reggie.core.modular.auth.model.dto.MenuDetailDTO;
import com.reggie.core.modular.auth.model.request.MenuAddRequest;
import com.reggie.core.modular.auth.model.request.MenuEditRequest;
import com.reggie.core.modular.auth.model.request.MenuIdRequest;
import com.reggie.core.modular.auth.model.request.MenuQueryRequest;
import com.reggie.core.modular.auth.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Api(tags = "权限模块-菜单信息管理接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/authModule/menu")
public class MenuController {

    private final MenuService menuService;

    private final MenuHelper menuHelper;

    @ApiOperation(value = "查询获取菜单完整树")
    @GetMapping("/getMenuFullTree")
    public HttpResult<Tree<String>> getMenuFullTree(MenuQueryRequest request) {
        return HttpResult.success(menuService.menuFullTree(request));
    }

    @ApiOperation(value = "查询菜单详细信息")
    @GetMapping("/get")
    public HttpResult<MenuDetailDTO> get(@RequestParam @NotBlank String id) {
        return HttpResult.success(menuService.get(id));
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("/add")
    public HttpResult<Void> add(@RequestBody @Validated MenuAddRequest request) {
        menuService.add(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "修改菜单信息")
    @PostMapping("/edit")
    public HttpResult<Void> edit(@RequestBody @Validated MenuEditRequest request) {
        menuService.edit(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除菜单")
    @PostMapping("/delete")
    public HttpResult<Void> delete(@RequestBody @Validated MenuIdRequest request) {
        menuService.delete(request);
        return HttpResult.success();
    }

}
