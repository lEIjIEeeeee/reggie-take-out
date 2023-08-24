package com.reggie.core.modular.dish.controller;

import cn.hutool.core.lang.tree.Tree;
import com.reggie.common.model.HttpResult;
import com.reggie.core.modular.common.convert.CommonConvert;
import com.reggie.core.modular.common.model.vo.PageVO;
import com.reggie.core.modular.dish.model.dto.CatDetailDTO;
import com.reggie.core.modular.dish.model.request.CatIdRequest;
import com.reggie.core.modular.dish.model.request.CatQueryRequest;
import com.reggie.core.modular.dish.model.request.CatRequest;
import com.reggie.core.modular.dish.model.request.CatSetUpStatusRequest;
import com.reggie.core.modular.dish.model.response.CatResponse;
import com.reggie.core.modular.dish.service.CategoryService;
import com.reggie.core.util.JavaBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Api(tags = "菜品模块-菜品分类信息管理接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/dishModule/cat")
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation(value = "查询获取完整菜品分类树")
    @GetMapping("/getCatFullTree")
    public HttpResult<Tree<String>> getCatFullTree() {
        return HttpResult.success(categoryService.getCatFullTree());
    }

    @ApiOperation(value = "分页查询菜品分类信息列表")
    @GetMapping("/listPage")
    public HttpResult<PageVO<CatResponse>> listPage(CatQueryRequest request) {
        return HttpResult.success(CommonConvert.convertPageToPageVO(categoryService.listPage(request), CatResponse.class));
    }

    @ApiOperation(value = "查询菜品类别详细信息")
    @GetMapping("/get")
    public HttpResult<CatDetailDTO> get(@RequestParam @NotBlank String id) {
        return HttpResult.success(JavaBeanUtils.map(categoryService.get(id), CatDetailDTO.class));
    }

    @ApiOperation(value = "新增菜品分类")
    @PostMapping("/add")
    public HttpResult<Void> add(@RequestBody @Validated CatRequest request) {
        categoryService.add(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "修改菜品分类信息")
    @PostMapping("/edit")
    public HttpResult<Void> edit(@RequestBody @Validated(CatRequest.Edit.class) CatRequest request) {
        categoryService.edit(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除菜品分类")
    @PostMapping("/delete")
    public HttpResult<Void> delete(@RequestBody @Validated CatIdRequest request) {
        categoryService.delete(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "设置菜品分类启用状态")
    @PostMapping("/setUpStatus")
    public HttpResult<Void> setUpStatus(@RequestBody @Validated CatSetUpStatusRequest request) {
        categoryService.setUpStatus(request);
        return HttpResult.success();
    }

}
