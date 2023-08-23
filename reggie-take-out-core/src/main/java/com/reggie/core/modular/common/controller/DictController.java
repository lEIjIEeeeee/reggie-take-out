package com.reggie.core.modular.common.controller;

import com.reggie.common.model.HttpResult;
import com.reggie.core.modular.common.convert.CommonConvert;
import com.reggie.core.modular.common.helper.DictHelper;
import com.reggie.core.modular.common.model.request.*;
import com.reggie.core.modular.common.model.response.DictDetailResponse;
import com.reggie.core.modular.common.model.response.DictResponse;
import com.reggie.core.modular.common.model.response.DictTypeResponse;
import com.reggie.core.modular.common.model.vo.PageVO;
import com.reggie.core.modular.common.service.DictService;
import com.reggie.core.util.JavaBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Api(tags = "公共模块-字典类型信息管理接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/commonModule/dictType")
public class DictController {

    private final DictService dictService;

    private final DictHelper dictHelper;

    @ApiOperation(value = "分页查询字典类型信息列表")
    @GetMapping("/listPageDictType")
    public HttpResult<PageVO<DictTypeResponse>> listPageDictType(@Validated(BaseQueryRequest.ListPage.class)
                                                                         DictTypeQueryRequest request) {
        return HttpResult.success(CommonConvert.convertPageToPageVO(dictService.listPageDictType(request), DictTypeResponse.class));
    }

    @ApiOperation(value = "根据dictTypeId查询字典类型详细信息")
    @GetMapping("/getDictTypeDetail")
    public HttpResult<DictTypeResponse> getDictTypeDetail(@RequestParam @NotBlank String id) {
        return HttpResult.success(JavaBeanUtils.map(dictService.getDictTypeDetail(id), DictTypeResponse.class));
    }

    @ApiOperation(value = "新增字典类型")
    @PostMapping("/addDictType")
    public HttpResult<Void> addDictType(@RequestBody @Validated DictTypeAddRequest request) {
        dictService.addDictType(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "修改字典类型信息")
    @PostMapping("/editDictType")
    public HttpResult<Void> editDictType(@RequestBody @Validated DictTypeEditRequest request) {
        dictService.editDictType(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除字典类型")
    @PostMapping("/deleteDictType")
    public HttpResult<Void> deleteDictType(@RequestBody @Validated DictTypeIdRequest request) {
        dictService.deleteDictType(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "分页查询字典详细信息列表")
    @GetMapping("/listPageDict")
    public HttpResult<PageVO<DictResponse>> listPageDict(@Validated(BaseQueryRequest.ListPage.class)
                                                                 DictQueryRequest request) {
        return HttpResult.success(CommonConvert.convertPageToPageVO(dictService.listPageDict(request), DictResponse.class));
    }

    @ApiOperation(value = "根据dictId查询字典值详细信息")
    @GetMapping("/getDictDetail")
    public HttpResult<DictDetailResponse> getDictDetail(@RequestParam @NotBlank String id) {
        DictDetailResponse dictDetailResponse = JavaBeanUtils.map(dictService.getDictDetail(id), DictDetailResponse.class);
        dictHelper.fillDictTypeInfo(dictDetailResponse);
        return HttpResult.success(dictDetailResponse);
    }

    @ApiOperation(value = "新增字典值")
    @PostMapping("/addDict")
    public HttpResult<Void> addDict(@RequestBody @Validated DictAddRequest request) {
        dictService.addDict(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "修改字典值信息")
    @PostMapping("/editDict")
    public HttpResult<Void> editDict(@RequestBody @Validated DictEditRequest request) {
        dictService.editDict(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除字典值")
    @PostMapping("/deleteDict")
    public HttpResult<Void> deleteDict(@RequestBody @Validated DictIdRequest request) {
        dictService.deleteDict(request);
        return HttpResult.success();
    }

}
